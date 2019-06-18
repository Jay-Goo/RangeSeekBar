<div style="text-align: center;">
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/logo.png" style="margin: 0 auto;" />
</div>

## [文档还是中文好](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_ZH.md)
<div>
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen1.gif" height="500px" ><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen2.gif" height="500px">
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen3.gif" height="500px" ><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/screen4.gif" height="500px">
</div>

# Attention

**RangeSeekBar Version v3.x is coming!! More powerful and easy to use, and the document will be updated soon. For details, please see [V3.0.0](https://github.com/Jay-Goo/RangeSeekBar/wiki/ChangeLog)**


## Usage

### Dependencies
`Release Version` [![](https://jitpack.io/v/Jay-Goo/RangeSeekBar.svg)](https://jitpack.io/#Jay-Goo/RangeSeekBar)

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

