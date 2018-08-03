package nifteam.kotlinuserdemoapp2.Mbaas

import android.content.Context

import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBUser
import nifteam.kotlinuserdemoapp2.R
import nifteam.kotlinuserdemoapp2.Utils

object Mbaas {
    fun onSignupByID(userId: String, pwd: String, callback: Callback) {
        val user = NCMBUser()
        user.userName = userId
        user.setPassword(pwd)
        user.signUpInBackground { e ->
            if (e != null) {
                /* 処理失敗 */
                callback.onFailure(e)
            } else {
                /* 処理成功 */
                callback.onSuccess()
            }
        }
    }

    fun signinByID(userId: String, pwd: String, callback: Callback) {

        try {
            NCMBUser.loginInBackground(userId, pwd) { ncmbUser, e ->
                if (e != null) {
                    callback.onFailure(e)
                } else {
                    callback.onSuccess(ncmbUser)
                }
            }
        } catch (e: NCMBException) {
            e.printStackTrace()
        }

    }


    fun signinByAnonymousID(callback: Callback) {
        NCMBUser.loginWithAnonymousInBackground { ncmbUser, e ->
            if (e != null) {
                /* 処理失敗 */
                callback.onFailure(e)
            } else {
                /* 処理成功 */
                callback.onSuccess(ncmbUser)

            }
        }
    }

    fun userSuccess(message: String, user: NCMBUser, context: Context, callback: Utils.ClickListener) {

        val sDisplay = message + " objectId: " + user.objectId
        Utils.showDialog(context, sDisplay, object : Utils.ClickListener {
            override fun onOK() {
                logout(context)
                Utils.showDialog(context, context.resources.getText(R.string.logged_out).toString(), object : Utils.ClickListener {
                    override fun onOK() {
                        callback.onOK()
                    }
                })
            }
        })
    }

    fun signupByEmail(mailAddress: String, callback: Callback) {

        NCMBUser.requestAuthenticationMailInBackground(mailAddress) { e ->
            if (e != null) {
                /* 処理失敗 */
                callback.onFailure(e)
            } else {
                /* 処理成功 */
                callback.onSuccess()
            }
        }
    }

    fun signinByEmail(mailAddress: String, pwd: String, callback: Callback) {

        NCMBUser.loginWithMailAddressInBackground(mailAddress, pwd) { ncmbUser, e ->
            if (e != null) {
                /* 処理失敗 */
                callback.onFailure(e)
            } else {
                /* 処理成功 */
                callback.onSuccess(ncmbUser)
            }
        }
    }

    fun userError(message: String, error: NCMBException, context: Context) {
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
