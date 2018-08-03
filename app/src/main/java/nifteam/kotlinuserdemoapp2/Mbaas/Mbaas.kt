package nifteam.kotlinuserdemoapp2.Mbaas

import android.content.Context

import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBUser
import nifteam.kotlinuserdemoapp2.R
import nifteam.kotlinuserdemoapp2.Utils

object Mbaas {
    fun onSignupByID(userId: String, pwd: String, context: Context, callback: Callback) {
        Utils.showLoading(context)
        val user = NCMBUser()
        user.userName = userId
        user.setPassword(pwd)
        user.signUpInBackground { e ->
            Utils.hideLoading()
            if (e != null) {
                /* 処理失敗 */
                userError(context.resources.getText(R.string.id_pw_registration_failure).toString(), e, context)
            } else {
                /* 処理成功 */
                signinByID(userId, pwd, context, callback)
            }
        }
    }

    fun signinByID(userId: String, pwd: String, context: Context, callback: Callback) {
        Utils.showLoading(context)
        try {
            NCMBUser.loginInBackground(userId, pwd) { ncmbUser, e ->
                Utils.hideLoading()
                if (e != null) {
                    userError(context.resources.getText(R.string.id_pw_login_failure).toString(), e, context)
                } else {
                    userSuccess(context.resources.getText(R.string.login_success).toString(),
                            ncmbUser, context, callback)
                }
            }
        } catch (e: NCMBException) {
            Utils.hideLoading()
            e.printStackTrace()
        }

    }


    fun signinByAnonymousID(context: Context, callback: Callback) {
        Utils.showLoading(context)
        NCMBUser.loginWithAnonymousInBackground { ncmbUser, e ->
            Utils.hideLoading()
            if (e != null) {
                /* 処理失敗 */
                userError(context.resources.getText(R.string.anonymous_login_failure).toString(), e, context)
            } else {
                /* 処理成功 */
                userSuccess(context.resources.getText(R.string.anonymous_login_success).toString(), ncmbUser, context, callback)
            }
        }
    }

    private fun userSuccess(message: String, user: NCMBUser, context: Context, callback: Callback) {
        val sDisplay = message + " objectId: " + user.objectId
        Utils.showDialog(context, sDisplay, object : Utils.ClickListener {
            override fun onOK() {
                logout(context)
                Utils.showDialog(context, context.resources.getText(R.string.logged_out).toString(), object : Utils.ClickListener {
                    override fun onOK() {
                        callback.onClickOK()
                    }
                })
            }
        })
    }

    fun signupByEmail(mailAddress: String, context: Context, callback: Callback) {
        Utils.showLoading(context)
        NCMBUser.requestAuthenticationMailInBackground(mailAddress) { e ->
            Utils.hideLoading()
            if (e != null) {
                userError(context.resources.getText(R.string.email_pw_registration_failure).toString(), e, context)
            } else {
                Utils.showDialog(context, context.resources.getText(R.string.email_pw_registration_complete).toString(), object : Utils.ClickListener {
                    override fun onOK() {
                        Utils.showDialog(context, context.resources.getText(R.string.message_response_registration_complete).toString(), object : Utils.ClickListener {
                            override fun onOK() {
                                callback.onClickOK()
                            }
                        })
                    }
                })
            }
        }
    }

    fun signinByEmail(mailAddress: String, pwd: String, context: Context, callback: Callback) {
        Utils.showLoading(context)
        NCMBUser.loginWithMailAddressInBackground(mailAddress, pwd) { ncmbUser, e ->
            Utils.hideLoading()
            if (e != null) {
                /* 処理失敗 */
                userError(context.resources.getText(R.string.email_pw_login_failure).toString(), e, context)
            } else {
                /* 処理成功 */
                userSuccess(context.resources.getText(R.string.email_pw_login_success).toString(), ncmbUser, context, callback)
            }
        }
    }

    private fun userError(message: String, error: NCMBException, context: Context) {
        val sDisplay = message + " " + error.message
        Utils.showDialog(context, sDisplay)
    }

    private fun logout(context: Context) {
        try {
            Utils.showLoading(context)
            NCMBUser.logout()
            Utils.hideLoading()
        } catch (e: NCMBException) {
            Utils.hideLoading()
            e.printStackTrace()
        }

    }
}
