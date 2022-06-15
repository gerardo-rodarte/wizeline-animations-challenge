package com.wizeline.academy.animations.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        setUpImageList()
        return binding.root
    }

    private fun setUpImageList() {
        binding.rvImages.apply {
            adapter = HomeAdapter { item -> goToDetailScreen(item.imageId) }
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun goToDetailScreen(imageId: Int) {
        val directions = HomeFragmentDirections.toDetailFragment(imageId)
        findNavController().navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.images.observe(viewLifecycleOwner) { images -> loadImages(images) }
    }

    private fun loadImages(images: List<HomeItem>) {
        binding.rvImages.apply {
            val adapter = this.adapter as? HomeAdapter
            adapter?.submitList(images)
        }
    }
}