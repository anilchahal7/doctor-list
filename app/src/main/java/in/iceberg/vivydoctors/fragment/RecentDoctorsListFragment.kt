package `in`.iceberg.vivydoctors.fragment

import `in`.iceberg.domain.model.Doctor
import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.adapter.DoctorItemAdapter
import `in`.iceberg.vivydoctors.db.SharedPreferencesCreator
import `in`.iceberg.vivydoctors.interfaces.DoctorsListListener
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.doctors_list_fragment.view.*


class RecentDoctorsListFragment : DaggerFragment(), SearchView.OnQueryTextListener {

    private lateinit var doctorItemAdapter: DoctorItemAdapter
    private lateinit var progressBar: ProgressBar

    private var doctorDataList: MutableList<Doctor> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.doctors_list_fragment, container, false)
        initUI(view)
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun initUI(view: View) {
        doctorItemAdapter = DoctorItemAdapter(object : DoctorsListListener {
            override fun onBottomReached() {
            }
            override fun onItemClick(doctor: Doctor) {
            }
        })
        view.doctorRecyclerView.layoutManager = LinearLayoutManager(activity)
        view.doctorRecyclerView.adapter = doctorItemAdapter
        progressBar = view.progressBar
        view.searchView.setOnQueryTextListener(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            doctorDataList = SharedPreferencesCreator(activity as Context).getDoctorsList()
            doctorItemAdapter.doctorDataList = doctorDataList
            doctorItemAdapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }
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