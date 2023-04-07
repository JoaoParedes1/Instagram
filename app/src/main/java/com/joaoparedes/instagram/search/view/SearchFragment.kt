package com.joaoparedes.instagram.search.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.BaseFragment
import com.joaoparedes.instagram.common.base.DependencyInjector
import com.joaoparedes.instagram.common.model.UserAuth
import com.joaoparedes.instagram.databinding.FragmentSearchBinding
import com.joaoparedes.instagram.search.Search
import com.joaoparedes.instagram.search.presenter.SearchPresenter

class SearchFragment : BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
    ), Search.View {

    override lateinit var presenter: Search.Presenter
    private val adapter by lazy { SearchAdapter(onItemClicked) }

    private var searchListener: SearchListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchListener) {
            searchListener = context
        }
    }

    override fun setupViews() {
        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRv?.adapter = adapter
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.SearchRepository()
        presenter = SearchPresenter(this, repository)
    }

    private val onItemClicked: (String) -> Unit = { uuid ->
        searchListener?.goToProfile(uuid)
    }

    override fun getMenu(): Int = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true) {
                        presenter.fetchUsers(newText)
                        return true
                    }
                    return false
                }

            })
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.searchProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullUsers(users: List<UserAuth>) {
        binding?.searchTxtEmpty?.visibility = View.GONE
        binding?.searchRv?.visibility = View.VISIBLE
        adapter.items = users
        adapter.notifyDataSetChanged()
    }

    override fun displayEmptyUsers() {
        binding?.searchTxtEmpty?.visibility = View.VISIBLE
        binding?.searchRv?.visibility = View.GONE
    }

    interface SearchListener {
        fun goToProfile(uuid: String)
    }
}