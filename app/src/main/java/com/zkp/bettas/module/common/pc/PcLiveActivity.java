package com.zkp.bettas.module.common.pc;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkp.bettas.R;
import com.zkp.bettas.module.common.phone.bean.LiveBean;
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
 * @package: com.zkp.bettas.module.common.pc
 * @time: 2018/8/23 14:33
 * @description:
 */
public class PcLiveActivity extends AppCompatActivity implements PcView, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, View.OnClickListener {

    public static final int HIDE_CONTROL_BAR = 0x01;//隐藏控制条
    public static final int SHOW_CENTER_CONTROL = 0x02;//显示中间控制
    public static final int HIDE_TIME = 5000;//隐藏控制条时间
    public static final int SHOW_CONTROL_TIME = 1000;
    public final static int ADD_FLAG = 1;
    public final static int SUB_FLAG = -1;

    private VideoView videoView;
    private DanmakuView danMaView;
    private ImageView ivBack, ivSetting, ivGift, ivShare, ivFollow, ivPlay, ivRefresh, ivDanMu, ivHotWord, ivSend, ivControl;
    private TextView tvNickname, tvList, tvLoading, tvControl, tvValue;
    private EditText etComment;
    private LoadingView loadingView;
    private FrameLayout flLoading;
    private RelativeLayout controlCenter, controlTop, controlBottom;

    private String Room_id, roomName;
    private PcPresenter presenter;
    private DanmuProcess danmuProcess;

    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener gestureListener;
    private int screenWidth = 0;//屏幕宽度
    private boolean isFullScreen = true;//是否为全屏
    private int showVolume;//声音
    private int showLightness;//亮度
    private int maxVolume;//最大声音
    private AudioManager audioManager;
    //弹幕控制开关 默认打开状态
    private boolean danmuControlFalg = true;

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
        //初始化Vitamio
        Vitamio.isInitialized(this);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_pc_live);

        initView();

        initData();

        initEvents();

        initDanMu(Room_id);

        initVolumeWithLight();
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        danMaView = findViewById(R.id.danMaView);
        ivBack = findViewById(R.id.ivBack);
        tvNickname = findViewById(R.id.tvNickname);
        ivSetting = findViewById(R.id.ivSetting);
        ivGift = findViewById(R.id.ivGift);
        ivShare = findViewById(R.id.ivShare);
        ivFollow = findViewById(R.id.ivFollow);
        ivPlay = findViewById(R.id.ivPlay);
        ivRefresh = findViewById(R.id.ivRefresh);
        tvList = findViewById(R.id.tvList);
        ivDanMu = findViewById(R.id.ivDanMu);
        ivHotWord = findViewById(R.id.ivHotWord);
        etComment = findViewById(R.id.etComment);
        ivSend = findViewById(R.id.ivSend);
        loadingView = findViewById(R.id.loadingView);
        tvLoading = findViewById(R.id.tvLoading);
        ivControl = findViewById(R.id.ivControl);
        tvControl = findViewById(R.id.tvControl);
        tvValue = findViewById(R.id.tvValue);
        flLoading = findViewById(R.id.flLoading);
        controlCenter = findViewById(R.id.controlCenter);
        controlTop = findViewById(R.id.controlTop);
        controlBottom = findViewById(R.id.controlBottom);

        //设置保持屏幕常亮
        videoView.setKeepScreenOn(true);
        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
    }

    private void initData() {

        Pair<Integer, Integer> screenPair = ScreenResolution.getResolution(this);
        screenWidth = screenPair.first;

        Room_id = getIntent().getExtras().getString("Room_id");
        roomName = getIntent().getExtras().getString("roomName");

        presenter = new PcPresenter();
        presenter.attachView(this);
        presenter.getVideo(Room_id);
    }

    private void initEvents() {
        //添加手势监听
        addTouchListener();

        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnErrorListener(this);

        ivBack.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivDanMu.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);
    }

    private void initDanMu(String roomId) {
        danmuProcess = new DanmuProcess(this, danMaView, Integer.valueOf(roomId));
        danmuProcess.start();
    }

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
                if (controlBottom.getVisibility() == View.VISIBLE) {
                    handler.removeMessages(HIDE_CONTROL_BAR);
                    hideControlBar();
                } else {
                    showControlBar();
                    handler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                }
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

    private void hideControlBar() {
        if (controlBottom != null && controlTop != null) {
            controlBottom.setVisibility(View.GONE);
            controlTop.setVisibility(View.GONE);
        }
    }

    private void showControlBar() {
        if (controlBottom != null && controlTop != null) {
            controlBottom.setVisibility(View.VISIBLE);
            controlTop.setVisibility(View.VISIBLE);
        }
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
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);
        }
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
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void getVideoSuccess(LiveBean liveBean) {
        if (tvNickname != null) {
            tvNickname.setText(roomName);
        }
        if (videoView != null) {
            videoView.setVideoURI(Uri.parse(liveBean.getData().getHls_url()));
            videoView.setBufferSize(1024 * 1024 * 2);
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    flLoading.setVisibility(View.GONE);
                    ivPlay.setImageResource(R.drawable.img_live_videopause);
                    handler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                }
            });
        }
    }

    @Override
    public void getVideoError(int error) {
        ToastUtil.showToast(this, "获取直播地址失败");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                ivPlay.setImageResource(R.drawable.img_live_videoplay);
                handler.removeMessages(HIDE_CONTROL_BAR);
                showControlBar();
                break;
            //完成缓冲
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                flLoading.setVisibility(View.GONE);
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                ivPlay.setImageResource(R.drawable.img_live_videopause);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivPlay:
                pauseAndPlay();
                break;
            case R.id.ivDanMu:
                openAndCloseDanmu();
                break;
            case R.id.ivRefresh:
                refresh();
                break;
        }
    }

    private void pauseAndPlay() {
        if (videoView.isPlaying()) {
            videoView.pause();
            ivPlay.setImageResource(R.drawable.img_live_videoplay);
            handler.removeMessages(HIDE_CONTROL_BAR);
            showControlBar();
        } else {
            videoView.start();
            ivPlay.setImageResource(R.drawable.img_live_videopause);
            handler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
        }
    }

    private void openAndCloseDanmu() {
        if (danmuControlFalg) {
            //隐藏弹幕
            danMaView.hide();
            ivDanMu.setImageResource(R.drawable.pad_play_closedanmu);
            danmuControlFalg = false;
        } else {
            //开启弹幕
            danMaView.show();
            ivDanMu.setImageResource(R.drawable.pad_play_opendanmu);
            danmuControlFalg = true;
        }
    }

    private void refresh() {
        presenter.getVideo(Room_id);
    }
}
