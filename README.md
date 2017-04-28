# ***UamaUniversal***
## *image-factory*
1. 需要文件读取权限
2. 需要相机权限
### ① 增加图片查看器 ImagePagerActivity
长按可以保存图片
### ② 增加相册图片浏览器 PhotoWallActivity
### ③ 单张图片提交 PhotoChoose
使用方式：在onCreate()里面实例化一个PhotoChoose,
然后重写onActivityResult中调用photoChoose.setImageList(requestCode, resultCode, data);

### ④ 4张图片提交 FourPicturesChoose
使用方式：在onCreate()里面实例化一个FourPicturesChoose,其余同上
### ⑤ 图片压缩工具类ImageSword
使用方式：调用sword()返回file,然后直接上传

## *uama-utils*
### ① 新增通用沉浸式工具类
## *share-factory*
分享注意，使用最新版友盟
###
1. 分离了微信支付的jar包，需要导入支付jar包，libammsdk.jar
2. 使用远程依赖xxx，删除umeng_social_sdk.jar，本地social_sdk_library_project依赖
3. 支持图片/纯文本/自定义分享功能
4. 支持自定义样式or直接引用目前的分享view（可修改图片，文字颜色，显示隐藏短信分享，默认显示短信分享）

需要文件读取权限
###
1. 创建并注测WXEntryActivity，并在mainfest中初始化友盟key
2. Application中初始化QQ或者微信key
3. 布局中引入ShreView,在activity中使用setDefaultJPMap()/setDefaultShareContentWithMsg(),将需分享的内容放入。ps：开放了自定义分享，只用使用setInterShare（）实现InterShare即可；
4. 分享时，让控件可见即可。

