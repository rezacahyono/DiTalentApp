package com.capstone.ditalent.ui.talent.fragments.profile

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentProfileTalentBinding
import com.capstone.ditalent.model.Influence
import com.capstone.ditalent.model.User
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.ui.talent.activities.TalentActivity
import com.capstone.ditalent.utils.Constant.NULL
import com.capstone.ditalent.utils.Utilities
import com.capstone.ditalent.utils.Utilities.dpToPx
import com.capstone.ditalent.utils.Utilities.getInitialName
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileTalentFragment : Fragment() {

    private var _binding: FragmentProfileTalentBinding? = null
    private val binding get() = _binding as FragmentProfileTalentBinding

    private val profileTalentViewModel: ProfileTalentViewModel by viewModels()
    private lateinit var talentAct: TalentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        talentAct = activity as TalentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileTalentBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.title = getString(R.string.profil)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileTalentViewModel.getUser.observe(viewLifecycleOwner) { user ->
            setupDataProfile(user)
        }

    }

    private fun setupDataProfile(user: User) {
        binding.apply {
            val photo = user.photo
            if (photo.isNullOrBlank() || photo == NULL) {
                ivAvatar.apply {
                    avatarInitials = user.name?.getInitialName()
                    avatarCircle = true
                    avatarInitialsBackgroundColor = Utilities.randomColor(requireContext())
                    avatarInitialsTextSize = requireContext().dpToPx(36F)
                }
            } else {
                ivAvatar.load(photo)
            }
            tvFullname.text = user.name
            tvUsername.text = user.email

            btnLogout.setOnClickListener {
                showDialogLogout()
                profileTalentViewModel.profiletalentUiState.observe(viewLifecycleOwner) { state ->
                    if (state.isSuccess) {
                        navigateToLogin()
                    }
                }
            }
        }
    }

    private fun generateChipInfluence(influences: List<Influence>) {
        influences.forEach { influence ->
            val chip = Chip(requireContext())
            chip.apply {
                text = influence.name
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.purple_light
                    )
                )
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                isEnabled = false
            }
            binding.cgInfluence.addView(chip)
        }
    }

    private fun showDialogLogout() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.message_logout))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yap)) { dialog, _ ->
                profileTalentViewModel.logout()
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        val intent = Intent(talentAct, AuthActivity::class.java)
        talentAct.startActivity(intent)
        talentAct.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}