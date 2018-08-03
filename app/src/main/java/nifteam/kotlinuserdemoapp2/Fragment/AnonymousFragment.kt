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
                Mbaas.signinByAnonymousID(this.context!!, object : Callback {
                    override fun onClickOK() {

                    }
                })
            }
        }
    }
}// Required empty public constructor
