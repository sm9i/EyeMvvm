package com.sm9i.eyemvvm.base.data

import android.content.Context
import com.sm9i.base.application.BaseApplication
import com.sm9i.base.data.ILocalDataSource
import com.sm9i.base.ext.boolean


/**
 * 用户配置信息
 */
object UserSettingLocalDataSource : ILocalDataSource {


    private const val NAME = "sm9i."
    private const val KEY_IS_USER_LOGIN = "is_user_login"
    private const val KEY_IS_FIRST_LOGIN = "is_first_login"
    private const val KEY_IS_SHOW_USER_ANIM = "key_is_show_user_anim"


    private fun getSharedPreferences() =
        BaseApplication.INSTANCE.getSharedPreferences(NAME, Context.MODE_PRIVATE)


    /**
     * 是否登录
     */
    var isUserLogin by getSharedPreferences().boolean(KEY_IS_USER_LOGIN)


    /**
     * 是否第一次登录
     */
    var isFirstLogin by getSharedPreferences().boolean(KEY_IS_FIRST_LOGIN, true)

    /**
     * 是否显示动画
     */
    var isShowUserAnim by getSharedPreferences().boolean(KEY_IS_SHOW_USER_ANIM)


}