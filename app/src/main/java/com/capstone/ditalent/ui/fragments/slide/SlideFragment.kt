package com.capstone.ditalent.ui.fragments.slide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentSlideBinding
import com.capstone.ditalent.utils.Constant
import com.capstone.ditalent.utils.Utilities.fromHtml


class SlideFragment : Fragment() {

    private var _binding: FragmentSlideBinding? = null
    private val binding get() = _binding as FragmentSlideBinding

    private var imageBoard: Int = 0
    private var titleBoard: Int = 0
    private var descriptionBoard: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageBoard = it.getInt(Constant.IMAGE_BOARD)
            titleBoard = it.getInt(Constant.TITLE_BOARD)
            descriptionBoard = it.getInt(Constant.DESCRIPTION_BOARD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDisplayBoard(imageBoard, titleBoard, descriptionBoard)
    }

    private fun setupDisplayBoard(
        @DrawableRes imageBoard: Int,
        @StringRes titleBoard: Int,
        @StringRes descriptionBoard: Int
    ) {
        binding.apply {
            ivImageBoarding.setImageResource(imageBoard)
            tvTitleBoarding.text = fromHtml(getString(titleBoard))
            tvDescriptionBoarding.text = getString(descriptionBoard)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(
            @DrawableRes imageBoard: Int,
            @StringRes titleBoard: Int,
            @StringRes descriptionBoard: Int
        ) = SlideFragment().apply {
            arguments = bundleOf(
                Constant.IMAGE_BOARD to imageBoard,
                Constant.TITLE_BOARD to titleBoard,
                Constant.DESCRIPTION_BOARD to descriptionBoard
            )
        }
    }
}