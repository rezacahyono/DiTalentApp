package com.capstone.ditalent.ui.umkm.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.ditalent.databinding.FragmentProfileUmkmBinding
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.ui.umkm.activities.UmkmActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileUmkmFragment : Fragment() {

    private var _binding: FragmentProfileUmkmBinding? = null
    private val binding get() = _binding as FragmentProfileUmkmBinding

    private val profileUmkmViewModel: ProfileUmkmViewModel by viewModels()

    private lateinit var umkmAct: UmkmActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        umkmAct = activity as UmkmActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            showDialogLogout()
            profileUmkmViewModel.profileUmkmState.observe(viewLifecycleOwner) { state ->
                if (state.isSuccess) {
                    navigateToLogin()
                }
            }
        }
    }

    private fun showDialogLogout() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Kamu yakin keluar ?")
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Yap") { dialog, _ ->
                profileUmkmViewModel.logout()
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        val intent = Intent(umkmAct, AuthActivity::class.java)
        umkmAct.startActivity(intent)
        umkmAct.finish()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}