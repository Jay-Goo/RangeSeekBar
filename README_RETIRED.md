
# RangeSeekBar v1.x[RETIRED] 

## [文档还是中文好](https://github.com/Jay-Goo/RangeSeekBar/blob/master/README_RETIRED_ZH.md)


![image](https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/2017-02-08%2019_27_55.gif)

## Attributes

attr | format | description
-------- | ---|---
rsb_min|float|min value, `Float.MIN_VALUE` <= min < max，Default：0
rsb_max|float|max value, min < max <= `Float.MAX_VALUE`, Default: 100
rsb_reserve|float|The minimum distance between two buttons
rsb_cells|int|Cells equal to 0 for the normal mode, greater than 1 to switch to scale mode
rsb_progressHintMode|enum|the progress hint mode. **defaultMode**: show hint when you move the thumb;**alwaysHide**: hide progress hint all the time;**alwaysShow**: show progress hint all the time.**touchAlwaysShow**: show progress hint all the time after touch.
rsb_lineColorSelected|color|The Seekbar color after dragging
rsb_lineColorEdge|color|The default Seekbar color
rsb_thumbPrimaryColor|color|The color of the thumb when the progress is the minimum or maximum，default：none
rsb_thumbSecondaryColor|color|The color of the thumb when the progress is not the minimum or maximum，default：none
rsb_markTextArray|reference|Scale text, do not set the default when hidden
rsb_thumbResId|reference|Button background resources, do not set the default when the circular button
rsb_progressHintResId|reference|Progress prompt background resources, you must use the **9 path file**
rsb_textPadding|dimension|The distance between the scale text and the progress bar
rsb_textSize|dimension|Scale text, and the size of the progress prompt text
rsb_hintBGHeight|dimension|The height of the progress prompt background, not set according to the text size adaptive
rsb_hintBGWith|dimension|The with of the progress prompt background, not set according to the text size adaptive
rsb_hintBGPadding|dimension|The progress indicates the distance between the background and the progress bar
rsb_seekBarHeight|dimension|The height of the progress bar
rsb_thumbSize|dimension|The size of the button
rsb_cellMode|enum|Scale mode **number** according to the scale of the actual proportion of the distribution of the location *（markTextArray must be a number）* **other** bisects the current layout*（markTextArray can be any character）*
rsb_seekBarMode| enum |One-way, two-way mode **single** like normal seekbar **range** Bidirectional selection SeekBar
rsb_thumbRadius|dimension|The radius of the progress bar

## Usage

### Step1：

```xml
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        compile 'com.github.Jay-Goo:RangeSeekBar:v1.2.2'
	}
   
```


### Step2：

```xml
    <com.jaygoo.widget.RangeSeekBar
        android:id="@+id/seekbar1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:rsb_markTextArray="@array/markArray"
        app:rsb_lineColorSelected="@color/colorAccent"
        app:rsb_thumbResId="@drawable/seekbar_thumb"
        app:rsb_lineColorEdge="@color/colorSeekBarDefault"
        app:rsb_cellMode="number"
        app:rsb_seekBarMode="range"
    />
```

## [Update]

version | message
-------- | ---
v1.0.1 | add thumbPrimaryColor、thumbSecondaryColor Attributes
v1.0.2 | fix Scroll conflict and make padding attributes efective
v1.0.3 | fix bugs when range is 0 ~ 1
v1.0.6 | config jitPack to make you can see real source(why is 1.0.6 ? god knew it……)
v1.0.7 | set the seekbar gravity center to make the layout  easier
v1.0.8 | fix issue #2, #3
v1.1.0 | add progressHintMode and some color attrs setter
v1.2.0 | thumbResId support custom drawable；Property name refactoring to prevent naming conflicts；adjust rsb_progressHintMode，add onStopTrackingTouch，onStartTrackingTouch Listeners
v1.2.1 | fix issue #23, #17. add radius of progress
v1.2.2 | fix custom progressHintResId crash


## [Blog](http://blog.csdn.net/google_acmer/article/details/54971421)


## Others 

I hope you like this RangeSeekBar. `Star` is the greatest support for me！ Thank U




