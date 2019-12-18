package `in`.iceberg.vivydoctors.adapter

import `in`.iceberg.domain.model.Doctor
import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.interfaces.DoctorsListListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.doctor_item_layout.view.*

class RecentlyContactedAdapter : RecyclerView.Adapter<RecentlyContactedAdapter.ViewHolder>() {

    var doctorDataList: MutableList<Doctor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recently_contacted_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorDataList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = doctorDataList[position]
        holder.bindData(data, position)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(doctorData: Doctor, position: Int) {
            containerView.doctorName.text = doctorData.name
        }
    }
}