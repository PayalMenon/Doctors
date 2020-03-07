package com.example.doctors.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.doctors.R
import com.example.doctors.util.EventObserver
import com.example.doctors.viewmodel.SearchViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.search_activity.*
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java)
        setContentView(R.layout.search_activity)
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchMenuItem?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.onSearchSelected(query)
                    observeDoctorData()
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun setListeners() {

        searchViewModel.showLoading.observe(this, EventObserver {
            this.loading.visibility = View.VISIBLE
        })

        searchViewModel.hideLoading.observe(this, EventObserver {
            this.loading.visibility = View.GONE
        })

        searchViewModel.hideText.observe(this, EventObserver {
            this.info_text.visibility = View.GONE
        })

        searchViewModel.showDoctorListFragment.observe(this, EventObserver {
            this.fragment_container.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, DoctorListFragment()).commit()
        })

        searchViewModel.showDoctorDetailFragment.observe(this, EventObserver { doctor ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorDetailFragment.newInstance(doctor))
                .addToBackStack(null)
                .commit()
        })

        searchViewModel.networkState.observe(this, EventObserver { errorMessage ->
            this.info_text.visibility = View.VISIBLE
            this.info_text.text = errorMessage
        })
    }

    fun observeDoctorData(){
        searchViewModel.doctorLiveData?.observe(this, Observer { list ->
            searchViewModel.onDoctorListReceived(list)
        })
    }
}
