package com.pikipin.pkpn_mobile

import androidx.lifecycle.liveData
import com.pikipin.pkpn_mobile.base.BaseViewModel
import com.pikipin.pkpn_mobile.network.login.ILoginRepository
import com.pikipin.pkpn_mobile.network.login.LoginBody

class LoginViewModel(private val loginRepository: ILoginRepository) : BaseViewModel() {

    fun verificationCode(phoneNumber: String) = liveData {
            try {
                val requestCode = loginRepository.registerUser(LoginBody("zakimstatefarm@gmail.com", phoneNumber))
                emit(requestCode)
            }catch (e: Exception){
                e.printStackTrace()
            }

    }
}