package com.joaoparedes.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.DependencyInjector
import com.joaoparedes.instagram.common.util.TxtWatcher
import com.joaoparedes.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.joaoparedes.instagram.register.RegisterNameAndPassword
import com.joaoparedes.instagram.register.presentation.RegisterNameAndPasswordPresenter

class RegisterNamePasswordFragment: Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null
    private var fragmentListenerAttach: FragmentAttachListener? = null

    override lateinit var presenter: RegisterNameAndPassword.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterNameAndPasswordPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("email not found")

        binding?.let {
            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }

                registerNameBtnNext.setOnClickListener {
                    presenter.create(
                        email,
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditConfirm.text.toString()
                    )
                }

                registerEditName.addTextChangedListener(watcher)
                registerEditName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                registerEditPassword.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                registerEditConfirm.addTextChangedListener(watcher)
                registerEditConfirm.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
            }
        }



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListener) {
            fragmentListenerAttach = context
        }
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }


    private val watcher = TxtWatcher {
        binding?.registerNameBtnNext?.isEnabled = binding?.registerTxtLogin?.text.toString().isNotEmpty()
                && binding?.registerEditPassword?.text.toString().isNotEmpty()
                && binding?.registerEditConfirm?.text.toString().isNotEmpty()
    }


    override fun showProgress(enabled: Boolean) {
        binding?.registerNameBtnNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(emailError: Int?) {
        binding?.registerEditPasswordInput?.error = emailError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateSuccess(name: String) {
        fragmentListenerAttach?.goToWelcomeScreen(name)
    }


    companion object {
        const val KEY_EMAIL = "key_email"
    }
}