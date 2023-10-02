package com.muratalarcin.yemektarifi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.adapter.SpecificationAdapter
import com.muratalarcin.yemektarifi.databinding.FragmentListBinding
import com.muratalarcin.yemektarifi.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val specificationAdapter = SpecificationAdapter(arrayListOf())
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // SearchView'i etkinleştir

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_favorite -> {
                    // Favori butonuna tıklandığında yapılacak işlemler
                    val action = ListFragmentDirections.actionListFragmentToFavoriteFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                    true
                }

                else -> false
            }
        }

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

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromAPI()
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

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { searchResult ->
            searchResult?.let {
                // Search sonucuna göre adapter'ı güncelleyin
                specificationAdapter.updateSpecificationList(searchResult)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Burada actionLayout özelliğini belirttiğimiz layout dosyasını kullanıyoruz.
        val searchViewCard = searchView.findViewById<CardView>(R.id.searchViewCard)

        searchView.queryHint = "Yemek ara..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Kullanıcının metin girdiğinde, ListViewModel'deki searchSpecifications fonksiyonunu çağırarak aramayı gerçekleştirin.
                viewModel.searchSpecifications(newText.orEmpty())
                return true
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // SearchView'i açmak için gerekli işlemleri gerçekleştirin
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
