package com.zkp.bettas.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 作者：zhoukp
 * 时间：2017/12/22 10:03
 * 邮箱：zhoukaiping@szy.cn
 * 作用：单位转换工具
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp
     * @return px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px
     * @return dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp单位转px float单位
     *
     * @param context 上下文
     * @param dp      dp
     * @return px
     */
    public static float dp2pxF(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * sp单位转px float单位
     *
     * @param context 上下文
     * @param sp      sp
     * @return px
     */
    public static float sp2pxF(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
