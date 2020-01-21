package com.example.date.ui.appointments;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.date.MainActivity;

import com.example.date.BuildConfig;
import com.example.date.R;
import com.example.date.ui.account.SaveSharedPreference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;

public class AppointmentFragment extends Fragment {

    private static final String path = Environment.DIRECTORY_DCIM + "/Screenshots";
    private Button OCRButton;
    private ImageView OCRImage;
    private TextView OCRText;
    private Boolean addFlag;
    private TextView textView;
    private String meetResultText;
    private String meetText;
    private com.google.api.services.calendar.Calendar mService = null;
    private int mID = 0;
    GoogleAccountCredential mCredential;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private Calendar dateCalender = java.util.Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);

        OCRImage = v.findViewById(R.id.OCRImage);

        // getting the latest screenshot image
        File screenShot = new File(Environment.getExternalStorageDirectory()+"/DCIM/Screenshots");
        File[] files = screenShot.listFiles();
        Uri selectedImageUri = Uri.fromFile(files[files.length-1]);

        OCRImage.setImageURI(selectedImageUri);

        FirebaseVisionImage image = imageFromPath(getContext(), selectedImageUri);
        recognizeText(image);

        mCredential = GoogleAccountCredential.usingOAuth2(
                getActivity().getApplicationContext(),
                Arrays.asList(SCOPES)
        ).setBackOff(new ExponentialBackOff());
        mID = 1;
        getResultsFromApi();

        return v;
    }

    public void recognizeText(FirebaseVisionImage image) {
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();

        FirebaseVisionCloudTextRecognizerOptions.Builder optionBuilder =
                new FirebaseVisionCloudTextRecognizerOptions.Builder();
        if (BuildConfig.DEBUG) {
            optionBuilder.enforceCertFingerprintMatch();
        }

        FirebaseVisionCloudTextRecognizerOptions options = optionBuilder
                .setLanguageHints(Arrays.asList("ko"))
                .build();

        ArrayList<String> recogTexts = new ArrayList<>();

        Task<FirebaseVisionText> result = detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
                            String text = block.getText();
                            recogTexts.add(text);
                        }
                        meetText = recogTexts.get(recogTexts.size()-3);
                        Log.d("TEXT", recogTexts.toString());
                        new Thread(){
                            public void run(){
                                List<String> list = JavaTwitterKoreanTextExample.main(meetText);
                                Log.d("result", ""+list);
                                Message msg = handler.obtainMessage();
                                if(list.contains("만나다")||list.contains("보다")||list.contains("가다")){
                                    Map<String, Integer> timeList = getMeeting(list);
                                    Log.d("일정: ", ""+timeList);
                                    meetResultText = meetingResult(timeList);
                                    handler.sendMessage(msg);
                                }
                            }
                        }.start();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d("OCR", "FAILED");
                    }
                });
    }

    private FirebaseVisionImage imageFromPath(Context context, Uri uri) {
        FirebaseVisionImage image;
        try {
            image = FirebaseVisionImage.fromFilePath(context, uri);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.meet);
    }
    final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            textView.setText(meetResultText);
            mID =2;
            getResultsFromApi();
        }
    };

    private String getResultsFromApi() {

        if (!isGooglePlayServicesAvailable()) { // Google Play Services를 사용할 수 없는 경우

            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) { // 유효한 Google 계정이 선택되어 있지 않은 경우

            chooseAccount();
        } else if (!isDeviceOnline()) {    // 인터넷을 사용할 수 없는 경우

        } else {

            // Google Calendar API 호출
            new MakeRequestTask((MainActivity) getActivity(), mCredential).execute();
        }
        return null;
    }
    private boolean isGooglePlayServicesAvailable() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getContext());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }
    private void acquireGooglePlayServices() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getContext());

        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {

            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode
    ) {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES
        );
        dialog.show();
    }
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {

        // GET_ACCOUNTS 권한을 가지고 있다면
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.GET_ACCOUNTS)) {


            // SharedPreferences에서 저장된 Google 계정 이름을 가져온다.
            String accountName = getActivity().getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {

                // 선택된 구글 계정 이름으로 설정한다.
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {


                // 사용자가 구글 계정을 선택할 수 있는 다이얼로그를 보여준다.
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }



            // GET_ACCOUNTS 권한을 가지고 있지 않다면
        } else {


            // 사용자에게 GET_ACCOUNTS 권한을 요구하는 다이얼로그를 보여준다.(주소록 권한 요청함)
            EasyPermissions.requestPermissions(
                    getActivity(),
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }
    @Override
    public void onActivityResult(
            int requestCode,  // onActivityResult가 호출되었을 때 요청 코드로 요청을 구분
            int resultCode,   // 요청에 대한 결과 코드
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case REQUEST_GOOGLE_PLAY_SERVICES:

                if (resultCode != RESULT_OK) {

                } else {

                    getResultsFromApi();
                }
                break;


            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;


            case REQUEST_AUTHORIZATION:

                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,  //requestPermissions(android.app.Activity, String, int, String[])에서 전달된 요청 코드
            @NonNull String[] permissions, // 요청한 퍼미션
            @NonNull int[] grantResults    // 퍼미션 처리 결과. PERMISSION_GRANTED 또는 PERMISSION_DENIED
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    /*
     * 안드로이드 디바이스가 인터넷 연결되어 있는지 확인한다. 연결되어 있다면 True 리턴, 아니면 False 리턴
     */
    private boolean isDeviceOnline() {

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
    /*
     * 캘린더 이름에 대응하는 캘린더 ID를 리턴
     */
    private String getCalendarID(String calendarTitle){

        String id = null;

        // Iterate through entries in calendar list
        String pageToken = null;
        do {
            CalendarList calendarList = null;
            try {
                calendarList = mService.calendarList().list().setPageToken(pageToken).execute();
            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            }catch (IOException e) {
                e.printStackTrace();
            }
            List<CalendarListEntry> items = calendarList.getItems();


            for (CalendarListEntry calendarListEntry : items) {

                if ( calendarListEntry.getSummary().toString().equals(calendarTitle)) {

                    id = calendarListEntry.getId().toString();
                }
            }
            pageToken = ((CalendarList) calendarList).getNextPageToken();
        } while (pageToken != null);

        return id;
    }
    /*
     * 비동기적으로 Google Calendar API 호출
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private Exception mLastError = null;
        private MainActivity mActivity;
        List<String> eventStrings = new ArrayList<String>();


        public MakeRequestTask(MainActivity activity, GoogleAccountCredential credential) {

            mActivity = activity;

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }


        @Override
        protected void onPreExecute() {
        }


        /*
         * 백그라운드에서 Google Calendar API 호출 처리
         */
        @Override
        protected String doInBackground(Void... params) {
            try {

                if ( mID == 1) {

                    return createCalendar();

                }else if (mID == 2) {

                    return addEvent();
                }
                else if (mID == 3) {

                    return getEvent();
                }


            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }

            return null;
        }


        /*
         * CalendarTitle 이름의 캘린더에서 10개의 이벤트를 가져와 리턴
         */
        private String getEvent() throws IOException {


            DateTime now = new DateTime(System.currentTimeMillis());

            String calendarID = getCalendarID("CalendarTitle");
            if ( calendarID == null ){

                return "캘린더를 먼저 생성하세요.";
            }


            Events events = mService.events().list(calendarID)//"primary")
                    .setMaxResults(10)
                    //.setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();


            for (Event event : items) {

                DateTime start = event.getStart().getDateTime();
                if (start == null) {

                    // 모든 이벤트가 시작 시간을 갖고 있지는 않다. 그런 경우 시작 날짜만 사용
                    start = event.getStart().getDate();
                }


                eventStrings.add(String.format("%s \n (%s)", event.getSummary(), start));
            }


            return eventStrings.size() + "개의 데이터를 가져왔습니다.";
        }

        /*
         * 선택되어 있는 Google 계정에 새 캘린더를 추가한다.
         */
        private String createCalendar() throws IOException {

            String ids = getCalendarID("DateCalender");

            if ( ids != null ){

                return "이미 캘린더가 생성되어 있습니다. ";
            }

            // 새로운 캘린더 생성
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();

            // 캘린더의 제목 설정
            calendar.setSummary("DateCalender");


            // 캘린더의 시간대 설정
            calendar.setTimeZone("Asia/Seoul");

            // 구글 캘린더에 새로 만든 캘린더를 추가
            com.google.api.services.calendar.model.Calendar createdCalendar = mService.calendars().insert(calendar).execute();

            // 추가한 캘린더의 ID를 가져옴.
            String calendarId = createdCalendar.getId();


            // 구글 캘린더의 캘린더 목록에서 새로 만든 캘린더를 검색
            CalendarListEntry calendarListEntry = mService.calendarList().get(calendarId).execute();

            // 캘린더의 배경색을 파란색으로 표시  RGB
            calendarListEntry.setBackgroundColor("#0000ff");

            // 변경한 내용을 구글 캘린더에 반영
            CalendarListEntry updatedCalendarListEntry =
                    mService.calendarList()
                            .update(calendarListEntry.getId(), calendarListEntry)
                            .setColorRgbFormat(true)
                            .execute();

            // 새로 추가한 캘린더의 ID를 리턴
            return "캘린더가 생성되었습니다.";
        }


        @Override
        protected void onPostExecute(String output) {

        }


        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            REQUEST_AUTHORIZATION);
                } else {
                }
            } else {
            }
        }


        private String addEvent() {


            String calendarID = getCalendarID("DateCalender");

            if ( calendarID == null ){

                return "캘린더를 먼저 생성하세요.";

            }

            Event event = new Event()
                    .setSummary(meetResultText)
                    .setLocation(SaveSharedPreference.getCity(getContext()))
                    .setDescription(meetResultText);


            java.util.Calendar calander;

            calander = java.util.Calendar.getInstance();
            SimpleDateFormat simpledateformat;
            //simpledateformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREA);
            // Z에 대응하여 +0900이 입력되어 문제 생겨 수작업으로 입력
            simpledateformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss+09:00", Locale.KOREA);
            String datetime = simpledateformat.format(dateCalender.getTime());
            Log.d("datetime", datetime+"");
            DateTime startDateTime = new DateTime(datetime);
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);

            Log.d( "@@@", datetime );


            DateTime endDateTime = new  DateTime(datetime);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setEnd(end);

            //String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
            //event.setRecurrence(Arrays.asList(recurrence));


            try {
                event = mService.events().insert(calendarID, event).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "Exception : " + e.toString());
            }
            System.out.printf("Event created: %s\n", event.getHtmlLink());
            Log.e("Event", "created : " + event.getHtmlLink());
            String eventStrings = "created : " + event.getHtmlLink();
            return eventStrings;
        }
    }
    private Map<String, Integer> getMeeting(List<String> list){
        Integer month =0;
        Integer day =0;
        Integer time =0;
        Integer min =0;
        Boolean nextFlag = false;
        Boolean nextWeekFlag = false;
        Integer afternoon = 0;
        Calendar currentCalendar = Calendar.getInstance();
        Log.d("today", ""+currentCalendar);
        Map<String, Integer> result = new HashMap<String, Integer>();
        for(String s :list){
            Log.d("다음주", ""+nextWeekFlag+"???"+s);
            if(s.charAt(s.length()-1) == '월'){
                month = Integer.valueOf(s.substring(0, s.length()-1));
            }
            else if(s.charAt(s.length()-1) == '일'&& (s.charAt(0)>='0' && s.charAt(0)<='9')){
                day = Integer.valueOf(s.substring(0, s.length()-1));
            }
            else if(s.charAt(s.length()-1) == '시'){
                time = Integer.valueOf(s.substring(0, s.length()-1));
            }
            else if(s.charAt(s.length()-1) == '분'){
                min = Integer.valueOf(s.substring(0, s.length()-1));
            }
            else if(s.equals("오전")||s.equals("아침")) afternoon = 1;
            else if(s.equals("오후")||s.equals("저녁")) afternoon = 2;
            else if((s.equals("내일")||s.equals("낼"))&&(!list.contains("모레"))){
                day = currentCalendar.get(Calendar.DAY_OF_MONTH)+1;
            }
            else if(s.equals("오늘")){
                day = currentCalendar.get(Calendar.DAY_OF_MONTH);
            }
            else if(s.equals("모레")){
                day = currentCalendar.get(Calendar.DAY_OF_MONTH)+2;
            }
            else if(s.equals("반")){
                min = 30;
            }
            else if(s.equals("다음")||s.equals("담")){
                nextFlag = true;
            }
            else if(s.equals("다음주")||s.equals("담주")){
                nextWeekFlag=true;
            }
            else if(nextFlag){
                if(s.equals("달")){
                    month = currentCalendar.get(Calendar.MONTH)+2;
                    nextFlag=false;
                }
                else if(s.equals("주")){
                    nextWeekFlag = true;
                    nextFlag = false;
                }
            }
            else if(nextWeekFlag){
                int temp = 0;
                if(s.equals("일요일")) temp=1;
                else if(s.equals("월요일")) temp=2;
                else if(s.equals("화요일")) temp=3;
                else if(s.equals("수요일")) temp=4;
                else if(s.equals("목요일")) temp=5;
                else if(s.equals("금요일")) temp=6;
                else if(s.equals("토요일")) temp=7;
                int today = currentCalendar.get(Calendar.DAY_OF_WEEK);
                day = currentCalendar.get(Calendar.DAY_OF_MONTH)+temp-today + 7;
                Log.d("the day is ", ""+day);
                nextWeekFlag = false;
            }
            else if(!nextWeekFlag){
                int temp=0;
                if(s.equals("일요일")) temp=1;
                else if(s.equals("월요일")) temp=2;
                else if(s.equals("화요일")) temp=3;
                else if(s.equals("수요일")) temp=4;
                else if(s.equals("목요일")) temp=5;
                else if(s.equals("금요일")) temp=6;
                else if(s.equals("토요일")) temp=7;
                int today = currentCalendar.get(Calendar.DAY_OF_WEEK);
                if(temp!=0 && temp>today) day =currentCalendar.get(Calendar.DAY_OF_MONTH)+temp-today;
                else if(temp!=0 && temp<=today) day = currentCalendar.get(Calendar.DAY_OF_MONTH)+temp-today+7;
            }
        }
        if(month==0){
            Log.d("why", ""+month);
            if(day!=0) {
                if (day < currentCalendar.get(Calendar.DAY_OF_MONTH))
                    month = currentCalendar.get(Calendar.MONTH) + 2;
                else month = currentCalendar.get(Calendar.MONTH) + 1;
            }
            Log.d("why", ""+month);
        }
        if(afternoon==0){
            if(time<8){
                afternoon=2;
            }
        }

        result.put("월", month);
        result.put("일", day);
        result.put("시", time);
        result.put("분", min);
        result.put("오후", afternoon);
        if(afternoon==2) time+=12;
        dateCalender.set(currentCalendar.get(Calendar.YEAR), month-1, day, time, min);
        return result;
    }
    private String meetingResult(Map<String, Integer> timeList){
        String result="데이트: ";
        if(timeList.get("월")!=0) result = result+timeList.get("월").toString()+"월 ";
        if(timeList.get("일")!=0) result = result+timeList.get("일").toString()+"일 ";
        if(timeList.get("오후")!=0) {
            if(timeList.get("오후")==1) result = result + "오전 ";

            else if(timeList.get("오후")==2) result = result + "오후 ";
        }
        if(timeList.get("시")!=0) result = result+timeList.get("시").toString()+"시 ";
        if(timeList.get("분")!=0) result = result+timeList.get("분").toString()+"분 ";
        return result;
    }
}