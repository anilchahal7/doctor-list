package `in`.iceberg.vivydoctors.activity

import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.adapter.TabAdapter
import `in`.iceberg.vivydoctors.fragment.DoctorsListFragment
import `in`.iceberg.vivydoctors.fragment.RecentDoctorsListFragment
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var tabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(DoctorsListFragment(), resources.getString(R.string.tab_one))
        tabAdapter.addFragment(RecentDoctorsListFragment(), resources.getString(R.string.tab_two))
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}