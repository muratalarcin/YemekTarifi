package com.muratalarcin.yemektarifi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.adapter.SpecificationAdapter
import com.muratalarcin.yemektarifi.databinding.FragmentListBinding
import com.muratalarcin.yemektarifi.viewmodel.ListViewModel

class ListFragment : Fragment() {

    lateinit var viewModel: ListViewModel
    private val specificationAdapter = SpecificationAdapter(arrayListOf())
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'ı oluşturunuz.
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        // RecyclerView'a LinearLayoutManager'ı atayın
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // RecyclerView'a adapter'ı atayın
        binding.recyclerView.adapter = specificationAdapter

        // LiveData'ları dinlemeye başlayın.
        observeLiveData()

        // Verileri LiveData'ya eklemek için refreshData() metodunu çağırın.
        viewModel.refreshData()

        binding.swipeRefreshLayout.setOnRefreshListener{
            binding.recyclerView.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeLiveData() {
        viewModel.specifications.observe(viewLifecycleOwner, Observer { specification ->
            specification?.let {
                binding.recyclerView.visibility = View.VISIBLE
                specificationAdapter.updateSpecificationList(specification)
            }
        })

        viewModel.specificationError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    binding.errorText.visibility = View.VISIBLE
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        })

        viewModel.specificationLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

