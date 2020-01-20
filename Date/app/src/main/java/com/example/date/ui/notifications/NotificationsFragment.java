package com.example.date.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.date.BuildConfig;
import com.example.date.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment {

    private static final String path = Environment.DIRECTORY_DCIM + "/Screenshots";
    private Button OCRButton;
    private ImageView OCRImage;
    private TextView OCRText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        OCRImage = v.findViewById(R.id.OCRImage);

        // getting the latest screenshot image
        File screenShot = new File(Environment.getExternalStorageDirectory()+"/DCIM/Screenshots");
        File[] files = screenShot.listFiles();
        Uri selectedImageUri = Uri.fromFile(files[files.length-1]);

        OCRImage.setImageURI(selectedImageUri);

        FirebaseVisionImage image = imageFromPath(getContext(), selectedImageUri);
        recognizeText(image);

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
                        Log.d("TEXT", recogTexts.toString());
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

//    private void processTextBlock(FirebaseVisionText result) {
//        String resultText = result.getText();
//        OCRText.setText(resultText);
//        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
//            String blockText = block.getText();
//
//            Float blockConfidence = block.getConfidence();
//            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
//            Point[] blockCornerPoints = block.getCornerPoints();
//            Rect blockFrame = block.getBoundingBox();
//
//            for (FirebaseVisionText.Line line: block.getLines()) {
//                String lineText = line.getText();
//                Float lineConfidence = line.getConfidence();
//                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
//                Point[] lineCornerPoints = line.getCornerPoints();
//                Rect lineFrame = line.getBoundingBox();
//
//                for (FirebaseVisionText.Element element: line.getElements()) {
//                    String elementText = element.getText();
//                    Float elementConfidence = element.getConfidence();
//                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
//                    Point[] elementCornerPoints = element.getCornerPoints();
//                    Rect elementFrame = element.getBoundingBox();
//                }
//            }
//        }
//    }

}