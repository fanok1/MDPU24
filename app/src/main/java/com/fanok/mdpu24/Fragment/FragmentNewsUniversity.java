package com.fanok.mdpu24.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fanok.mdpu24.DowlandNews;
import com.fanok.mdpu24.InsertDataInSql;
import com.fanok.mdpu24.R;

import java.util.concurrent.ExecutionException;

public class FragmentNewsUniversity extends android.support.v4.app.Fragment {

    private int offset = 10;
    private int step = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_university, container, false);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        final String URL = getResources().getString(R.string.login_api);
        DowlandNews dowlandNews = new DowlandNews(view, URL);

        dowlandNews.setProgressBar(progressBar);
        dowlandNews.setData("action", "load_more");
        dowlandNews.setData("post_style", "timeline");
        dowlandNews.setData("eael_show_image", "1");
        dowlandNews.setData("image_size", "medium");
        dowlandNews.setData("eael_show_title", "1");
        dowlandNews.setData("eael_show_excerpt", "0");
        dowlandNews.setData("eael_excerpt_length", String.valueOf(step)); //count
        dowlandNews.setData("post_type", "post");
        dowlandNews.setData("posts_per_page", "10");
        dowlandNews.setData("offset", "0"); //отступ
        if (new InsertDataInSql(view, null).isOnline())
            dowlandNews.execute();
        else
            Toast.makeText(view.getContext(), getResources().getString(R.string.error_no_internet_conection), Toast.LENGTH_SHORT).show();



        return view;
    }



}
