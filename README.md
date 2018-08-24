# 斗鱼Tv 首页API
 `android` `java` `移动端开发`
 
#### 版权说明：该版本为抓取Android手机客户端2.4.1.1版本API接口，该项目纯属练手，不能作为商用项目！违反上述所有，涉及商用侵权与本人无关！
[TOC]

# 目录结构
>
 * ------------------------[栏目分类](#1.0.1)
 * ------------------------[分类详情](#1.0.2)
 * ------------------------[推荐页--轮播图](#1.0.3)
 * ------------------------[推荐页--栏目列表](#1.0.4)
 * ------------------------[推荐页--最热栏目](#1.0.5)
 * ------------------------[推荐页--颜值栏目](#1.0.6)
 * ------------------------[直播房间详情页](#1.0.7)
 * ------------------------[服务器时间戳](#1.0.8)
 * ------------------------[用户协议](#1.0.9)
 * ------------------------[栏目更多--全部](#1.1.0)
 * ------------------------[栏目更多--二级分类列表](#1.1.1)
 * ------------------------[栏目更多--二级直播视频列表](#1.1.2)

<h4 id="1.0.1">1.栏目分类</h4>
>
* 接口描述： 获取首页分类信息
* 请求URL：http://capi.douyucdn.cn/api/homeCate/getCateList
* 请求方式：Get
* 请求参数：
*        client_sys：设备类型 默认为：Android

 
<h4 id="1.0.2">2.分类详情</h4>
>
* 接口描述： 获取首页列表详情接口
* 请求URL：http://capi.douyucdn.cn/api/homeCate/getHotRoom?aid=android1&client_sys=android&identification=3e760da75be261a588c74c4830632360&time=1480493599&auth=b455ecdc27fd82acc4f5ed771d015a3d
* 请求方式：Get
* 请求参数：
*        client_sys：设备类型 默认为：Android
*        identification：分类ID
*        time: 时间戳
*        auth：32随机数

<h4 id="1.0.3">3.推荐页面轮播图接口</h4>
>
* 接口描述： 首页推荐页面轮播图接口
* 请求URL：http://capi.douyucdn.cn/api/v1/slide/6?version=2.421&client_sys=android
* 请求方式：Get
* 请求参数：
*        version：版本号
*        client_sys：设备类型

 
<h4 id="1.0.4">4.推荐页--栏目列表</h4>
>
* 接口描述：获取到推荐页面 如英雄联盟，户外，数码科技等栏目
* 请求URL：http://capi.douyucdn.cn/api/v1/getHotCate?aid=android1&client_sys=android&time=1480500054&token=89175431_12_cd2cb4963d259081_1_54371072&auth=19ca301d4523a0af1c4945887ae0b03e
* 请求方式：Get
* 请求参数：
*        client_sys：设备类型 默认为：Android
*        time：时间戳
*        token：登录后token值
*        auth：


 <h4 id="1.0.5">5.推荐页--最热栏目</h4>
>
* 接口描述：首页--推荐页--最热栏目
* 请求URL：http://capi.douyucdn.cn/api/v1/getbigDataRoom?aid=android1&client_sys=android&time=1480501445&token=89175431_12_cd2cb4963d259081_1_54371072&auth=43b3facb607f9f0251a49182491bdceb
* 请求方式：Get
* 请求参数：
*        client_sys：设备类型 默认为：Android
*        time：时间戳
*        token：登录后token值
*        auth：
* 返回数据:

 <h4 id="1.0.6">6.推荐页--颜值栏目 </h4>
>
* 接口描述：首页--推荐页--颜值栏目 本栏目做全屏直播 单独跳转到全屏直播详情页
* 请求URL：http://capi.douyucdn.cn/api/v1/getVerticalRoom?offset=0&limit=4&client_sys=android
* 请求方式：Get
* 请求参数：
*        client_sys：设备类型 默认为：Android
*        offset：从第几个开始  （分页加载数据）
*        limit： 到那个结束
* 返回数据：

 <h4 id="1.0.7">7.直播房间详情页</h4>
>
* 接口描述：直播房间详情页---
* 请求URL：http://capi.douyucdn.cn/api/html5/live?
* 请求方式：Get
* 请求参数： 
*        roomid:房间ID
* 返回数据：

 
<h4 id="1.0.8">8.获取服务器端时间戳</h4>
>
* 接口描述： 获取服务端时间戳
* 请求URL：http://capi.douyucdn.cn/api/v1/timestamp
* 请求方式：Get
* 返回数据:

<h4 id="1.0.9">9.用户注册协议</h4>
>
* 接口描述： 用户注册协议
* 请求URL：http://www.douyu.com/protocal/client

 ---
 
 
<h4 id="1.1.0">10.栏目--全部视频</h4>
>
* 接口描述： 获取某个栏目中的全部视频 
* 请求URL：http://capi.douyucdn.cn/api/v1/live/1?offset=0&limit=20&client_sys=android
* 请求方式：Get
* 请求参数：
*        1  ---> tag_id
*        client_sys：设备类型 默认为：Android
*        offset：从第几个开始  （分页加载数据）
*        limit： 到那个结束
* 返回数据:

<h4 id="1.1.1">11.栏目更多--二级分类列表</h4>
>
* 接口描述： 获取首页分类信息
* 请求URL：http://capi.douyucdn.cn/api/v1/getThreeCate?tag_id=1&client_sys=android
* 请求方式：Get
* 请求参数：
*        tag_id：父类ID
*        client_sys：设备类型 默认为：Android
* 返回数据:

  
<h4 id="1.1.2">12.二级栏目直播列表</h4>
>
* 接口描述： 获取首页分类信息
* 请求URL：http://capi.douyucdn.cn/api/v1/getThreeList?cate_id=168&offset=0&limit=20&client_sys=android
* 请求方式：Get
* 请求参数：
*        cate_id：分类ID
*        client_sys：设备类型 默认为：Android
