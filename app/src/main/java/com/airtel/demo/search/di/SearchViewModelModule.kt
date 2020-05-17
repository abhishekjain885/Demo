package com.airtel.demo.search.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airtel.demo.base.viewmodel.ViewModelFactory
import com.airtel.demo.base.viewmodel.ViewModelKey
import com.airtel.demo.search.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@SearchNavScope
abstract class SearchViewModelModule {

    @Binds
    @SearchNavScope
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @SearchNavScope
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun findSearchViewModel(viewModel: SearchViewModel): ViewModel
}