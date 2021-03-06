package com.fanok.mdpu24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fanok.mdpu24.activity.LoginActivity;
import com.fanok.mdpu24.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class StartActivity extends AppCompatActivity {
    public static final String PREF_NAME = "mdpu";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dayStart = "21.01.2019";
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy", Locale.forLanguageTag("UA"));
        Date date;
        int countWeek = 0;
        try {

            date = sdf.parse(dayStart);
            cal1.setTime(date);
            cal2.setTime(new Date());
            countWeek = daysBetween(cal1.getTime(), cal2.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (countWeek % 2 == 0) Week.setWeek(Week.red);
        else Week.setWeek(Week.green);

        SharedPreferences mPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String login = mPref.getString("login", "");

        if (login.isEmpty()) startActivity(new Intent(this, LoginActivity.class));
        else {
            TypeTimeTable.setGroup(mPref.getString("TypeTimeTable_group", ""));
            TypeTimeTable.setType(mPref.getInt("TypeTimeTable_type", 0));
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    private int daysBetween(Date time, Date time1) {
        return (int) ((time1.getTime() - time.getTime()) / (1000 * 60 * 60 * 24 * 7));
    }
}
