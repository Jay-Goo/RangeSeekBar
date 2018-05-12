
# RangeSeekBar

## [文档还是中文好](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_ZH.md)

<div style="float:left;margin:2px;"><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/demo.gif"  width="283" height="500" ></div>

<div style="float:left;margin:2px;"><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/vertical_demo.gif"  width="283" height="500" ></div>

# Attention

**RangeSeekBar v2.x is coming！！！**

**The v2.x's API has a very different from v1.x because I rebuilt the project. I strongly suggest you to use v2.x because it has more powerful functions. If you still want to use v1.x, please to see** [RangeSeekBar v1.x Guide](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_RETIRED.md)

## Usage

### dependencies：

```xml
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        compile 'com.github.Jay-Goo:RangeSeekBar:v2.0.0'
	}

```


### RangeSeekBar：
```
 <com.jaygoo.widget.RangeSeekBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:rsb_mode="single"
        />
```

### VerticalRangeSeekBar：
```
  <com.jaygoo.widget.VerticalRangeSeekBar
        android:layout_width="50dp"
        android:layout_height="300dp"
        app:rsb_mode="range"
	app:rsb_orientation="right"
        />
```
##  Attributes
 If you want to know more attributes's usage , please to see [attrs](https://github.com/Jay-Goo/RangeSeekBar/blob/master/RangeSeekBar/src/main/res/values/attrs.xml)

## WIKI
You can know more information from wiki.

## Give Me A Star
I hope you like RangeSeekBar. `Star` is the greatest support for me！ Thank you !




