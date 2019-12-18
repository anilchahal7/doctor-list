package `in`.iceberg.vivydoctors.db

import `in`.iceberg.domain.model.Doctor
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SharedPreferencesCreator constructor(context: Context) {

    companion object {
        private const val PREF_NAME = "in.iceberg.vivydoctors.cache.preferences"
        private const val RECENTLY_VISITED_DOCTOR_KEY = "RECENTLY_VISITED_DOCTOR_KEY"
    }

    private val sharedPreferences: SharedPreferences
    private val gSonBuilder: Gson

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        gSonBuilder = GsonBuilder().setPrettyPrinting().create()
    }

    fun setDoctorsList(doctor: Doctor) {
        val savedData = sharedPreferences.getString(RECENTLY_VISITED_DOCTOR_KEY, null)
        if (savedData != null) {
            val savedDoctorList: MutableList<Doctor> = gSonBuilder.fromJson(
                savedData, object : TypeToken<MutableList<Doctor>>() {}.type)
            val position = isItemAddedAlready(doctor.id, savedDoctorList)
            if (position == -1) {
                savedDoctorList.add(0, doctor)
                saveDoctorItemList(savedDoctorList)
            } else {
                savedDoctorList.removeAt(position)
                savedDoctorList.add(0, doctor)
                saveDoctorItemList(savedDoctorList)
            }
        } else {
            val recentlyViewedDoctorList: MutableList<Doctor> = mutableListOf()
            recentlyViewedDoctorList.add(doctor)
            saveDoctorItemList(recentlyViewedDoctorList)
        }
    }

    private fun saveDoctorItemList(savedDoctorList: MutableList<Doctor>) {
        if (savedDoctorList.count() < 3) {
            val savedDoctorString = gSonBuilder.toJson(savedDoctorList)
            sharedPreferences.edit().putString(RECENTLY_VISITED_DOCTOR_KEY, savedDoctorString).apply()
        } else {
            val savedDoctorString = gSonBuilder.toJson(savedDoctorList)
            sharedPreferences.edit().putString(RECENTLY_VISITED_DOCTOR_KEY, savedDoctorString).apply()
        }
    }

    fun getDoctorsList(): MutableList<Doctor> {
        var recentlyViewedDoctorList: MutableList<Doctor> = mutableListOf()
        val savedData = sharedPreferences.getString(RECENTLY_VISITED_DOCTOR_KEY, null)
        if (savedData != null) {
            recentlyViewedDoctorList = gSonBuilder.fromJson(
                savedData, object : TypeToken<MutableList<Doctor>>() {}.type)
        }
        return recentlyViewedDoctorList
    }

    fun getRecentlyContactedThreeDoctorsList(): MutableList<Doctor> {
        var recentlyViewedDoctorList: MutableList<Doctor> = mutableListOf()
        val savedData = sharedPreferences.getString(RECENTLY_VISITED_DOCTOR_KEY, null)
        if (savedData != null) {
            recentlyViewedDoctorList = gSonBuilder.fromJson(
                savedData, object : TypeToken<MutableList<Doctor>>() {}.type)
            val filteredList: MutableList<Doctor> = mutableListOf()
            for (i in recentlyViewedDoctorList.indices) {
                if (i < 3) {
                    filteredList.add(recentlyViewedDoctorList[i])
                } else {
                    break
                }
            }
            return filteredList
        }
        return recentlyViewedDoctorList
    }

    fun getDoctorItem(id: String): Doctor? {
        val doctorList: MutableList<Doctor>
        val savedData = sharedPreferences.getString(RECENTLY_VISITED_DOCTOR_KEY, null)
        if (savedData != null) {
            doctorList = gSonBuilder.fromJson(savedData, object : TypeToken<MutableList<Doctor>>() {}.type)
            val iterator = doctorList.listIterator()
            for (item in iterator) {
                if (item.id == id) {
                    return item
                }
            }
        }
        return null
    }

    private fun isItemAddedAlready(id: String, doctorList: MutableList<Doctor>): Int {
        var position: Int = -1
        for (i in doctorList.indices) {
            if (doctorList[i].id == id) {
                position = i
                break
            }
        }
        return position
    }

    fun clearStoredData() {
        sharedPreferences.edit().putString(RECENTLY_VISITED_DOCTOR_KEY, null).apply()
    }
}