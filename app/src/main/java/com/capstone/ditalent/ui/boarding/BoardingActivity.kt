package com.capstone.ditalent.ui.boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.BoardingAdapter
import com.capstone.ditalent.databinding.ActivityBoardingBinding
import com.capstone.ditalent.model.Boarding
import com.capstone.ditalent.ui.auth.activities.AuthActivity
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

        val boardingOne = Boarding(
            image = R.drawable.slide_board_1,
            title = R.string.title_slide_one,
            description = R.string.description_slide_one
        )
        val boardingTwo = Boarding(
            image = R.drawable.slide_board_2,
            title = R.string.title_slide_two,
            description = R.string.description_slide_two
        )
        val boardingThree = Boarding(
            image = R.drawable.slide_board_3,
            title = R.string.title_slide_three,
            description = R.string.description_slide_three
        )

        val listBoarding = listOf(boardingOne, boardingTwo, boardingThree)
        val boardingAdapter = BoardingAdapter()
        boardingAdapter.submitList(listBoarding)

        val changePage = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigateHandlingSlide(position, listBoarding.size - 1)

                setupTextButton(position == listBoarding.size - 1)
            }
        }

        binding.pageBoarding.apply {
            adapter = boardingAdapter
            registerOnPageChangeCallback(changePage)
            isUserInputEnabled = false
        }

        binding.indicatorVp.attachTo(binding.pageBoarding)
    }

    internal fun navigateHandlingSlide(position: Int, lastPage: Int) {
        binding.apply {
            btnNext.setOnClickListener {
                if (position == lastPage) {
                    navigateToMain(ROUTE_LOGIN)
                } else {
                    binding.pageBoarding.currentItem = position + 1
                }
            }
            btnSkip.setOnClickListener {
                navigateToMain(
                    if (position != lastPage) ROUTE_LOGIN else ROUTE_REGISTER
                )
            }
        }
    }

    private fun setupTextButton(lastPage: Boolean) {
        val textButtonNext =
            if (lastPage) getString(R.string.text_button_login_now) else getString(R.string.text_button_next)
        val textButtonSkip =
            if (lastPage) getString(R.string.text_button_no_have_account) else getString(R.string.text_button_skip)
        binding.apply {
            btnNext.text = textButtonNext
            btnSkip.text = textButtonSkip
        }
    }

    private fun navigateToMain(route: String) {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(ROUTE, route)
        startActivity(intent)
        finish()
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

    companion object {
        const val ROUTE_REGISTER = "route_register"
        const val ROUTE_LOGIN = "route_login"
        const val ROUTE = "route"
    }
}