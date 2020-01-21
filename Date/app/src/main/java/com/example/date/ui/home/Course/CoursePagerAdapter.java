package com.example.date.ui.home.Course;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.date.R;
import com.google.android.libraries.places.api.model.Place;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

public class CoursePagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"데이트 시작", "데이트 끝"};
    private static ArrayList<Place> spots = new ArrayList<>();
    private static ArrayList<String> comments = new ArrayList<>();
    private static ArrayList<View> views;
    private static String type;
    private LayoutInflater inflater;
    private Context mContext;

    public CoursePagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public int getCount() {
        return spots.size()+2;
    }


//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view==object;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((LinearLayout) object);
//    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            Log.d("START ITEM", "");
            return CourseStartFragment.newInstance(position, type);
        } else if (position==(spots.size()+1)) {
            Log.d("END ITEM", "");
            return CourseEndFragment.newInstance(position, spots);
        } else {
            Log.d("DETAIL ITEM", "");
            return CourseDetailFragment.newInstance(position, spots.get(position-1), comments.get(position-1));
        }
    }

    public void setSpots(ArrayList<Place> spots) {
        this.spots = spots;
        notifyDataSetChanged();
    }
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }
    public void setType(String type) {
        this.type = type;
        notifyDataSetChanged();
    }
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View viewLayout;
//
//        if (position==0) {
//            viewLayout = inflater.inflate(R.layout.fragment_course_start, container, false);
//            TextView startText = viewLayout.findViewById(R.id.courseStartText);
//            startText.setText("start");
//        } else if (position==(spots.size()+1)) {
//            viewLayout = inflater.inflate(R.layout.fragment_course_end, container, false);
//            TextView endText = viewLayout.findViewById(R.id.courseTip);
//            endText.setText("tip");
//        } else {
//            viewLayout = inflater.inflate(R.layout.fragment_course_detail, container, false);
//            TextView nameText = viewLayout.findViewById(R.id.spotName);
//            nameText.setText(spots.get(position-1));
//        }
//
//        container.addView(viewLayout);
//        return viewLayout;
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        for (int i=0; i<getCount(); i++) {
//            if((View)object==views.get(i)) {
//                return i;
//            }
//        }
//        return POSITION_NONE;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return position+"";
//    }

//    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return TAB_TITLES[0];
        else if(position==(spots.size()+1)) return TAB_TITLES[1];
        else return spots.get(position-1).getName();
    }
}