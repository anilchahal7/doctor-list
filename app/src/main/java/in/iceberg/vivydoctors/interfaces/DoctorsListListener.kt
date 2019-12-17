package `in`.iceberg.vivydoctors.interfaces

import `in`.iceberg.domain.model.Doctor

interface DoctorsListListener {
    fun onBottomReached()
    fun onItemClick(doctor: Doctor, position: Int)
}