package com.wizeline.academy.animations.ui.splash_screen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wizeline.academy.animations.databinding.SplashFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            runTogetherAnimation()
            delay(2000)
            goToHomeScreen()
        }
    }

    private fun goToHomeScreen() {
        val directions = SplashFragmentDirections.toMainFragment()
        findNavController().navigate(directions)
    }

    private fun restoreOriginalValues() {
        binding.ivWizelineLogo.apply {
            translationX = 0f
            translationY = 0f
            rotation = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }

    private val blinkAnimator by lazy {
        ObjectAnimator.ofFloat(binding.ivWizelineLogo, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            repeatCount = 4
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateInterpolator()
            doOnCancel { restoreOriginalValues() }
            doOnStart { }
            doOnRepeat { }
            doOnEnd { }
        }
    }

    private val bounceAnimator by lazy {
        ObjectAnimator.ofFloat(
            binding.ivWizelineLogo,
            View.TRANSLATION_Y,
            -binding.ivWizelineLogo.height.toFloat(),
            0F
        ).apply {
            duration = 750
            interpolator = BounceInterpolator()
            doOnCancel { restoreOriginalValues() }
        }
    }

    private fun runTogetherAnimation() {
        AnimatorSet().apply {
            play(blinkAnimator)
                .with(bounceAnimator)
        }.start()
    }
}