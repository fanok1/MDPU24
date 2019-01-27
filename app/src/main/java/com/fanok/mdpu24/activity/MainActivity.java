package com.fanok.mdpu24.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fanok.mdpu24.R;
import com.fanok.mdpu24.StartActivity;
import com.fanok.mdpu24.TypeTimeTable;
import com.fanok.mdpu24.fragment.FragmentAbsenteeism;
import com.fanok.mdpu24.fragment.FragmentAbsenteeismSelect;
import com.fanok.mdpu24.fragment.FragmentMarckSelect;
import com.fanok.mdpu24.fragment.FragmentMarks;
import com.fanok.mdpu24.fragment.FragmentNewsUniversity;
import com.fanok.mdpu24.fragment.FragmentStudentInfo;
import com.fanok.mdpu24.fragment.FragmentTimeTable;
import com.fanok.mdpu24.fragment.FragmentTimeTableSelect;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.name_header);
        TextView login = headerView.findViewById(R.id.login_header);
        TextView textImage = headerView.findViewById(R.id.image_text);
        CircleImageView imageView = headerView.findViewById(R.id.image_header);

        SharedPreferences mPref = getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
        String nameValue = mPref.getString("name", "");
        String loginValue = mPref.getString("login", "");
        String imageValue = mPref.getString("photo", "");
        level = mPref.getInt("level", 0);

        login.setText(loginValue);
        name.setText(nameValue);

        if (!imageValue.equals("null")) {
            Picasso.get().load(imageValue).into(imageView);
            textImage.setVisibility(View.GONE);
        } else if (!nameValue.equals("null")) {
            String text = "" + nameValue.charAt(0) + nameValue.charAt(nameValue.indexOf(" ") + 1);
            textImage.setText(text.toUpperCase());
            imageView.setImageResource(android.support.v4.R.color.secondary_text_default_material_dark);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        MenuItem student = navigationView.getMenu().findItem(R.id.student);
        if (level == 3) student.setEnabled(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentNewsUniversity()).commit();
            navigationView.setCheckedItem(R.id.news);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.timetable) {
            switch (level) {
                case 1:
                case 2:
                    TypeTimeTable.setType(TypeTimeTable.studentTimeTable);
                    break;
                case 3:
                    TypeTimeTable.setType(TypeTimeTable.teacherTimeTable);
                    break;
            }

            if (level == 4)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTimeTableSelect()).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTimeTable()).commit();
        } else if (id == R.id.student) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentStudentInfo()).commit();
        } else if (id == R.id.marks) {
            switch (level) {
                case 1:
                    TypeTimeTable.setType(TypeTimeTable.studentTimeTable);
                    break;
                case 2:
                    TypeTimeTable.setType(TypeTimeTable.starostaTimeTable);
                    break;
                case 3:
                    TypeTimeTable.setType(TypeTimeTable.teacherTimeTable);
                    break;
                case 4:
                    TypeTimeTable.setType(TypeTimeTable.curatorTimeTable);
                    break;
            }
            if (level == 3 || level == 4) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMarckSelect()).commit();
            } else
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMarks()).commit();

        } else if (id == R.id.absenteeism) {
            switch (level) {
                case 1:
                    TypeTimeTable.setType(TypeTimeTable.studentTimeTable);
                    break;
                case 2:
                    TypeTimeTable.setType(TypeTimeTable.starostaTimeTable);
                    break;
                case 3:
                    TypeTimeTable.setType(TypeTimeTable.teacherTimeTable);
                    break;
                case 4:
                    TypeTimeTable.setType(TypeTimeTable.curatorTimeTable);
                    break;
            }
            if (level != 3) {
                if (level == 4)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbsenteeismSelect()).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbsenteeism()).commit();
            }

        } else if (id == R.id.chat) {

        } else if (id == R.id.tasks) {

        } else if (id == R.id.curator) {

        } else if (id == R.id.news) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentNewsUniversity()).commit();
        } else if (id == R.id.grafic) {

        } else if (id == R.id.setngs) {

        } else if (id == R.id.info) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
