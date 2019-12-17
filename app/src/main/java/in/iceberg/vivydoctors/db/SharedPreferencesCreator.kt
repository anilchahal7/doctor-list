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
            if (!isItemAddedAlready(doctor.id, savedDoctorList)) {
                savedDoctorList.add(0, doctor)
                val savedDoctorString = gSonBuilder.toJson(savedDoctorList)
                sharedPreferences.edit().putString(RECENTLY_VISITED_DOCTOR_KEY, savedDoctorString).apply()
            }
        } else {
            val recentlyViewedDoctorList: MutableList<Doctor> = mutableListOf()
            recentlyViewedDoctorList.add(doctor)
            val data = gSonBuilder.toJson(recentlyViewedDoctorList)
            sharedPreferences.edit().putString(RECENTLY_VISITED_DOCTOR_KEY, data).apply()
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

    private fun isItemAddedAlready(id: String, doctorList: MutableList<Doctor>): Boolean {
        val iterator = doctorList.listIterator()
        for (item in iterator) {
            if (item.id == id) {
                return true
            }
        }
        return false
    }
}