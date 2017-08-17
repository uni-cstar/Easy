# Skin latestVersion = 1.2.3

  Skin是一款提供在Android开发中实现动态快速切换皮肤的库。

  ## 感谢
  Skin是站在巨人的肩膀上，其核心思想来源于[Android-Skin-Loader](https://github.com/SupLuo/Android-Skin-Loader)，并且借用了洪洋（[地址](https://github.com/hongyangAndroid/ChangeSkin)）通过后缀切换属性的思路。
  在这里对两位表示深深的感谢。
  ## 与其它库的不同
  Skin的核心思路来源于前面提到的两位作者的思想，但并不是ctrl+C&V的结果，我仅仅是基于两者的思想进行实现，有些地方是参照了原作者的写法，但更多的是我自身对这个思想的理解。
  Skin由于经过重构和再设计，因此理论上是比原有的库更具有特点，实际上也是如此。

  ## 支持功能
    支持动态换肤
        任何一个地方触发换肤，程序中所有相关界面会自动切换，已加载界面不需要重启
    支持外置皮肤文件
        可以以文件形式提供外置皮肤包进行加载，可以是asset中的文件，也可以是外置sd卡中的文件
    支持字体切换
        提供asset中字体切换和外置皮肤文件字体切换
    支持多达十几种常用属性换肤支持，并支持换肤属性扩展及自定义
        默认实现text，textColor，background，src，tint系列，tablayout等等
    支持各种灵活配置
    支持三种换肤策略
          Skin提供三种换肤方式：前缀属性／标记属性／前缀＋标记属性。
          前缀属性：只需将需要换肤的属性统一以固定的前缀开始命名即可；eg. skin_login_btn_bg.png。
          标记属性：在布局文件中进行命名空间申明，并为需要进行皮肤切换的控件设置属性标记。
          前缀＋标记属性：即前两种方式的综合，换肤的资源必须以特定标记作为前缀并且使用资源的控件必须增加标记（标记的介绍具体请见wiki.）
          这三种种方式都支持属性后缀切换，也就是在同一套皮肤中（可以是外置皮肤文件），可以提供不同后缀结尾的属性来达到快速的皮肤切换。



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
    compile 'ms.easy:skin:1.2.3'
   ```

    如有问题，可联系：QQ862638161

来个图直观感受一下。<br/>
![img](https://github.com/SupLuo/Easy/blob/master/skin/sample_iamge.gif?raw=true)

发布日志：
  ## beta v1.2.0-beta
     * fix imageview tint属性的处理，如果程序要在5.0版本以前支持imageview的tint属性，只需要自定义tint属性，并将控件的android:tint更改为app:tint即可支持
  ## beta v1.1.6-bata
     * 去掉library层自动保存换肤参数，这一层的实现应该由客户端业务层逻辑实现。

  ## beta v1.1.6-bata
        完善SkinResourceManager的资源获取
  ## beta v1.1.4-beta
     * 新增在解析布局文件时，如果ui符合换肤规则，在创建view的时候主动执行应用换肤属性，以解决某些兼容属性不生效问题（例如drawableTint）的方法
     调用SkinManager#setApplySkinAttrWhenCreateSkinView(boolean value)使设置生效
     修改view的引用为软引用

     * fix @null 资源的处理问题
     * 解决tint属性换肤的兼容性问题


  ## Release v1.0.5
     * 新增DrawableTint属性支持
        如需应用此属性，请调用`DrawableTintAttr.addToSupportAttr()`方法
     * BaseSkinActivity 默认增加皮肤和字体切换的回调方法实现，如果需要实现自己的处理，则可以重写对应回调方法
        具体重写BaseSkinActivity的`onSkinChanged()`和`onSkinFontChanged()`方法。

  ## Release v1.0.4
     * 新增字体切换支持
       提供程序内以及皮肤包中字体切换，要求是字体文件路径必须为assets/fonts/{xxx.ttf}
     * 使用步骤
        `SkinManager`下提供了更改字体的相关方法
        启用功能，默认关闭
       `public void setEnableFontChange(boolean enable)`

       切换字体
       `public void changeFont(String fontName)`
       `public void changeFont(String fontName, @FontType int fontType)`
       还原默认字体
       `public void restoreDefaultFont()`
  ## Release v1.0.3
     * 新增动态View换肤支持
       `ISkinDelegate.addSkinView(View view, List<SkinAttr> skinAttrs)`
       只需在使用中调用`SkinDelegate` 或者 `BaseSkinActivity` 与 `BaseSkinFragment`中等同的方法添加即可。

     * Fragment换肤完善
       具体依靠`BaseSkinFragment`处理

     * 新增额外属性支持android:text
       默认没有添加到支持的集合中，因为可能不是常用的属性，如需使用，在初始化`SkinManager`之后调用`SkinAttrSupport.addExtraSupportAttr()`方法添加
       也可根据自己需要添加到`SkinAttrSupport`中。

  ## Release v1.0.1

     * 修正style属性解析bug
     * 前缀属性模式申明：
      前缀属性解析方式是根据资源名字的前缀来区分的，注意资源连续引用的情况：

      //假如有以下定义
      ```
      <color name="skin_sample_button_1_text_color">@android:color/white</color>
      <style name="StyleText">
           <item name="android:textColor">@color/skin_sample_button_1_text_color</item>
           <item name="android:background">@color/skin_sample_button_1_bg_color</item>
      </style>
      ```

      当一个控件引用上述style，并且是前缀属性解析方式，哪么最终结果是会忽略此控件的textColor换肤属性，因为textColor的资源是一个连续引用，在解析style之后，得到的资源结果最终是
      `android:textColor="@android:color/white"`,资源值为white，不符合资源前缀属性解析规则，所以在这种情况下这个属性会被忽略。
      解决办法就是避免连续引用，或者保证最后的资源引用符合前缀属性解析规则即可。
      例如：`<color name="skin_sample_button_1_text_color">#FFFFFF</color>`.
      如果是多次连续引用，比如A指向B，B指向C，C指向D，哪么只需要保证D是以换肤前缀开头即可。

  ## Release v1.0.0
     * 支持外置皮肤插件文件
     * 支持两种换肤方式：前缀属性和标记属性。
     * 支持同一套皮肤中通过后缀名快速切换皮肤。
     * 提供textColor/background/src三个属性的缺省实现，可以自定义扩展。
     * 支持View切换皮肤时的动画控制。
     * 支持皮肤中找不到资源时使用默认皮肤中的资源代替。
