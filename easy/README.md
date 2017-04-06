# Easy
## Release
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
    compile 'ms.easy:easy:0.2.0'
   ```