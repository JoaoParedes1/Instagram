package com.joaoparedes.instagram.add.data

import com.google.firebase.auth.FirebaseAuth
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.UserAuth

class AddLocalDataSource : AddDataSource {

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario não logado!!")
    }

}