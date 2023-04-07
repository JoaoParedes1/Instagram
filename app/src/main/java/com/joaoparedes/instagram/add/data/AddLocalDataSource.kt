package com.joaoparedes.instagram.add.data

import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.UserAuth

class AddLocalDataSource : AddDataSource {

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("Usuario não logado!!")
    }

}