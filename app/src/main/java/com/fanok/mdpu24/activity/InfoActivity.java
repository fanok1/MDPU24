package com.fanok.mdpu24.activity;

import com.fanok.mdpu24.R;

public class InfoActivity extends PopupInfoTeacher {
    @Override
    protected void init() {
        getImageView().setImageDrawable(getDrawable(R.drawable.gerb));
        getTextView().setText(getResources().getText(R.string.progam_info));
    }
}
