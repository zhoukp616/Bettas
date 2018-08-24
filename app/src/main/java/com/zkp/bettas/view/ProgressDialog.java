package com.zkp.bettas.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zkp.bettas.R;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.view
 * @time: 2018/8/15 14:38
 * @description:
 */
public class ProgressDialog extends Dialog {
    private TextView mMesssageTV;

    public ProgressDialog(Context context) {
        super(context, R.style.transparent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mMesssageTV = findViewById(R.id.messagetv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showMessage(String message) {
        try {
            super.show();
            if (!TextUtils.isEmpty(message)) {
                mMesssageTV.setText(message);
                mMesssageTV.setVisibility(View.VISIBLE);
            } else {
                mMesssageTV.setText("");
                mMesssageTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
