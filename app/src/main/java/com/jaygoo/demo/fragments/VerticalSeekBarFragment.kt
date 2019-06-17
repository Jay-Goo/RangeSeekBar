package com.jaygoo.demo.fragments

import android.graphics.Color
import android.view.View
import com.jaygoo.demo.R
import com.jaygoo.widget.*
import kotlinx.android.synthetic.main.fragment_range.*
import kotlinx.android.synthetic.main.fragment_vertical.*

/**
//                       _ooOoo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                       O\ = /O
//                   ____/`---'\____
//                 .   ' \\| |// `.
//                  / \\||| : |||// \
//                / _||||| -:- |||||- \
//                  | | \\\ - /// | |
//                | \_| ''\---/'' | |
//                 \ .-\__ `-` ___/-. /
//              ______`. .' /--.--\ `. . __
//           ."" '< `.___\_<|>_/___.' >'"".
//          | | : `- \`.;`\ _ /`;.`/ - ` : | |
//            \ \ `-. \_ __\ /__ _/ .-` / /
//    ======`-.____`-.___\_____/___.-`____.-'======
//                       `=---='
//
//    .............................................
//             佛祖保佑             永无BUG
 * =====================================================
 * 作    者：JayGoo
 * 创建日期：2019-06-13
 * 描    述:
 * =====================================================
 */
class VerticalSeekBarFragment: BaseFragment() {
	override fun getLayoutId(): Int {
		return R.layout.fragment_vertical
	}

	override fun initView(view: View) {

		sb_vertical_2?.setIndicatorTextDecimalFormat("0.0")
		sb_vertical_2?.setProgress(0f, 100f)
		changeSeekBarThumb(sb_vertical_2.leftSeekBar, sb_vertical_2.leftSeekBar.progress)
		changeSeekBarThumb(sb_vertical_2.rightSeekBar, sb_vertical_2.rightSeekBar.progress)
		sb_vertical_2?.setOnRangeChangedListener(object : OnRangeChangedListener {
			override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
				changeSeekBarThumb(rangeSeekBar.leftSeekBar, leftValue)
				changeSeekBarThumb(rangeSeekBar.rightSeekBar, rightValue)
			}

			override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

			}

			override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

			}

		})

		sb_vertical_3?.setIndicatorTextDecimalFormat("0")
		sb_vertical_4?.setIndicatorTextDecimalFormat("0")
	}

	private fun changeSeekBarThumb(seekbar: SeekBar, value: Float){
		if (value < 33){
			seekbar.indicatorBackgroundColor = Utils.getColor(activity, R.color.colorAccent)
			seekbar.setThumbDrawableId(R.drawable.thumb_green, seekbar.thumbWidth, seekbar.thumbHeight)
		}else if (value < 66){
			seekbar.indicatorBackgroundColor = Utils.getColor(activity, R.color.colorProgress)
			seekbar.setThumbDrawableId(R.drawable.thumb_yellow, seekbar.thumbWidth, seekbar.thumbHeight)
		}else{
			seekbar.indicatorBackgroundColor = Utils.getColor(activity, R.color.colorRed)
			seekbar.setThumbDrawableId(R.drawable.thumb_red, seekbar.thumbWidth, seekbar.thumbHeight)
		}
	}
}