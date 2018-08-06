package nifteam.kotlinuserdemoapp2.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBUser
import kotlinx.android.synthetic.main.fragment_anonymous.*

import nifteam.kotlinuserdemoapp2.Mbaas.Callback
import nifteam.kotlinuserdemoapp2.Mbaas.Mbaas
import nifteam.kotlinuserdemoapp2.Mbaas.Mbaas.userError

import nifteam.kotlinuserdemoapp2.R
import nifteam.kotlinuserdemoapp2.Utils


class AnonymousFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anonymous, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_sign_in.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_in -> {
                Utils.showLoading(context!!)
                Mbaas.signinByAnonymousID(object : Callback {
                    override fun onSuccess() {
                        Utils.hideLoading()
                    }

                    override fun onSuccess(ncmbUser: NCMBUser) {
                        Utils.hideLoading()
                        Mbaas.userSuccess(context!!.resources.getText(R.string.anonymous_login_success).toString(), ncmbUser, context!!, object : Utils.ClickListener {
                            override fun onOK() {

                            }

                        })
                    }

                    override fun onFailure(e: NCMBException) {
                        Utils.hideLoading()
                        userError(context!!.resources.getText(R.string.anonymous_login_failure).toString(), e, context!!)
                    }

                })
            }
        }
    }
}// Required empty public constructor
