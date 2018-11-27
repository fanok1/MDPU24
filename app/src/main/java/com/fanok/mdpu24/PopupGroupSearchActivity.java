package com.fanok.mdpu24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class PopupGroupSearchActivity extends AppCompatActivity {
    MaterialSearchView searchView;
    ListView listView;
    ArrayAdapter adapter;
    Toolbar toolbar;
    List<String> grouplist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_group_search);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getGroupList();
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;
        getWindow().setAttributes(params);


        toolbar = findViewById(R.id.toolbarPopup);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Выберете группу");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, grouplist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RegistrationActivity.groupName = adapterView.getItemAtPosition(i).toString();
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView.setMenuItem(searchItem);
        searchView.setHint("Поиск...");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> newList = new ArrayList<>();
                for (int i = 0; i < grouplist.size(); i++) {
                    if (grouplist.get(i).contains(newText)) {
                        newList.add(grouplist.get(i));
                    }
                    adapter = new ArrayAdapter(PopupGroupSearchActivity.this, android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void getGroupList() {
        for (int i = 0; i < 100; i++)
            grouplist.add(Integer.toString(i));

    }
}
