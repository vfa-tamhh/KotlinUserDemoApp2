package nifteam.kotlinuserdemoapp2.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBUser
import kotlinx.android.synthetic.main.fragment_idpwd.*
import nifteam.kotlinuserdemoapp2.Mbaas.Callback
import nifteam.kotlinuserdemoapp2.Mbaas.Mbaas

import nifteam.kotlinuserdemoapp2.R
import nifteam.kotlinuserdemoapp2.Utils

class IDPwdFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idpwd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_sign_up.setOnClickListener(this)
        btn_sign_in.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_up -> {
                sigup()
            }
            R.id.btn_sign_in -> {
                signin()
            }
        }
    }

    private fun sigup() {
        if (Utils.isBlankOrEmpty(edt_sign_up_id) || Utils.isBlankOrEmpty(edt_sign_up_pwd) || Utils.isBlankOrEmpty(edt_sign_up_pwd_confirm)) {
            Utils.showDialog(this.context!!, context!!.resources.getText(R.string.message_error_not_input).toString())
        } else if (edt_sign_up_pwd.text.toString() != edt_sign_up_pwd_confirm.text.toString()) {
            Utils.showDialog(this.context!!, context!!.resources.getText(R.string.message_error_pwd_do_not_match).toString())
        } else {
            Utils.showLoading(this.context!!)
            Mbaas.onSignupByID(edt_sign_up_id.text.toString(), edt_sign_up_pwd.text.toString(), object : Callback {
                override fun onSuccess() {

                    Mbaas.signinByID(edt_sign_up_id.text.toString(), edt_sign_up_pwd.text.toString(), object : Callback {
                        override fun onSuccess() {
                            Utils.hideLoading()
                        }

                        override fun onSuccess(ncmbUser: NCMBUser) {
                            Utils.hideLoading()
                            Mbaas.userSuccess(context!!.resources.getText(R.string.login_success).toString(),
                                    ncmbUser, context!!, object : Utils.ClickListener {
                                override fun onOK() {
                                    Utils.clearField(main_container)
                                }

                            })
                        }

                        override fun onFailure(e: NCMBException) {
                            Utils.hideLoading()
                            Mbaas.userError(context!!.resources.getText(R.string.id_pw_login_failure).toString(), e, context!!)
                        }

                    })
                }

                override fun onSuccess(ncmbUser: NCMBUser) {
                    Utils.hideLoading()
                }

                override fun onFailure(e: NCMBException) {
                    Utils.hideLoading()
                    Mbaas.userError(context!!.resources.getText(R.string.id_pw_registration_failure).toString(), e, context!!)
                }

            })
        }
    }

    private fun signin() {
        if (Utils.isBlankOrEmpty(edt_sign_in_id) || Utils.isBlankOrEmpty(edt_sign_in_pwd)) {
            Utils.showDialog(this.context!!, context!!.resources.getText(R.string.message_error_not_input).toString())
        } else {
            Utils.showLoading(this.context!!)
            Mbaas.signinByID(edt_sign_in_id.text.toString(), edt_sign_in_pwd.text.toString(), object : Callback {
                override fun onSuccess() {
                    Utils.hideLoading()
                }

                override fun onSuccess(ncmbUser: NCMBUser) {
                    Utils.hideLoading()
                    Mbaas.userSuccess(context!!.resources.getText(R.string.login_success).toString(),
                            ncmbUser, context!!, object : Utils.ClickListener {
                        override fun onOK() {
                            Utils.clearField(main_container)
                        }

                    })
                }

                override fun onFailure(e: NCMBException) {
                    Utils.hideLoading()
                    Mbaas.userError(context!!.resources.getText(R.string.id_pw_login_failure).toString(), e, context!!)
                }

            })
        }
    }
}
