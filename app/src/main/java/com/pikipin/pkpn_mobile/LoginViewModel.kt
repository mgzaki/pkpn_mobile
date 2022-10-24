package com.pikipin.pkpn_mobile

import androidx.lifecycle.liveData
import com.pikipin.pkpn_mobile.base.BaseViewModel
import com.pikipin.pkpn_mobile.network.registration.ILoginRepository
import com.pikipin.pkpn_mobile.network.registration.RegisterBody
import com.pikipin.pkpn_mobile.network.registration.verify.VerifyBody

class LoginViewModel(private val loginRepository: ILoginRepository) : BaseViewModel() {

    fun verifyCode(code: String) = liveData {
        val response = loginRepository.verifyCode(code, VerifyBody("zakimstatefarm@gmail.com"))
        emit(response)
    }

    fun sendVerificationCode(phoneNumber: String) = liveData {
        try {
            val response = loginRepository.registerUser(
                RegisterBody(
                    "zakimstatefarm@gmail.com",
                    "Chang3m3!",
                    "Samory",
                    "+15204285101"
                )
            )
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun login(username: String, password: String) = liveData {
        try {
            val response = loginRepository.login(username, password)
            emit(response)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}