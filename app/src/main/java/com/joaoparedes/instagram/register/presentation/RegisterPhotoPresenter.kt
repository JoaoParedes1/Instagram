package com.joaoparedes.instagram.register.presentation

import android.net.Uri
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.register.RegisterNameAndPassword
import com.joaoparedes.instagram.register.RegisterPhoto
import com.joaoparedes.instagram.register.data.RegisterCallback
import com.joaoparedes.instagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhoto.View?,
    private var repository: RegisterRepository
) : RegisterPhoto.Presenter {

    override fun updateUser(photoUri: Uri) {
        view?.showProgress(true)

        repository.updateUser(photoUri, object : RegisterCallback {
            override fun onSuccess() {
                view?.onUpdateSuccess()
            }

            override fun onFailure(message: String) {
                view?.onUpdateFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun onDestroy() {
        view = null
    }


}