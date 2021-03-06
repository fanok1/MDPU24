package com.fanok.mdpu24.adapter;

import android.content.Context;
import android.view.View;

import com.fanok.mdpu24.MyTask;

import java.util.ArrayList;

public class CuratorAdapter extends TaskAdapter {


    public CuratorAdapter(Context context, ArrayList<MyTask> model) {
        super(context, model);
    }

    @Override
    protected void show(Holder holder) {
        holder.name.setVisibility(View.GONE);
    }
}
