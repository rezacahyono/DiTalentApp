package com.capstone.ditalent.ui.talent.fragments.profile

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentProfileTalentBinding
import com.capstone.ditalent.model.Influence
import com.capstone.ditalent.model.User
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.ui.talent.activities.TalentActivity
import com.capstone.ditalent.utils.Constant.NULL
import com.capstone.ditalent.utils.Utilities.getInitialName
import com.google.android.material.chip.Chip
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

        profileTalentViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                setupDataProfile(it)
            }
        }
    }

    private fun setupDataProfile(user: User) {
        binding.apply {
            val photo = user.photo
            if (photo.isNullOrBlank() || photo == NULL) {
                ivAvatar.avatarInitials = user.name?.getInitialName()
            } else {
                ivAvatar.load(photo) {
                    transformations(CircleCropTransformation())
                }
            }
            tvFullname.text = user.name
            tvUsername.text = user.email

            btnLogout.setOnClickListener {
                profileTalentViewModel.apply {
                    logout()
                    profiletalentUiState.observe(viewLifecycleOwner) { state ->
                        if (state.isSuccess) {
                            navigateToLogin()
                        }
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