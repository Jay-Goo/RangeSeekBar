<div style="text-align: center;">
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/logo.png" style="margin: 0 auto;" />
</div>

<div>
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen1.gif" height="500px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen2.gif" height="500px"><br/>
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen3.gif" height="500px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen4.gif" height="500px">
</div>

# 注意


**RangeSeekBar  v3.x 即将来临!! 更强大，更好用！ 文档和新版本即将更新，具体更新内容请看 [V3.0.0](https://github.com/Jay-Goo/RangeSeekBar/wiki/ChangeLog)**
**
## 用法

### Dependencies
`Release Version`[![](https://jitpack.io/v/Jay-Goo/RangeSeekBar.svg)](https://jitpack.io/#Jay-Goo/RangeSeekBar)

	
```xml
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
		// sample: implementation 'com.github.Jay-Goo:RangeSeekBar:v2.0.4'
	        implementation 'com.github.Jay-Goo:RangeSeekBar:Release Version'
	}

```


### RangeSeekBar
```
 <com.jaygoo.widget.RangeSeekBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:rsb_mode="single"
        />
```

### VerticalRangeSeekBar
```
  <com.jaygoo.widget.VerticalRangeSeekBar
        android:layout_width="50dp"
        android:layout_height="300dp"
        app:rsb_mode="range"
	app:rsb_orientation="right"
        />
```
你可以认为`VerticalRangeSeekBar` 是 `RangeSeekBar` 旋转90度得到的,它和 `RangeSeekBar` 的属性用法一致.唯一的不同就是 
`rsb_orientation`，它是用来控制旋转的方向的.

##  属性
 如果你想了解更多地属性，请参看 [attrs](https://github.com/Jay-Goo/RangeSeekBar/blob/master/RangeSeekBar/src/main/res/values/attrs.xml)

## WIKI
你可以从[wiki](https://github.com/Jay-Goo/RangeSeekBar/wiki)获取更多的信息和用法.
### [快速入门](https://github.com/Jay-Goo/RangeSeekBar/wiki/FAQ)
### [属性方法](https://github.com/Jay-Goo/RangeSeekBar/wiki/Attribute-methods)
### [更新日志](https://github.com/Jay-Goo/RangeSeekBar/wiki/ChangeLog)
### [贡献者](https://github.com/Jay-Goo/RangeSeekBar/wiki/Contributors)

## 给我个Star吧
真心希望你能喜欢RangeSeekBar， `Star` 是对我最大的支持，谢谢！

## License

Copyright 2018 JayGoo

Licensed under the Apache License.

