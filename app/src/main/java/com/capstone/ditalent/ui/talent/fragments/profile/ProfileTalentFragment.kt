package com.capstone.ditalent.ui.talent.fragments.profile

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
import com.capstone.ditalent.utils.Utilities.getInitialName
import com.capstone.ditalent.utils.Utilities.showSnackBar
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileTalentFragment : Fragment() {

    private var _binding: FragmentProfileTalentBinding? = null
    private val binding get() = _binding as FragmentProfileTalentBinding

    private val profileTalentViewModel: ProfileTalentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileTalentBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.title = getString(R.string.profil)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileTalentViewModel.user.observe(viewLifecycleOwner) { state ->
            when {
                state.isError -> showSnackBar(requireContext(), "error", binding.root)
                state.isLoading -> showSnackBar(requireContext(), "loading", binding.root)
                state.user != null -> setupDataProfile(state.user)
            }
        }
    }

    private fun setupDataProfile(user: User) {
        binding.apply {
            val talent = user.talent
            if (talent != null) {
                if (user.avatar.isEmpty()) {

                    ivAvatar.avatarInitials = talent.fullName.getInitialName()

                } else {
                    ivAvatar.load(user.avatar) {
                        transformations(CircleCropTransformation())
                    }
                }
                tvFullname.text = talent.fullName
                tvUsername.text = user.username
                tvAboutUser.text = getString(
                    R.string.about_user,
                    getString(R.string.year_age, talent.age),
                    talent.region
                )
                generateChipInfluence(talent.influences)
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
                        requireContext(),
                        R.color.purple_light
                    )
                )
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                isEnabled = false
            }
            binding.cgInfluence.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}