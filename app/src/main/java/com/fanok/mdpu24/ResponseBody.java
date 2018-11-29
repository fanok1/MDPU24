package com.fanok.mdpu24;

import android.view.View;

public class ResponseBody {
    private String res;
    private View view;

    public ResponseBody(String res, View view) {
        this.res = res;
        this.view = view;
    }

    public String getRes() {
        return res;
    }

    public View getView() {
        return view;
    }
}
