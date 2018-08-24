package com.zkp.bettas.utils;

import android.util.Log;

import com.zkp.bettas.BettasApplication;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.base
 * @time: 2018/8/15 10:22
 * @description:
 */
public class RequestUtil {

    //创建网络接口请求实例
    public static <T> T createApi(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://capi.douyucdn.cn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(BettasApplication.genericClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    //执行网络请求
    public static <T> void request(Observable<T> observable, final IResponseListener<T> listener) {
        if (!NetUtil.isConnected(BettasApplication.getContext())) {
            ToastUtil.showToast(BettasApplication.getContext(), "网络不可用,请检查网络");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {

                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Log.d("test", e.getMessage());
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    public interface IResponseListener<T> {
        //请求成功
        void onSuccess(T data);

        //请求失败/出错
        void onFail();
    }

}
