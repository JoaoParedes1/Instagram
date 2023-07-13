package com.joaoparedes.instagram.register.view

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.DependencyInjector
import com.joaoparedes.instagram.common.util.TxtWatcher
import com.joaoparedes.instagram.databinding.FragmentRegisterEmailBinding
import com.joaoparedes.instagram.login.view.LoginActivity
import com.joaoparedes.instagram.register.RegisterEmail
import com.joaoparedes.instagram.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null
    private var fragmentListenerAttach: FragmentAttachListener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterEmailBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterEmailPresenter(this, repository)


        binding?.let {

            when(resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    binding?.registerImgLogo?.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                }
            }

            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
                registerBtnNext.setOnClickListener {
                    presenter.create(registerEditEmail.text.toString())
                }


                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)
                })

                registerTxtLogin.setOnClickListener {
                    goToLoginScreen()
                }

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
        fragmentListenerAttach = null
        presenter.onDestroy()
        super.onDestroy()
    }

    fun goToLoginScreen() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private val watcher = TxtWatcher {
        binding?.registerBtnNext?.isEnabled = binding?.registerTxtLogin?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentListenerAttach?.goToNameAndPasswordScreen(email)
    }

}