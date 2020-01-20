package com.example.date.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.date.R;
public class NotificationsFragment extends Fragment {
    private static final String path = Environment.DIRECTORY_DCIM + "/Screenshots";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);







        return v;
    }

}