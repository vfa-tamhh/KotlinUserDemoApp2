package nifteam.kotlinuserdemoapp2.Mbaas

import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBUser

interface Callback {
    fun onSuccess()
    fun onSuccess(ncmbUser: NCMBUser)
    fun onFailure(e: NCMBException)
}
