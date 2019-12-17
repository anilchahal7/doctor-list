package `in`.iceberg.vivydoctors.fragment

import `in`.iceberg.domain.model.Doctor
import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.presentation.state.ResourceState
import `in`.iceberg.presentation.viewmodel.GetDoctorsListViewModel
import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.activity.DoctorDetailActivity
import `in`.iceberg.vivydoctors.adapter.DoctorItemAdapter
import `in`.iceberg.vivydoctors.db.SharedPreferencesCreator
import `in`.iceberg.vivydoctors.dependencies.ViewModelFactory
import `in`.iceberg.vivydoctors.interfaces.DoctorsListListener
import `in`.iceberg.vivydoctors.util.TextUtils
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.doctors_list_fragment.view.*
import javax.inject.Inject

class DoctorsListFragment : DaggerFragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var getDoctorsListViewModel: GetDoctorsListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var doctorsListResponse: DoctorsListResponse
    private var lastKey: String = ""

    private lateinit var doctorItemAdapter: DoctorItemAdapter
    private lateinit var progressBar: ProgressBar

    private var doctorDataList: MutableList<Doctor> = mutableListOf()

    private var startDoctorDetailActivity: Int = 1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.doctors_list_fragment, container, false)
        initUI(view)
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == startDoctorDetailActivity) {
            doctorItemAdapter.doctorDataList = doctorDataList
            doctorItemAdapter.notifyDataSetChanged()
        }
    }

    private fun initUI(view: View) {
        doctorItemAdapter = DoctorItemAdapter(object : DoctorsListListener {
            override fun onBottomReached() {
                if (TextUtils.isNotNullOrEmpty(lastKey)) {
                    getDoctorsListViewModel.getSearchResponse(lastKey)
                }
                Toast.makeText(activity, "Reached at bottom of the list", Toast.LENGTH_SHORT).show()
            }
            override fun onItemClick(doctor: Doctor, position: Int) {
                SharedPreferencesCreator(activity as Context).setDoctorsList(doctor)
                doctorDataList.removeAt(position)
                val newInstance = DoctorDetailActivity.newInstance(activity as Context, doctor.id)
                activity?.startActivityForResult(newInstance, startDoctorDetailActivity)
            }
        })
        view.doctorRecyclerView.layoutManager = LinearLayoutManager(activity)
        view.doctorRecyclerView.adapter = doctorItemAdapter
        progressBar = view.progressBar
        getDoctorsListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(GetDoctorsListViewModel::class.java)
        observeDoctorsListResponse()
        getDoctorsListViewModel.getSearchResponse(lastKey)
        view.searchView.setOnQueryTextListener(this)
    }

    private fun observeDoctorsListResponse() {
        getDoctorsListViewModel.observeGetSearchResponse().observe(this, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    doctorsListResponse = it.data!!
                    doctorsListResponse.lastKey.let {
                        lastKey = doctorsListResponse.lastKey
                    }
                    doctorDataList.addAll(doctorsListResponse.doctors.toMutableList())
                    doctorDataList = doctorDataList.sortedWith(compareBy({ it.rating })).toMutableList()
                    doctorItemAdapter.doctorDataList = doctorDataList
                    doctorItemAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Doctors List Fetched", Toast.LENGTH_LONG).show()
                }
                ResourceState.ERROR -> {
                    Toast.makeText(activity, "Error In Fetching Doctors List", Toast.LENGTH_LONG).show()
                }
                ResourceState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    Toast.makeText(activity, "Fetching Doctors List", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    @SuppressLint("DefaultLocale")
    private fun search(text: String?) {
        val filterDoctorList: MutableList<Doctor> = mutableListOf()
        val iterator = doctorDataList.listIterator()
        for (item in iterator) {
            if (item.name.toLowerCase().contains(text!!.toLowerCase())) {
                filterDoctorList.add(item)
            }
        }
        doctorItemAdapter.filerList(filterDoctorList)
    }
}