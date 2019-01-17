<div style="text-align: center;">
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/logo.png" style="margin: 0 auto;" />
</div>

## [文档还是中文好](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_ZH.md)

`Release Version` [![](https://jitpack.io/v/fa-hessari/RangeSeekBar.svg)](https://jitpack.io/#fa-hessari/RangeSeekBar)

<div>
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/demo.gif" height="500px" ><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/vertical_demo.gif" height="500px">
</div>


# Attention

**1/17/2019
Add top and below feature for tick mark text.

**RangeSeekBar v2.x is coming！！！**

**The v2.x's API has a very different from v1.x because I rebuilt the project. I strongly suggest you to use v2.x because it has more powerful functions. If you still want to use v1.x, please to see** [RangeSeekBar v1.x Guide](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_RETIRED.md)

## Usage

### Dependencies

```xml
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.fa-hessari:RangeSeekBar:2.0.6'
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
`VerticalRangeSeekBar` rotates `RangeSeekBar` 90 degrees, and its attribute usage is same as `RangeSeekBar` .The only difference is  the 
`rsb_orientation`, it controls the direction of rotation.

##  Attributes
 If you want to know more attributes's usage , please to see [attrs](https://github.com/Jay-Goo/RangeSeekBar/blob/master/RangeSeekBar/src/main/res/values/attrs.xml)

## WIKI
You can know more usage and information from [wiki](https://github.com/Jay-Goo/RangeSeekBar/wiki).
### [Quickly Start](https://github.com/Jay-Goo/RangeSeekBar/wiki/FAQ)
### [Attribute methods](https://github.com/Jay-Goo/RangeSeekBar/wiki/Attribute-methods)
### [Change Log](https://github.com/Jay-Goo/RangeSeekBar/wiki/ChangeLog)
### [Contributors](https://github.com/Jay-Goo/RangeSeekBar/wiki/Contributors)

## Give me a Star
Hope you like RangeSeekBar. `Star` is the greatest support for me！ Thank you !

## License

Copyright 2018 JayGoo

Licensed under the Apache License.

