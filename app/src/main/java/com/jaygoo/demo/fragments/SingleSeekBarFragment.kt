package com.jaygoo.demo.fragments

import android.graphics.Typeface
import android.view.View
import com.jaygoo.demo.R
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.fragment_single.*

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
class SingleSeekBarFragment: BaseFragment() {
	override fun getLayoutId(): Int {
		return R.layout.fragment_single
	}

	override fun initView(view: View) {
		sb_single1?.setProgress(10f)
		sb_single2?.setProgress(20f)
		sb_single3?.setProgress(30f)
		sb_single4?.setProgress(40f)
		sb_single4?.setIndicatorTextDecimalFormat("0.00")
		sb_single4?.setIndicatorTextStringFormat("%s%%")
		sb_single5?.setIndicatorTextDecimalFormat("0")

		sb_single6?.setTypeface(Typeface.SANS_SERIF)
		sb_single6?.setOnRangeChangedListener(object :OnRangeChangedListener{
			override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
				when {
					leftValue < 33.33 -> rangeSeekBar.leftSeekBar.setIndicatorText("赶往店中")
					leftValue < 66.66 -> rangeSeekBar.leftSeekBar.setIndicatorText("制作中")
					else -> rangeSeekBar.leftSeekBar.setIndicatorText("配送中")
				}

				if (rangeSeekBar.rangeSeekBarState[0].isMin){
					rangeSeekBar.leftSeekBar.setIndicatorText("商家接单")
				}else if (rangeSeekBar.rangeSeekBarState[0].isMax){
					rangeSeekBar.leftSeekBar.setIndicatorText("已送达")
				}
			}

			override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

			}

			override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

			}

		})
	}


}