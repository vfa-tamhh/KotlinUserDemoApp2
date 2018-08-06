@file:Suppress("DEPRECATION")

package nifteam.kotlinuserdemoapp2

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

object Utils {

    private var progressDialog: ProgressDialog? = null

    interface ClickListener {
        fun onOK()
    }

    fun showLoading(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage(context.resources.getText(R.string.loading_title))
        progressDialog!!.setCancelable(true)
        progressDialog!!.show()
    }

    fun hideLoading() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }

    }

    fun showDialog(context: Context, msg: String, clickListener: ClickListener) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val text = dialog.findViewById<TextView>(R.id.tv_message)
        text.text = msg

        val dialogButton = dialog.findViewById<Button>(R.id.btn_ok)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            clickListener.onOK()
        }

        dialog.show()
    }

    fun showDialog(context: Context, msg: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val text = dialog.findViewById<TextView>(R.id.tv_message)
        text.text = msg

        val dialogButton = dialog.findViewById<Button>(R.id.btn_ok)
        dialogButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    fun isBlankOrEmpty(editText: EditText): Boolean {

        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    fun clearField(linearLayout: LinearLayout) {
        for (i in 0 until linearLayout.childCount) {
            if (linearLayout.getChildAt(i) is EditText) {
                (linearLayout.getChildAt(i) as EditText).setText("")
            }
        }
    }
}
