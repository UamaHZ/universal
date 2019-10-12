# ***UamaUniversal***
## *image-factory*
<pre><code>compile 'com.uama.widget:image-factory:XXX'</code></pre>

日期   |版本     | 更新记录
-------|-------- | -----
2019-08-01   |3.2.7  | 1.修改image-factory provider
2019-09-23   |3.2.9  | 去掉string中添加applicationId的配置，让使用更加简单
2019-09-26   |3.3.0  | 添加没有权限时，跳转到权限设置页面

1. 需要文件读取权限
2. 需要相机权限
### ① 增加图片查看器 ImagePagerActivity
长按可以保存图片
### ② 增加相册图片浏览器 PhotoWallActivity
### ③ 单张图片提交 PhotoChoose
使用方式：在onCreate()里面实例化一个PhotoChoose,
然后重写onActivityResult中调用photoChoose.setImageList(requestCode, resultCode, data);

设置title颜色
<pre><code>public void setTitleColor(int color);</code></pre>
### ④ 4张图片提交 FourPicturesChoose
使用方式：在onCreate()里面实例化一个FourPicturesChoose,其余同上

设置title颜色
<pre><code>public void setTitleColor(int color);</code></pre>
设置checkbox背景
<pre><code>public void setCheckBackground(int color);</code></pre>
设置第一张默认图背景
<pre><code>public void setFirstSelectBg(int res);</code></pre>
设置第二张默认图背景
<pre><code>public void setSecondSelectBg(int res);</code></pre>
### ⑤ 图片压缩工具类ImageSword
使用方式：调用sword()返回file,然后直接上传

## *uama-utils*
### ① 新增通用沉浸式工具类

## *eagle-eye*

<pre><code>compile 'com.uama.widget:eagle-eye:XXX'</code></pre>


日期    |版本     | 更新记录
-------|-------- | --------
2019-07-13  |1.2.8  | 1.升级萤石云版本为4.8.4
2019-08-05  | 1.3.4.2 | 1.targetSdkVersion 22 >> 26 <br> 2.萤石云 4.8.4 >> 4.8.8
        

