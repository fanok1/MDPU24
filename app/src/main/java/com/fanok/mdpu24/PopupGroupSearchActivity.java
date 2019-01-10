package com.fanok.mdpu24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class PopupGroupSearchActivity extends AppCompatActivity {
    private MaterialSearchView searchView;
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> grouplist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_group_search);

        final String url = getResources().getString(R.string.server_api) + "groups_get.php";
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;
        getWindow().setAttributes(params);


        Toolbar toolbar = findViewById(R.id.toolbarPopup);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Выберете группу");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finishAfterTransition());

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);

        DowlandGroupsName groups = new DowlandGroupsName(findViewById(R.id.popup), url, listView, grouplist);
        if (groups.isOnline()) {
            groups.setProgressBar(findViewById(R.id.progressBarGroup));
            groups.execute();
        }

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            RegistrationActivity.groupName = adapterView.getItemAtPosition(i).toString();
            finish();
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
}
