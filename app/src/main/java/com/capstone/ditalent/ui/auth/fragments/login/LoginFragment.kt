package com.capstone.ditalent.ui.auth.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.component.LoadingDialog
import com.capstone.ditalent.databinding.FragmentLoginBinding
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.utils.UiText
import com.capstone.ditalent.utils.Utilities.hideSoftKeyboard
import com.capstone.ditalent.utils.Utilities.isNotValidEmail
import com.capstone.ditalent.utils.Utilities.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var authAct: AuthActivity

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authAct = activity as AuthActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())

        binding.apply {
            tvRegister.setOnClickListener { navigateToRegister() }
            btnLogin.setOnClickListener {
                setupToLogin()
            }

            btnLoginWithGoogle.setOnClickListener {
                authAct.signInWithGoogle()
            }
        }
    }

    private fun setupToLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isBlank()) {
            binding.layoutEdtEmail.apply {
                error = getString(
                    R.string.text_message_error_field_cant_empty,
                    getString(R.string.email)
                )
                isErrorEnabled = true
            }
        }
        if (password.isBlank()) {
            binding.layoutEdtPassword.apply {
                error = getString(
                    R.string.text_message_error_field_cant_empty,
                    getString(R.string.password)
                )
                isErrorEnabled = true
            }
        }
        val loginCorrect = !email.isNotValidEmail() && password.length >= 6

        if (loginCorrect) {
            loginViewModel.apply {
                login(email, password)
                loginUiState.observe(viewLifecycleOwner) { state ->
                    when {
                        state.isSuccess -> {
                            loadingDialog.hideDialog()
                        }
                        state.isLoading -> {
                            loadingDialog.showDialog()
                        }
                        state.isError -> {
                            loadingDialog.hideDialog()
                            showSnackBar(
                                requireActivity(),
                                getString((state.message as UiText.StringResource).id),
                                binding.root
                            )
                        }
                    }
                }
            }
        }

        requireActivity().currentFocus?.let {
            hideSoftKeyboard(requireContext(), it)
        }
    }


    private fun navigateToRegister() {
        val directions = LoginFragmentDirections.actionLoginNavToRegisterNav()
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}