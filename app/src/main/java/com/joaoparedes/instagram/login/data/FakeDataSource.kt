package com.joaoparedes.instagram.login.data

import android.os.Handler
import android.os.Looper
import com.joaoparedes.instagram.common.model.Database

class FakeDataSource : LoginDataSource{

    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

            when {
                userAuth == null -> {
                    callback.onFailure("Usuario não encontrado")
                }
                userAuth.password != password -> {
                    callback.onFailure("Senha incorreta")
                }
                else -> {
                    Database.sessionAuth = userAuth
                    callback.onSuccess()
                }
            }

            callback.onComplete()
        }, 2000)
    }



}