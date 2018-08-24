package com.zkp.bettas.module.common.phone;

import android.text.TextUtils;

import com.zkp.bettas.module.common.phone.bean.LiveBean;
import com.zkp.bettas.utils.LiveUtil;
import com.zkp.bettas.utils.RequestUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.phone
 * @time: 2018/8/22 16:29
 * @description:
 */
public class PhonePresenter implements PhoneBiz {

    private PhoneView phoneView;

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void attachView(PhoneView phoneView) {
        if (this.phoneView == null) {
            this.phoneView = phoneView;
        }
    }

    @Override
    public void getVideo(String roomId) {

        if (phoneView != null) {
            phoneView.showProgress();

            LiveUtil.request(LiveUtil.createApi(PhoneApi.class).getVideo(roomId),
                    new LiveUtil.IResponseListener<LiveBean>() {
                        @Override
                        public void onSuccess(LiveBean data) {
                            phoneView.getVideoSuccess(data.getData().getHls_url());
                            phoneView.showProgress();
                        }

                        @Override
                        public void onFail() {
                            phoneView.getVideoError(-1);
                            phoneView.showProgress();
                        }
                    });
        }

    }

    public void detachView() {
        if (this.phoneView != null) {
            this.phoneView = null;
        }
    }
}
