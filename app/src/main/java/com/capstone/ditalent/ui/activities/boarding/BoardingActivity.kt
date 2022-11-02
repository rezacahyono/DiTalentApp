package com.capstone.ditalent.ui.activities.boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.BoardingPageAdapter
import com.capstone.ditalent.databinding.ActivityBoardingBinding
import com.capstone.ditalent.ui.activities.MainActivity
import com.capstone.ditalent.ui.fragments.slide.SlideFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoardingActivity : AppCompatActivity() {
    private var _binding: ActivityBoardingBinding? = null
    private val binding get() = _binding as ActivityBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUi()

        setupSlideBoarding()

    }

    private fun setupSlideBoarding() {
        val slideBoardOne = SlideFragment.newInstance(
            imageBoard = R.drawable.slide_board_1,
            titleBoard = R.string.title_slide_one,
            descriptionBoard = R.string.description_slide_one
        )

        val slideBoardTwo = SlideFragment.newInstance(
            imageBoard = R.drawable.slide_board_2,
            titleBoard = R.string.title_slide_two,
            descriptionBoard = R.string.description_slide_two
        )

        val slideBoardThree = SlideFragment.newInstance(
            imageBoard = R.drawable.slide_board_3,
            titleBoard = R.string.title_slide_three,
            descriptionBoard = R.string.description_slide_three
        )

        val listSlides = listOf(slideBoardOne, slideBoardTwo, slideBoardThree)

        val boardingPageAdapter = BoardingPageAdapter(this, listSlides)

        binding.pageBoarding.apply {
            adapter = boardingPageAdapter

            // Set disable boarding swiping
            isUserInputEnabled = false
        }

        val changePage = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigateHandlingSlide(position, listSlides.size - 1)
            }
        }
        binding.pageBoarding.registerOnPageChangeCallback(changePage)

        binding.indicatorVp.attachTo(binding.pageBoarding)
    }

    internal fun navigateHandlingSlide(position: Int, lastPage: Int) {
        binding.btnNext.setOnClickListener {
            if (position == lastPage) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                binding.pageBoarding.currentItem = position + 1
            }
        }
    }

    private fun hideSystemUi() {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        val statusBar = WindowInsetsCompat.Type.statusBars()
        val navBars = WindowInsetsCompat.Type.navigationBars()
        insetsController.hide(statusBar)
        insetsController.hide(navBars)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}