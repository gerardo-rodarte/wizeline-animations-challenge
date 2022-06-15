package com.wizeline.academy.animations.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.ListAdapter
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.ItemImageBinding
import kotlin.random.Random
import kotlin.random.nextInt

class HomeAdapter(private var onClickListener: (HomeItem) -> Unit) :
    ListAdapter<HomeItem, HomeViewHolder>(HomeItemDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) { animateImage(holder.itemView, getAnimation(), item, onClickListener) }
    }

    @AnimRes
    private fun getAnimation(): Int {
        val animations = listOf(
            R.anim.bounce,
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.move,
            R.anim.rotate,
            R.anim.slide_down,
            R.anim.slide_up,
            R.anim.zoom_in,
            R.anim.zoom_out
        )
        val randomNumber = Random.nextInt(animations.indices)
        return animations[randomNumber]
    }

    private fun animateImage(
        view: View,
        @AnimRes animation: Int,
        homeItem: HomeItem,
        listener: (HomeItem) -> Unit
    ) {
        val animationRes = AnimationUtils.loadAnimation(view.context, animation)
        animationRes.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                listener.invoke(homeItem)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        view.clearAnimation()
        view.startAnimation(animationRes)
    }
}