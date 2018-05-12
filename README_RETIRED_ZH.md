
# RangeSeekBar v1.x[停止维护]

![image](https://github.com/Jay-Goo/RangeSeekBar/blob/master/Gif/2017-02-08%2019_27_55.gif)

## Attributes

attr | format | description
-------- | ---|---
rsb_min|float|最小值, `Float.MIN_VALUE` <= min < max，默认：0
rsb_max|float|最大值, min < max <= `Float.MAX_VALUE`, 默认: 100
rsb_reserve|float|两个按钮的最小间距
rsb_cells|int|cells 等于0为普通模式，大于1时切换为刻度模式
rsb_progressHintMode|enum|进度提示模式 **defaultMode**：当拖动时显示 **alwaysHide**：一直隐藏 **alwaysShow**：一直显示
rsb_lineColorSelected|color|拖动后的Seekbar颜色
rsb_lineColorEdge|color|默认的Seekbar颜色
rsb_thumbPrimaryColor|color|进度为最小值或最大值时按钮的颜色，默认：不调用
rsb_thumbSecondaryColor|color|进度不为最小值或最大值时按钮的颜色，默认：不调用
rsb_markTextArray|reference|刻度文字，不设置的时候默认隐藏按钮的背景资源，不设置的时候默认为圆形按钮
rsb_thumbResId|reference|按钮的背景资源(支持自定义drawable文件)，不设置的时候默认为圆形按钮
rsb_progressHintResId|reference|进度提示背景资源，必须使用 **9 path**文件
rsb_textPadding|dimension|刻度文字与进度条之间的距离textSize|dimension|刻度文字和进度提示文字的大小
rsb_hintBGHeight|dimension|进度提示背景的高度，不设置时根据文字尺寸自适应
rsb_hintBGWith|dimension|进度提示背景的宽度，不设置时根据文字尺寸自适应
rsb_hintBGPadding|dimension|进度提示背景和进度条之间的距离
rsb_seekBarHeight|dimension|进度条的高度
rsb_thumbSize|dimension|按钮的尺寸
rsb_cellMode|enum|刻度模式 **number** 根据刻度的实际所占比例分配位置*（markTextArray中必须都为数字）* **other** 平分当前布局*（markTextArray可以是任何字符）*
rsb_seekBarMode| enum |单向、双向模式 **single** 单向模式，只有一个按钮 **range** 双向模式，有两个按钮
rsb_thumbRadius|dimension|进度条圆角

## Usage

### 第一步：

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


### 第二步：

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

## [更新]
version | message
-------- | ---
v1.0.1 | 增加了thumbPrimaryColor、thumbSecondaryColor属性
v1.0.2 | 修复滑动冲突，添加padding属性，使其生效
v1.0.3 | 修复当范围为0 ~ 1 的时候产生的一些bug
v1.0.6 | 配置 jitPack，可以让你看到真正的源码 (别问我为啥是1.0.6，我想静静……)
v1.0.7 | 令seekbar布局居中，让你的布局和其他布局配合更简单
v1.0.8 | 修复issue #2，#3
v1.1.0 | 添加进度提示模式属性，添加color 属性setter
v1.2.0 | thumbResId支持自定义drawable；属性名重构，防止命名冲突；调整rsb_progressHintMode，新增onStopTrackingTouch，onStartTrackingTouch Listeners
v1.2.1 | fix issue #23, #17. 支持自定义进度条圆角
v1.2.2 | fix 自定义属性 progressHintResId 闪退

## [博客讲解](http://blog.csdn.net/google_acmer/article/details/54971421)

## 其它
希望你喜欢我的作品。`Star`是对我的最大支持. 谢谢




