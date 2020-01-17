package com.example.date.ui.home.Course;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.date.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CoursePagerAdapter extends PagerAdapter {

//    private static final int[] TAB_TITLES = new int[]{R.string.course_tab_text_1, R.string.course_tab_text_2};
    private static ArrayList<View> views = new ArrayList<>();
    private final Context mContext;

    public CoursePagerAdapter(ArrayList<View> views, Context context) {
        this.views = views;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    public View getView(int position) {
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        for (int i=0; i<getCount(); i++) {
            if((View)object==views.get(i)) {
                return i;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+"";
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mContext.getResources().getString(TAB_TITLES[position]);
//    }
}