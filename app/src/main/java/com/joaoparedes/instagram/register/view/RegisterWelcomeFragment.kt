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
import com.joaoparedes.instagram.databinding.FragmentRegisterWelcomeBinding
import com.joaoparedes.instagram.register.RegisterNameAndPassword
import com.joaoparedes.instagram.register.presentation.RegisterNameAndPasswordPresenter

class RegisterWelcomeFragment : Fragment(R.layout.fragment_register_welcome) {

    private var binding: FragmentRegisterWelcomeBinding? = null
    private var fragmentListenerAttach: FragmentAttachListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name = arguments?.getString(RegisterWelcomeFragment.KEY_NAME) ?: throw IllegalArgumentException("email not found")

        binding?.let {
            with(it) {
                registerTxtWelcome.text = getString(R.string.welcome_to_instagram, name)

                registerBtnNext.isEnabled = true
                registerBtnNext.setOnClickListener {
                    fragmentListenerAttach?.goToPhotoScreen()
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
        super.onDestroy()
    }

    companion object {
        const val KEY_NAME = "key_name"
    }
}