package com.zkp.bettas.view.danmu.client;

import android.util.Log;

import com.zkp.bettas.view.danmu.msg.DyMessage;
import com.zkp.bettas.view.danmu.msg.MsgView;
import com.zkp.bettas.view.danmu.utils.KeepAlive;
import com.zkp.bettas.view.danmu.utils.KeepGetMsg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

public class DyBulletScreenClient {

    private static final String TAG = "DyBulletScreenClient";
    //第三方弹幕协议服务器地址
    private static final String hostName = "openbarrage.douyutv.com";
    //第三方弹幕协议服务器端口
    private static final int port = 8601;
    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 8192;
    private static DyBulletScreenClient instance;
    //socket相关配置
    private Socket sock;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    //获取弹幕线程及心跳线程运行和停止标记
    private boolean readyFlag = false;
    private HandleMsgListener mHandleMsgListener = null;

    private DyBulletScreenClient() {
    }

    /**
     * 单例获取方法，客户端单例模式访问
     */
    public static DyBulletScreenClient getInstance() {
        if (null == instance) {
            instance = new DyBulletScreenClient();
        }
        return instance;
    }

    public void setmHandleMsgListener(HandleMsgListener mHandleMsgListener) {
        this.mHandleMsgListener = mHandleMsgListener;
    }

    public void start(int roomId, int groupId) {
        init(roomId, groupId);

        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();

        KeepGetMsg keepGetMsg = new KeepGetMsg();
        keepGetMsg.start();
    }

    /**
     * 客户端初始化，连接弹幕服务器并登陆房间及弹幕池
     *
     * @param roomId  房间ID
     * @param groupId 弹幕池分组ID
     */
    public void init(int roomId, int groupId) {
        //连接弹幕服务器
        this.connectServer();
        //登陆指定房间
        this.loginRoom(roomId);
        //加入指定的弹幕池
        this.joinGroup(roomId, groupId);
        //设置客户端就绪标记为就绪状态
        readyFlag = true;
    }

    /**
     * 连接弹幕服务器
     */
    private void connectServer() {
        try {
            //获取弹幕服务器访问host
            String host = InetAddress.getByName(hostName).getHostAddress();
            //建立socke连接
            sock = new Socket(host, port);
            //设置socket输入及输出
            bos = new BufferedOutputStream(sock.getOutputStream());
            bis = new BufferedInputStream(sock.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录指定房间
     *
     * @param roomId roomId
     */
    private void loginRoom(int roomId) {
        //获取弹幕服务器登陆请求数据包
        byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);


        try {
            //发送登陆请求数据包给弹幕服务器
            bos.write(loginRequestData, 0, loginRequestData.length);
            bos.flush();

            //初始化弹幕服务器返回值读取包大小
            byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
            //获取弹幕服务器返回值
            bis.read(recvByte, 0, recvByte.length);

            //解析服务器返回的登录信息
            if (DyMessage.parseLoginRespond(recvByte)) {
                Log.d(TAG, "loginRoom: Receive login response successfully!");
            } else {
                Log.d(TAG, "loginRoom: Receive login response failed!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入弹幕分组池
     *
     * @param roomId  roomId
     * @param groupId groupId
     */
    private void joinGroup(int roomId, int groupId) {
        //获取弹幕服务器加弹幕池请求数据包
        byte[] joinGroupRequest = DyMessage.getJoinGroupRequest(roomId, groupId);

        try {
            //想弹幕服务器发送加入弹幕池请求数据
            bos.write(joinGroupRequest, 0, joinGroupRequest.length);
            bos.flush();
            Log.d(TAG, "joinGroup: Send join group request successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "joinGroup: Send join group request failed!");
        }
    }

    public void stop() {
        unInit();
    }

    public void unInit() {
        readyFlag = false;
    }

    /**
     * 获取弹幕客户端就绪标记
     *
     * @return
     */
    public boolean getReadyFlag() {
        return readyFlag;
    }

    /**
     * 服务器心跳连接
     */
    public void keepAlive() {
        //获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int) (System.currentTimeMillis() / 1000));

        try {
            //向弹幕服务器发送心跳请求数据包
            bos.write(keepAliveRequest, 0, keepAliveRequest.length);
            bos.flush();
            Log.d(TAG, "keepAlive: Send keep alive request successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "keepAlive: Send keep alive request failed!");
        }
    }

    /**
     * 获取服务器返回信息
     */
    public void getServerMsg() {
        //初始化获取弹幕服务器返回信息包大小
        byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
        //定义服务器返回信息的字符串
        String dataStr;
        try {
            //读取服务器返回信息，并获取返回信息的整体字节长度
            int recvLen = bis.read(recvByte, 0, recvByte.length);

            //根据实际获取的字节数初始化返回信息内容长度
            byte[] realBuf = new byte[recvLen];
            //按照实际获取的字节长度读取返回信息
            System.arraycopy(recvByte, 0, realBuf, 0, recvLen);
            //根据TCP协议获取返回信息中的字符串信息
            dataStr = new String(realBuf, 12, realBuf.length - 12);
            //对单一数据包进行解析
            MsgView msgView = new MsgView(dataStr);
            //分析该包的数据类型，以及根据需要进行业务操作
            parseServerMsg(msgView.getMessageList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析从服务器接受的协议，并根据需要订制业务需求
     *
     * @param msg msg
     */
    private void parseServerMsg(Map<String, Object> msg) {
        if (msg.get("type") != null) {

            //服务器反馈错误信息
            if (msg.get("type").equals("error")) {
                Log.d(TAG, "parseServerMsg: msg.toString()=" + msg.toString());
                //结束心跳和获取弹幕线程
                this.readyFlag = false;
            }

            //判断消息类型
            if (msg.get("type").equals("chatmsg")) {//弹幕消息
                if (msg.containsKey("txt")) {
                    if (!isHandleMsgListenerNull()) {
                        mHandleMsgListener.handleMessage(msg.get("txt").toString());
                    }
                }
            }
        }
    }

    private boolean isHandleMsgListenerNull() {
        return mHandleMsgListener == null;
    }

    public interface HandleMsgListener {
        void handleMessage(String txt);
    }
}
