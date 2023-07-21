package com.muratalarcin.yemektarifi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.databinding.FragmentDetailBinding
import com.muratalarcin.yemektarifi.viewmodel.DetailViewModel
import com.muratalarcin.yemektarifi.viewmodel.ListViewModel

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        viewModel.getDataFromRoom()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.specificationLiveData.observe(viewLifecycleOwner, Observer { specification ->
            specification?.let {
                binding.detailName.text = specification.specificationName
                binding.detailTag.text = specification.specificationTag
                binding.detailMaterial.text = specification.specificationMaterial
                binding.detailFabrication.text = specification.specificationFabrication

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
