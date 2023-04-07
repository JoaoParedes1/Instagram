package com.joaoparedes.instagram.register.presentation

import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.register.RegisterNameAndPassword
import com.joaoparedes.instagram.register.data.RegisterCallback
import com.joaoparedes.instagram.register.data.RegisterRepository

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private var repository: RegisterRepository
) : RegisterNameAndPassword.Presenter {

    override fun create(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length > 3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirm

        if(!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isConfirmValid) {
            view?.displayPasswordFailure(R.string.password_not_equal)
        } else {
            view?.displayPasswordFailure(null)
            if (!isPasswordValid) {
                view?.displayPasswordFailure(R.string.invalid_password)
            } else {
                view?.displayPasswordFailure(null)
            }
        }


        if(isNameValid && isPasswordValid && isConfirmValid) {
            view?.showProgress(true)

            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }


}