package com.capstone.ditalent.ui.auth.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.component.LoadingDialog
import com.capstone.ditalent.databinding.FragmentRegisterBinding
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.utils.Utilities.clearNoPhone
import com.capstone.ditalent.utils.Utilities.hideSoftKeyboard
import com.capstone.ditalent.utils.Utilities.isNotValidEmail
import com.capstone.ditalent.utils.Utilities.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding as FragmentRegisterBinding

    private lateinit var loadingDialog: LoadingDialog

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())

        binding.apply {
            rgChooseUser.setOnCheckedChangeListener { _, checkedId ->
                val hintText =
                    if (checkedId == R.id.rb_choose_umkm) getString(R.string.hint_name_umkm) else getString(
                        R.string.hint_name_talent
                    )
                edtName.hint = hintText
            }

            tvLogin.setOnClickListener {
                navigateToBack()
            }

            btnRegister.setOnClickListener {
                setupToRegister()
            }
        }


    }

    private fun setupToRegister() {
        val email = binding.edtEmail.text.toString().trim()
        val role = if (binding.rgChooseUser.checkedRadioButtonId == R.id.rb_choose_umkm) {
            Role.UMKM
        } else {
            Role.TALENT
        }
        val name = binding.edtName.text.toString().trim()
        val noPhone = binding.edtNoPhone.text.toString().trim()
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
        if (name.isBlank()) {
            binding.layoutEdtName.apply {
                error = getString(
                    R.string.text_message_error_field_cant_empty,
                    getString(R.string.name)
                )
                isErrorEnabled = true
            }
        }

        if (noPhone.isBlank()) {
            binding.layoutEdtNoPhone.apply {
                error = getString(
                    R.string.text_message_error_field_cant_empty,
                    getString(R.string.no_phone)
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

        val registerCorrect =
            !email.isNotValidEmail() && name.isNotBlank() && noPhone.isNotBlank() && password.length >= 6

        if (registerCorrect) {
            registerViewModel.apply {
                register(name, email, role.toString(), noPhone.clearNoPhone(), password)
                registerUiState.observe(viewLifecycleOwner) { state ->
                    when {
                        state.isSuccess -> {
                            loadingDialog.hideDialog()
                            showSnackBar(requireContext(), "Success", binding.root)
                            navigateToBack()
                        }
                        state.isLoading -> {
                            loadingDialog.showDialog()
                        }
                        state.isError -> {
                            loadingDialog.hideDialog()
                            showSnackBar(requireContext(), "Error", binding.root)
                        }
                    }
                }
            }
        }
        requireActivity().currentFocus?.let {
            hideSoftKeyboard(requireContext(), it)
        }
    }

    private fun navigateToBack() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}