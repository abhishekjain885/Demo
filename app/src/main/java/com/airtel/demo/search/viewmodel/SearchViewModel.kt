package com.airtel.demo.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airtel.demo.base.utils.Result
import com.airtel.demo.search.data.model.SearchResponse
import com.airtel.demo.search.data.repository.SearchRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var searchRepository: SearchRepository

    private val city: String = "gurgaon"

    var searchResult: LiveData<Result<SearchResponse>>? = null

    private val clearScreenFlag = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val _textInput = BehaviorSubject.create<String>()
    private val textInput = _textInput.toFlowable(BackpressureStrategy.LATEST)

    fun init() {
        searchResult = searchRepository.getSearchLiveData()

        disposable.add(
            textInput
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) {
                        fetchSearchResult(it, city)
                    } else {
                        clearScreenFlag.value = true
                    }
                }
        )
    }


    private fun fetchSearchResult(queryString: String, city: String) {
        searchRepository.getSearchData(queryString, city)
    }

    fun isScreenToBeCleared(): LiveData<Boolean> {
        return clearScreenFlag
    }

    fun debounce(searchString: String) {
        _textInput.onNext(searchString)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}