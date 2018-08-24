package com.zkp.bettas.module.common.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkp.bettas.R;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.LoadingView;
import com.zkp.bettas.view.danmu.utils.DanmuProcess;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.ScreenResolution;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.ui.widget.DanmakuView;

import static io.vov.vitamio.widget.VideoView.VIDEO_LAYOUT_ORIGIN;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common
 * @time: 2018/8/22 15:00
 * @description:
 */
public class PhoneLiveActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, PhoneView {

    public static final int HIDE_CONTROL_BAR = 0x01;//隐藏控制条
    public static final int SHOW_CENTER_CONTROL = 0x02;//显示中间控制
    public static final int HIDE_TIME = 5000;//隐藏控制条时间
    public static final int SHOW_CONTROL_TIME = 1000;
    public final static int ADD_FLAG = 1;
    public final static int SUB_FLAG = -1;

    private VideoView videoView;
    private FrameLayout flLoading;
    private ImageView ivIcon, ivControl;
    private LoadingView loadingView;
    private TextView tvLoading, tvControl, tvValue;
    private RelativeLayout controlCenter;
    private DanmakuView danMaView;

    private String Room_id;
    private PhonePresenter presenter;

    private DanmuProcess danmuProcess;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener gestureListener;
    private int screenWidth = 0;//屏幕宽度
    private boolean isFullScreen = true;//是否为全屏
    private int showVolume;//声音
    private int showLightness;//亮度
    private int maxVolume;//最大声音
    private AudioManager audioManager;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_CONTROL_BAR:
                    break;
                case SHOW_CENTER_CONTROL:
                    if (controlCenter != null) {
                        controlCenter.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Vitamio.isInitialized(this);

        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_phone_live);

        initView();

        initData();

        initEvents();

        initDanMu(Room_id);

        initVolumeWithLight();
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        flLoading = findViewById(R.id.flLoading);
        ivIcon = findViewById(R.id.ivIcon);
        loadingView = findViewById(R.id.loadingView);
        tvLoading = findViewById(R.id.tvLoading);
        controlCenter = findViewById(R.id.controlCenter);
        ivControl = findViewById(R.id.ivControl);
        tvControl = findViewById(R.id.tvControl);
        tvValue = findViewById(R.id.tvValue);
        danMaView = findViewById(R.id.danMaView);

        //设置保持屏幕常亮
        videoView.setKeepScreenOn(true);
        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
    }

    private void initData() {
        Pair<Integer, Integer> screenPair = ScreenResolution.getResolution(this);
        screenWidth = screenPair.first;
        Room_id = getIntent().getExtras().getString("Room_id");
        presenter = new PhonePresenter();
        presenter.attachView(this);
        presenter.getVideo(Room_id);
    }

    private void initEvents() {
        //添加手势监听
        addTouchListener();

        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnErrorListener(this);
    }

    private void initDanMu(String room_id) {
        danmuProcess = new DanmuProcess(this, danMaView, Integer.valueOf(room_id));
        danmuProcess.start();
    }

    /**
     * 初始化声音和亮度
     */
    private void initVolumeWithLight() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager != null ? audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) : 0;
        showVolume = (audioManager != null ? audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) : 0) * 100 / maxVolume;
        showLightness = getScreenBrightness();
    }

    private void addTouchListener() {
        gestureListener = new GestureDetector.SimpleOnGestureListener() {
            //滑动操作
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (!isFullScreen) {
                    //非全屏不进行手势操作
                    return false;
                }
                float x1 = e1.getX();
                float absDistanceX = Math.abs(distanceX);// distanceX < 0 从左到右
                float absDistanceY = Math.abs(distanceY);// distanceY < 0 从上到下

                // Y方向的距离比X方向的大，即 上下 滑动
                if (absDistanceX < absDistanceY) {
                    if (distanceY > 0) {//向上滑动
                        if (x1 >= screenWidth * 0.65) {//右边调节声音
                            changeVolume(ADD_FLAG);
                        } else {//调节亮度
                            changeLightness(ADD_FLAG);
                        }
                    } else {//向下滑动
                        if (x1 >= screenWidth * 0.65) {
                            changeVolume(SUB_FLAG);
                        } else {
                            changeLightness(SUB_FLAG);
                        }
                    }
                }
                return false;
            }

            //双击事件，有的视频播放器支持双击播放暂停，可从这实现
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            //单击事件
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }
        };
        gestureDetector = new GestureDetector(this, gestureListener);
    }

    /**
     * 获得当前屏幕亮度值 0--255
     */
    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    @SuppressLint("SetTextI18n")
    private void changeVolume(int flag) {
        showVolume += flag;
        if (showVolume > 100) {
            showVolume = 100;
        } else if (showVolume < 0) {
            showVolume = 0;
        }
        tvControl.setText("音量");
        ivControl.setImageResource(R.drawable.img_volume);
        tvValue.setText(showVolume + "%");
        int tagVolume = showVolume * maxVolume / 100;
        //tagVolume:音量绝对值
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tagVolume, 0);
        handler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }

    @SuppressLint("SetTextI18n")
    private void changeLightness(int flag) {
        showLightness += flag;
        if (showLightness > 255) {
            showLightness = 255;
        } else if (showLightness <= 0) {
            showLightness = 0;
        }
        tvControl.setText("亮度");
        ivControl.setImageResource(R.drawable.img_light);
        tvValue.setText(showLightness * 100 / 255 + "%");
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = showLightness / 255f;
        getWindow().setAttributes(lp);

        handler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }

    @Override
    protected void onDestroy() {
        if (videoView != null) {
            //释放资源
            videoView.stopPlayback();
        }
        danmuProcess.finish();
        danMaView.release();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getVideo(Room_id);
        if (videoView != null) {
            videoView.start();
        }
        if (danMaView != null && danmuProcess != null) {
            danMaView.restart();
            danmuProcess.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector != null)
            gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
        if (danMaView != null) {
            danMaView.pause();
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                handler.removeMessages(HIDE_CONTROL_BAR);
                break;
            //完成缓冲
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                flLoading.setVisibility(View.GONE);
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                handler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:

                break;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        flLoading.setVisibility(View.VISIBLE);
        if (videoView.isPlaying()) {
            videoView.pause();
        }
        tvLoading.setText("直播已缓冲" + percent + "%...");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
            ToastUtil.showToast(this, "主播还在赶来的路上~~");
        }
        return false;
    }

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void getVideoSuccess(String data) {
        if (videoView != null) {
            videoView.setVideoURI(Uri.parse(data));
            videoView.setBufferSize(1024 * 1024 * 2);
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    flLoading.setVisibility(View.GONE);
                    handler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                }
            });
        }
    }

    @Override
    public void getVideoError(int error) {
        ToastUtil.showToast(this, "获取直播地址失败");
    }
}
