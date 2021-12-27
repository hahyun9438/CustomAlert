package com.hhyun.customalertlibrary

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog

class HhyunAlert(context: Context, alertType: AlertType)
    : AppCompatDialog(context, resolveDialogTheme(alertType)), DialogInterface {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    private var dialogController = HhyunAlertController(context, this, window, alertType)


    fun getAlert(): Dialog {
        return dialogController.getAlert()
    }

    fun getButton(buttonType: Int): TextView? {
        return dialogController.getButton(buttonType)
    }


    class Builder(private val context: Context?, private val alertType: AlertType) {

        var alertParam = HhyunAlertController.AlertParam()


        /** 다이얼로그 레이아웃 셋팅 */
        fun setLayout(@LayoutRes layoutResId: Int?): Builder {
            alertParam.layoutResId = layoutResId
            return this
        }

        fun setCustomView(view: View?): Builder {
            alertParam.customView = view
            return this
        }


        /** 다이얼로그 타이틀 셋팅 */
        fun setTitle(@StringRes description: Int, textSize: Int? = null): Builder {
            alertParam.title = SpannableString(context?.getString(description) ?: "")
            alertParam.titleSize = textSize
            return this
        }
        fun setTitle(description: String, textSize: Int? = null): Builder {
            alertParam.title = SpannableString(description)
            alertParam.titleSize = textSize
            return this
        }
        fun setTitle(description: Spannable, textSize: Int? = null): Builder {
            alertParam.title = description
            alertParam.titleSize = textSize
            return this
        }

        /** 다이얼로그 디스크립션 셋팅 */
        fun setDescription(@StringRes description: Int, textSize: Int? = null): Builder {
            alertParam.description = SpannableString(context?.getString(description) ?: "")
            alertParam.descriptionSize = textSize
            return this
        }
        fun setDescription(description: String, textSize: Int? = null): Builder {
            alertParam.description = SpannableString(description)
            alertParam.descriptionSize = textSize
            return this
        }
        fun setDescription(description: Spannable, textSize: Int? = null): Builder {
            alertParam.description = description
            alertParam.descriptionSize = textSize
            return this
        }

        fun setDescriptionPadding(alertTopPadding: Int? = null, alertBottomPadding: Int? = null, descriptionGapPadding: Int? = null): Builder {
            alertParam.topPadding = alertTopPadding
            alertParam.bottomPadding = alertBottomPadding
            alertParam.messageGapPadding = descriptionGapPadding
            return this
        }


        fun setCustomViewSetting(listener: HhyunAlretCustomSettingListener?): Builder {
            alertParam.customSettingListener = listener
            return this
        }


        /** 다이얼로그 positive 버튼 셋팅 */
        fun setPositiveButton(@StringRes label: Int): Builder {
            alertParam.positiveButtonLabel = context?.getString(label) ?: ""
            alertParam.positiveButtonClickListener = null
            return this
        }
        fun setPositiveButton(@StringRes label: Int,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.positiveButtonLabel = context?.getString(label) ?: ""
            alertParam.positiveButtonClickListener = listener
            return this
        }
        fun setPositiveButton(label : String): Builder {
            alertParam.positiveButtonLabel = label
            alertParam.positiveButtonClickListener = null
            return this
        }
        fun setPositiveButton(label : String,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.positiveButtonLabel = label
            alertParam.positiveButtonClickListener = listener
            return this
        }
        fun setPositiveButton(@StringRes label: Int,
                              @ColorRes positiveButtonColor: Int = R.color.hhyunlib_black,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.positiveButtonLabel = context?.getString(label) ?: ""
            alertParam.positiveButtonColor = positiveButtonColor
            alertParam.positiveButtonClickListener = listener
            return this
        }
        fun setPositiveButton(label : String,
                              @ColorRes positiveButtonColor: Int = R.color.hhyunlib_black,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.positiveButtonLabel = label
            alertParam.positiveButtonColor = positiveButtonColor
            alertParam.positiveButtonClickListener = listener
            return this
        }
        fun setPositiveButton(listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.positiveButtonClickListener = listener
            return this
        }


        /** 다이얼로그 negative 버튼 셋팅 */
        fun setNegativeButton(@StringRes label: Int): Builder {
            alertParam.negativeButtonLabel = context?.getString(label) ?: ""
            alertParam.negativeButtonClickListener = null
            return this
        }
        fun setNegativeButton(@StringRes label: Int,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.negativeButtonLabel = context?.getString(label) ?: ""
            alertParam.negativeButtonClickListener = listener
            return this
        }
        fun setNegativeButton(label : String): Builder {
            alertParam.negativeButtonLabel = label
            alertParam.negativeButtonClickListener = null
            return this
        }
        fun setNegativeButton(label : String,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.negativeButtonLabel = label
            alertParam.negativeButtonClickListener = listener
            return this
        }
        fun setNegativeButton(@StringRes label: Int,
                              @ColorRes negativeButtonColor: Int = R.color.hhyunlib_black,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.negativeButtonLabel = context?.getString(label) ?: ""
            alertParam.negativeButtonColor = negativeButtonColor
            alertParam.negativeButtonClickListener = listener
            return this
        }
        fun setNegativeButton(label : String,
                              @ColorRes negativeButtonColor: Int = R.color.hhyunlib_black,
                              listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.negativeButtonLabel = label
            alertParam.negativeButtonColor = negativeButtonColor
            alertParam.negativeButtonClickListener = listener
            return this
        }
        fun setNegativeButton(listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.negativeButtonClickListener = listener
            return this
        }


        /** 다이얼로그 neutral 버튼 셋팅 */
        fun setNeutralButton(@StringRes label: Int): Builder {
            alertParam.neutralButtonLabel = context?.getString(label) ?: ""
            alertParam.neutralButtonClickListener = null
            return this
        }
        fun setNeutralButton(@StringRes label: Int,
                             listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.neutralButtonLabel = context?.getString(label) ?: ""
            alertParam.neutralButtonClickListener = listener
            return this
        }
        fun setNeutralButton(label : String): Builder {
            alertParam.neutralButtonLabel = label
            alertParam.neutralButtonClickListener = null
            return this
        }
        fun setNeutralButton(label : String,
                             listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.neutralButtonLabel = label
            alertParam.neutralButtonClickListener = listener
            return this
        }
        fun setNeutralButton(@StringRes label: Int,
                             @ColorRes neutralButtonColor: Int = R.color.hhyunlib_black,
                             listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.neutralButtonLabel = context?.getString(label) ?: ""
            alertParam.neutralButtonColor = neutralButtonColor
            alertParam.neutralButtonClickListener = listener
            return this
        }
        fun setNeutralButton(label : String,
                             @ColorRes neutralButtonColor: Int = R.color.hhyunlib_black,
                             listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.neutralButtonLabel = label
            alertParam.neutralButtonColor = neutralButtonColor
            alertParam.neutralButtonClickListener = listener
            return this
        }
        fun setNeutralButton(listener: HhyunAlretButtonClickListener?): Builder {
            alertParam.neutralButtonClickListener = listener
            return this
        }


        /** bottom up 다이얼로그 뷰 셋팅 */
        fun setBottomViewLayout(@LayoutRes layoutResId: Int?, listener: HhyunBottomAlretCustomSettingListener?): Builder {
            alertParam.bottomViewLayoutResId = layoutResId
            alertParam.bottomCustomSettingListener = listener
            return this
        }

        fun setBottomViewBackground(@DrawableRes backgroundResId: Int): Builder {
            alertParam.bottomViewBackgroundResId = backgroundResId
            return this
        }


        /** action sheet 다이얼로그 뷰 셋팅 */
        fun setActionSheet(anchorView: View?,
                           actionList: List<String>,
                           actionMenuGravity: Int,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            return setActionSheet(
                anchorView = anchorView,
                width = null,
                actionList = actionList,
                defaultSelectedActionSheetItem = null,
                isShowActionSheetArrowIcon = null,
                actionSheetItemTextSize = 15,
                actionMenuGravity = actionMenuGravity,
                topMargin = topMargin,
                rightMargin = rightMargin,
                leftPadding = null, topPadding = null, rightPadding = null, bottomPadding = null,
                actionSheetItemVerticalPadding = null,
                excludeBottomHeight = excludeBottomHeight,
                listener = listener
            )
        }
        fun setActionSheet(anchorView: View?,
                           actionList: List<String>,
                           actionMenuGravity: Int,
                           actionSheetItemTextSize: Int = 15,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           leftPadding: Int?, topPadding: Int?, rightPadding: Int?, bottomPadding: Int?,
                           actionSheetItemVerticalPadding: Int?,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            return setActionSheet(
                anchorView = anchorView,
                width = null,
                actionList = actionList,
                defaultSelectedActionSheetItem = null,
                isShowActionSheetArrowIcon = null,
                actionSheetItemTextSize = actionSheetItemTextSize,
                actionMenuGravity = actionMenuGravity,
                topMargin = topMargin,
                rightMargin = rightMargin,
                leftPadding = leftPadding, topPadding = topPadding, rightPadding = rightPadding, bottomPadding = bottomPadding,
                actionSheetItemVerticalPadding = actionSheetItemVerticalPadding,
                excludeBottomHeight = excludeBottomHeight,
                listener = listener
            )
        }
        fun setActionSheet(anchorView: View?, width: Int,
                           actionList: List<String>,
                           actionMenuGravity: Int,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            return setActionSheet(
                anchorView = anchorView,
                width = width,
                actionList = actionList,
                defaultSelectedActionSheetItem = null,
                isShowActionSheetArrowIcon = null,
                actionSheetItemTextSize = 15,
                actionMenuGravity = actionMenuGravity,
                topMargin = topMargin,
                rightMargin = rightMargin,
                leftPadding = null, topPadding = null, rightPadding = null, bottomPadding = null,
                actionSheetItemVerticalPadding = null,
                excludeBottomHeight = excludeBottomHeight,
                listener = listener
            )
        }
        fun setActionSheet(anchorView: View?,
                           actionList: List<String>,
                           defaultSelectedActionSheetItem: String? = "",
                           isShowActionSheetArrowIcon: Boolean? = null,
                           actionMenuGravity: Int,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            return setActionSheet(
                anchorView = anchorView,
                width = null,
                actionList = actionList,
                defaultSelectedActionSheetItem = defaultSelectedActionSheetItem,
                isShowActionSheetArrowIcon = isShowActionSheetArrowIcon,
                actionSheetItemTextSize = 15,
                actionMenuGravity = actionMenuGravity,
                topMargin = topMargin,
                rightMargin = rightMargin,
                leftPadding = null, topPadding = null, rightPadding = null, bottomPadding = null,
                actionSheetItemVerticalPadding = null,
                excludeBottomHeight = excludeBottomHeight,
                listener = listener
            )
        }
        fun setActionSheet(anchorView: View?,
                           actionList: List<String>,
                           defaultSelectedActionSheetItem: String? = "",
                           isShowActionSheetArrowIcon: Boolean? = null,
                           actionSheetItemTextSize: Int = 15,
                           actionMenuGravity: Int,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            return setActionSheet(
                anchorView = anchorView,
                width = null,
                actionList = actionList,
                defaultSelectedActionSheetItem = defaultSelectedActionSheetItem,
                isShowActionSheetArrowIcon = isShowActionSheetArrowIcon,
                actionSheetItemTextSize = actionSheetItemTextSize,
                actionMenuGravity = actionMenuGravity,
                topMargin = topMargin,
                rightMargin = rightMargin,
                leftPadding = null, topPadding = null, rightPadding = null, bottomPadding = null,
                actionSheetItemVerticalPadding = null,
                excludeBottomHeight = excludeBottomHeight,
                listener = listener
            )
        }
        fun setActionSheet(anchorView: View?,
                           width: Int? = 0,
                           actionList: List<String>,
                           defaultSelectedActionSheetItem: String? = "",
                           isShowActionSheetArrowIcon: Boolean? = null,
                           actionSheetItemTextSize: Int = 15,
                           actionMenuGravity: Int,
                           topMargin: Int? = 0,
                           rightMargin: Int? = 0,
                           leftPadding: Int?, topPadding: Int?, rightPadding: Int?, bottomPadding: Int?,
                           actionSheetItemVerticalPadding: Int?,
                           excludeBottomHeight: Int? = 0,
                           listener: HhyunActionSheetClickListener): Builder {
            alertParam.anchorView = anchorView
            alertParam.width = width ?: 0
            alertParam.actionList = actionList
            alertParam.defaultSelectedActionSheetItem = defaultSelectedActionSheetItem
            alertParam.isShowActionSheetArrowIcon = isShowActionSheetArrowIcon
            alertParam.textSize = actionSheetItemTextSize
            alertParam.actionMenuGravity = actionMenuGravity
            alertParam.topMargin = topMargin
            alertParam.rightMargin = rightMargin
            alertParam.leftPadding = leftPadding
            alertParam.topPadding = topPadding
            alertParam.rightPadding = rightPadding
            alertParam.bottomPadding = bottomPadding
            alertParam.actionSheetItemVerticalPadding = actionSheetItemVerticalPadding
            alertParam.excludeBottomHeight = excludeBottomHeight
            alertParam.actionSheetListener = listener
            return this
        }


        /** spinner 다이얼로그 뷰 셋팅 */
        fun setSpinner(anchorView: View?, headerView: View,
                       width: Int,
                       defaultSelectedSpinnerItem: String? = "",
                       defaultScrollToSpinnerItem: String? = null,
                       spinnerList: List<String>,
                       spinnerItemTextSize: Int = 15,
                       spinnerMaxHeight: Int? = null,
                       showAlwaysScrollBar: Boolean = false,
                       listener: HhyunSpinnerClickListener?): Builder {
            alertParam.anchorView = anchorView
            alertParam.spinnerHeaderView = headerView
            alertParam.width = width
            alertParam.textSize = spinnerItemTextSize
            alertParam.defaultSelectedSpinnerItem = defaultSelectedSpinnerItem
            alertParam.defaultScrollToSpinnerItem = defaultScrollToSpinnerItem
            alertParam.spinnerList = spinnerList
            alertParam.maxHeight = spinnerMaxHeight
            alertParam.showAlwaysScrollBar = showAlwaysScrollBar
            alertParam.spinnerListener = listener
            return this
        }
        fun setSpinner(anchorView: View?, headerView: View,
                       width: Int,
                       defaultSelectedSpinnerItem: String? = "",
                       defaultScrollToSpinnerItem: String? = null,
                       spinnerList: List<String>,
                       spinnerItemTextSize: Int = 15,
                       spinnerMaxHeight: Int? = null,
                       spinnerMaxLength: Int = 100,
                       showAlwaysScrollBar: Boolean = false,
                       listener: HhyunSpinnerClickListener?): Builder {
            alertParam.maxLength = spinnerMaxLength
            return setSpinner(anchorView, headerView, width,
                defaultSelectedSpinnerItem, defaultScrollToSpinnerItem, spinnerList,
                spinnerItemTextSize, spinnerMaxHeight, showAlwaysScrollBar, listener)
        }


        /** info 다이얼로그 뷰 셋팅 */
        fun setInfo(anchorView: View?, layoutResId: Int, topMargin: Int? = 0): Builder {
            alertParam.anchorView = anchorView
            alertParam.infoViewLayoutResId = layoutResId
            alertParam.topMargin = topMargin
            return this
        }



        /** 디바이스 뒤로가기 버튼을 눌렀을때 다이얼로그를 닫을지 여부 셋팅 */
        fun setBackPressCancelAble(backPressCancelable: Boolean): Builder {
            alertParam.backPressCancelable = backPressCancelable
            return this
        }
        fun setBackPressCancelAble(backPressCancelable: Boolean, listener: HhyunAlretCancelListener?): Builder {
            alertParam.backPressCancelable = backPressCancelable
            alertParam.cancelListener = listener
            return this
        }

        /** 다이얼로그의 여부 영역을 터치했을때 다이얼로그를 닫을지 여부 셋팅 */
        fun setOutsideTouchCancelable(outsideTouchCancelable: Boolean): Builder {
            alertParam.outsideTouchCancelable = outsideTouchCancelable
            return this
        }
        fun setOutsideTouchCancelable(outsideTouchCancelable: Boolean, listener: HhyunAlretCancelListener?): Builder {
            alertParam.outsideTouchCancelable = outsideTouchCancelable
            alertParam.cancelListener = listener
            return this
        }


        /** 다이얼로그 생성 */
        private fun create(): AppCompatDialog? {
            if(context == null) return null
            val dialog = HhyunAlert(context, alertType)
            alertParam.apply(dialog.dialogController)
            dialog.setCancelable(alertParam.backPressCancelable)
            dialog.setCanceledOnTouchOutside(alertParam.outsideTouchCancelable)
            dialog.setOnCancelListener {
                alertParam.cancelListener?.onCancel(dialog)
            }
            return dialog
        }

        /** 다이얼로그 띄우기 */
        fun show(): AppCompatDialog?  {
            val dialog = create()
            if(dialog?.isShowing == false) dialog.show()
            return dialog
        }

    }

}

fun resolveDialogTheme(alertType: AlertType): Int {
    return when(alertType) {
        AlertType.CENTER_TRANSPARENT -> R.style.HhyunLibTransparentWarpAlert
        AlertType.CENTER -> R.style.HhyunLibFillAlert
        AlertType.BOTTOM_TRANSPARENT -> R.style.HhyunLibUpTransparentAlert
        AlertType.TRANSPARENT, AlertType.ACTION_SHEET,
        AlertType.SPINNER_RADIUS_6, AlertType.SPINNER_RADIUS_10 -> R.style.HhyunLibTransparentMatchAlert
        else -> R.style.HhyunLibUpAlert
    }

}