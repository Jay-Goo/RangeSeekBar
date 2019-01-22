<div style="text-align: center;">
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/logo.png" style="margin: 0 auto;" />
</div>

## [文档还是中文好](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_ZH.md)

<div>
<img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/demo.gif" height="500px" ><img src="https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/vertical_demo.gif" height="500px"><img src="https://github.com/fa-hessari/RangeSeekBar/blob/master/Gif/Screenshot_2019-01-17-17-44-07.jpg" height="500px">
</div>


# Attention

**New feature 1/21/2019 ！！！**

Add oneTouchMode for single seekbarMode.No need to drag thumb,just move it by one touch.

**New feature 1/17/2019 ！！！**

Add tick mark text on the top and below of the progress bar.

## Usage

### Dependencies

[![](https://jitpack.io/v/fa-hessari/RangeSeekBar.svg)](https://jitpack.io/#fa-hessari/RangeSeekBar)

```xml
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.fa-hessari:RangeSeekBar:TAG'
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

## New Features
**Use these attrs for the new features:**

in xml use:
```
app:rsb_tick_mark_is_from_first_item="false"
app:rsb_tick_mark_is_top_and_below="true"
app:rsb_tick_mark_is_one_touch_mode="true"
```

or in code use:
```
rangeSeekBar.setTickMarkTopAndBelow(true, false);
rangeSeekBar.setOneTouchMode(true);
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

### Example for new feature
```
<com.jaygoo.widget.RangeSeekBar
        android:id="@+id/seekbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginEnd="10dp"
        android:layout_marginStart="15dp"
        android:layout_marginTop="20dp"
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        app:rsb_indicator_height="20dp"
        app:rsb_indicator_padding_left="20dp"
        app:rsb_indicator_padding_right="20dp"
        app:rsb_indicator_text_size="12sp"
        app:rsb_mode="single"
        app:rsb_progress_color="#28369f"
        app:rsb_progress_default_color=#28369f"
        app:rsb_progress_height="4dp"
        app:rsb_thumb_drawable="@drawable/shape_circle"
        app:rsb_thumb_size="30dp"
        app:rsb_tick_mark_gravity="center"
        app:rsb_tick_mark_is_from_first_item="false"
        app:rsb_tick_mark_is_one_touch_mode="true"
        app:rsb_tick_mark_is_top_and_below="true"
        app:rsb_tick_mark_mode="other"
        app:rsb_tick_mark_text_array="@array/my_array"
        app:rsb_tick_mark_text_margin="20dp"
        app:rsb_tick_mark_text_size="14sp"
        />
```

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

