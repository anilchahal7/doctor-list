package `in`.iceberg.vivydoctors.adapter

import `in`.iceberg.domain.model.Doctor
import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.interfaces.DoctorsListListener
import `in`.iceberg.vivydoctors.util.TextUtils
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.doctor_item_layout.view.*
import java.math.RoundingMode

class DoctorItemAdapter constructor(
        private val doctorsListListener: DoctorsListListener,
        private val context: Context
    ) : RecyclerView.Adapter<DoctorItemAdapter.ViewHolder>() {

    var doctorDataList: MutableList<Doctor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.doctor_item_layout,
            parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorDataList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = doctorDataList[position]
        holder.bindData(data, position)
        if (position == doctorDataList.count() - 5) {
            doctorsListListener.onBottomReached()
        }
    }

    fun filerList(filterDoctorList: MutableList<Doctor>) {
        doctorDataList = filterDoctorList
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(doctorData: Doctor, position: Int) {
            if (TextUtils.isNotNullOrEmpty(doctorData.name)) {
                val name = "Name - "
                containerView.doctorName.text = """$name${doctorData.name}"""
            }
            if (TextUtils.isNotNullOrEmpty(doctorData.photoId)) {
                Picasso.with(context).load(doctorData.photoId).into(containerView.doctorPhotoId)
            }
            if (TextUtils.isNotNullOrEmpty(doctorData.address)) {
                val address = "Address - "
                containerView.doctorAddress.text = """$address${doctorData.address}"""
            }
            if (TextUtils.isNotNullOrEmpty(doctorData.phoneNumber)) {
                val phoneNumber = "Phone "
                containerView.doctorContactNumber.text = """$phoneNumber${doctorData.phoneNumber}"""
            }
            if (doctorData.rating > 0) {
                val number = doctorData.rating
                val rounded = number.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                val ratingNumber = "Rating "
                containerView.doctorRating.text = """$ratingNumber${rounded}"""
            }
            containerView.setOnClickListener{
                doctorsListListener.onItemClick(doctorData, position)
            }
        }
    }
}