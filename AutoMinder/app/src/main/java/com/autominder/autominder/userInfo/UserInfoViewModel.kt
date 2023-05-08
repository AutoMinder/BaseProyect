package com.autominder.autominder.userInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    fun onBuyLinkClicked(context: Context) {
        val url =
            "https://www.amazon.com/?&tag=googleglobalp-20&ref=pd_sl_7nnedyywlk_e&adgrpid=82342659060&hvpone=&hvptwo=&hvadid=585475370855&hvpos=&hvnetw=g&hvrand=8109794863544036515&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9069896&hvtargid=kwd-10573980&hydadcr=2246_13468515"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun onContactDevelopersClicked(context: Context){
        val address = "mailto:00077321@uca.edu.sv" //TODO: Cambiar
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(address)
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
        }
        context.startActivity(intent)
    }
}