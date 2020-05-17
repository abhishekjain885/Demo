package com.airtel.demo.search.di

import com.airtel.demo.base.di.BaseAppComponent
import com.airtel.demo.search.view.activity.SearchActivity
import com.airtel.demo.search.view.fragment.SearchFragment
import dagger.Component

@SearchNavScope
@Component(
    modules = [SearchModule::class, SearchViewModelModule::class],
    dependencies = [BaseAppComponent::class]
)
interface SearchComponent {
    fun inject(searchActivity: SearchActivity)
    fun inject(searchFragment: SearchFragment)
}


