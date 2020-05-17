package com.airtel.demo.search.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.airtel.demo.R
import com.airtel.demo.base.application.MainApplication
import com.airtel.demo.base.utils.Fail
import com.airtel.demo.base.utils.Success
import com.airtel.demo.search.data.model.AddressListItem
import com.airtel.demo.search.di.DaggerSearchComponent
import com.airtel.demo.search.di.SearchComponent
import com.airtel.demo.search.network.APIHandler
import com.airtel.demo.search.viewmodel.SearchViewModel
import com.airtel.demo.search.view.adapter.SearchListAdapter
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var apiHandler: APIHandler

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchComponent: SearchComponent

    lateinit var searchViewModel: SearchViewModel

    private var addressList = ArrayList<AddressListItem>()

    private val searchListAdapter: SearchListAdapter by lazy { SearchListAdapter(addressList) }

    private lateinit var searchRecyclerView: RecyclerView
    lateinit var searchBox: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initInjector()
        initViewModel()
    }

    private fun initView(view: View) {
        searchRecyclerView = view.findViewById(R.id.search_recylerview)
        searchBox = view.findViewById(R.id.search_edit_text)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerView.adapter = searchListAdapter

        searchBox.addTextChangedListener(textWatcher)
    }

    private fun initViewModel() {
        activity?.let {
            val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
            searchViewModel = viewModelProvider.get(SearchViewModel::class.java)
        }

        searchViewModel.init()
        observeData()
    }

    private val textWatcher: TextWatcher by lazy {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.debounce(searchBox.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }
    }


    private fun observeData() {
        searchViewModel.searchResult?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Success -> {
                    it.data.data?.addressList?.let { list ->
                        addressList.clear()
                        addressList.addAll(list)
                        searchRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }

                is Fail -> {
                    Toast.makeText(this.context, getStringRes(it.StringResId), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

        searchViewModel.isScreenToBeCleared().observe(viewLifecycleOwner, Observer {
            addressList.clear()
            searchRecyclerView.adapter?.notifyDataSetChanged()
        })
    }


    private fun initInjector() {
        searchComponent = DaggerSearchComponent.builder()
            .baseAppComponent(
                (activity?.applicationContext as MainApplication)
                    .getBaseAppComponent()
            ).build()

        searchComponent.inject(this)
    }

    private fun getStringRes(stringRes: Int): String? {
        return context?.getString(stringRes)
    }

}
