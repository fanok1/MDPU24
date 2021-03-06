package com.fanok.mdpu24.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.fanok.mdpu24.R;
import com.fanok.mdpu24.StartActivity;
import com.fanok.mdpu24.TypeTimeTable;
import com.fanok.mdpu24.dowland.DowlandJsonAbsenteeism;
import com.fanok.mdpu24.dowland.ParceJsonAbsenteeism;

import static android.content.Context.MODE_PRIVATE;

public class TabAbsenteeism extends Fragment {

    private int modul;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_marks, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.modul = bundle.getInt("modul");
        }
        ScrollView scrollVertical = view.findViewById(R.id.scrollV);
        HorizontalScrollView scrollHoresontal = view.findViewById(R.id.scrollH);

        scrollHoresontal.requestDisallowInterceptTouchEvent(true);
        scrollVertical.setOnTouchListener(new View.OnTouchListener() {
            private float mx, my, curX, curY;
            private boolean started = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                curX = motionEvent.getX();
                curY = motionEvent.getY();
                int dx = (int) (mx - curX);
                int dy = (int) (my - curY);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (started) {
                            scrollVertical.scrollBy(0, dy);
                            scrollHoresontal.scrollBy(dx, 0);
                        } else {
                            started = true;
                        }
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        scrollVertical.scrollBy(0, dy);
                        scrollHoresontal.scrollBy(dx, 0);
                        break;
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String url = getResources().getString(R.string.server_api) + "get_absenteeism.php";
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences mPref = view.getContext().getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
        DowlandJsonAbsenteeism dowlandJson = new DowlandJsonAbsenteeism(view, url, modul);
        if (dowlandJson.isOnline()) {
            dowlandJson.setProgressBar(view.findViewById(R.id.progressBar));
            dowlandJson.setData("modul", String.valueOf(modul));
            dowlandJson.setData("group", TypeTimeTable.getGroup());
            dowlandJson.execute();
        } else {
            String json = mPref.getString("absenteeism_" + modul, "");
            if (!json.isEmpty()) {
                ParceJsonAbsenteeism parceJson = new ParceJsonAbsenteeism(json, view, modul);
                parceJson.setProgressBar(view.findViewById(R.id.progressBar));
                parceJson.execute();
            }
        }
    }
}
