package com.zkp.bettas.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zkp.bettas.R;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.utils
 * @time: 2018/8/15 10:27
 * @description:
 */
public class ToastUtil {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        public void run() {
            mToast.cancel();
            //toast隐藏后，将其置为null
            mToast = null;
        }
    };

    public static void showToast(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView text = view.findViewById(R.id.toastMessage);
        //显示的提示文字
        text.setText(message);
        mHandler.removeCallbacks(runnable);
        mToast = new Toast(context);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.show();
    }

}
