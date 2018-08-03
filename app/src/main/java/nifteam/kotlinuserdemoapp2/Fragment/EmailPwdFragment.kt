package nifteam.kotlinuserdemoapp2.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_email_pwd.*
import nifteam.kotlinuserdemoapp2.Mbaas.Callback
import nifteam.kotlinuserdemoapp2.Mbaas.Mbaas

import nifteam.kotlinuserdemoapp2.R
import nifteam.kotlinuserdemoapp2.Utils


class EmailPwdFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_pwd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_sign_up.setOnClickListener(this)
        btn_sign_in.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_up -> {
                signup()
            }
            R.id.btn_sign_in -> {
                signin()
            }
        }
    }

    private fun signup() {
        if (Utils.isBlankOrEmpty(edt_sign_up_mail)) {
            Utils.showDialog(this.context!!, context!!.resources.getText(R.string.message_error_email_do_not_input).toString())
        } else {
            Mbaas.signupByEmail(edt_sign_up_mail.getText().toString(), this.context!!, object : Callback {
                override fun onClickOK() {
                    Utils.clearField(main_container)
                }
            })
        }
    }

    private fun signin() {
        if (Utils.isBlankOrEmpty(edt_sign_in_mail) || Utils.isBlankOrEmpty(edt_sign_in_pwd)) {
            Utils.showDialog(this.context!!, context!!.resources.getText(R.string.message_error_not_input).toString())
        } else {
            Mbaas.signinByEmail(edt_sign_in_mail.getText().toString(), edt_sign_in_pwd.getText().toString(), this.context!!, object : Callback {
                override fun onClickOK() {
                    Utils.clearField(main_container)
                }
            })
        }
    }

}
