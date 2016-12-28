# ***UamaUniversal***
##*image-factory*
需要文件读取权限
需要相机权限
###① 增加图片查看器 ImagePagerActivity
长按可以保存图片
###② 增加相册图片浏览器 PhotoWallActivity
###③ 单张图片提交 PhotoChoose
使用方式：在onCreate()里面实例化一个PhotoChoose,
然后重写onActivityResult中调用photoChoose.setImageList(requestCode, resultCode, data);

###④ 4张图片提交 FourPicturesChoose
使用方式：在onCreate()里面实例化一个FourPicturesChoose,其余同上
###⑤ 图片压缩工具类ImageSword
使用方式：调用sword()返回file,然后直接上传

##*uama-utils*
##*share-help*
