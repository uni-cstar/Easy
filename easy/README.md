# Easy
## Release
### v0.3.0
    发布快速的底部导航 ＋ 未读数（数字，红点，图片） ＋ 未读数滑动删除实现。
来个图直观感受一下。

![img](https://github.com/SupLuo/Easy/blob/master/screenshot/ezgif-1-2b815c5201.gif?raw=true)
    
    详情请见:[地址](http://www.jianshu.com/p/077da6bb9ef1)
    
### v0.2
    详情请见:[地址](http://www.jianshu.com/p/14d3b03c2e99)

## Gradle配置
   ```
   allprojects {
       repositories {
           jcenter()
           //在工程的build.gradle文件中添加下述语句
           maven { url 'https://dl.bintray.com/supluo/maven' }
       }
   }
   ```

   ```
   //在Module的build.gradle文件中添加下述语句
    compile 'ms.easy:easy:{latestVersion}'
   ```
