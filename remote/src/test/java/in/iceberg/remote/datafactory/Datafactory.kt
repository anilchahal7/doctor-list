package `in`.iceberg.remote.datafactory

import `in`.iceberg.domain.model.Doctor
import `in`.iceberg.domain.response.DoctorsListResponse
import java.util.*

object DataFactory {
    fun getRandomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun getRandomInt(): Int {
        return (Math.random() * 1000).toInt()
    }

    private fun getRandomDouble(): Double {
        return (10) * Math.random()
    }

    private fun getRandomBoolean(): Boolean {
        return Random().nextBoolean()
    }

    private fun getRandomArrayInteger() : Array<Int> {
        return Array(5) { getRandomInt() }
    }

    private fun getRandomArrayString(): Array<String> {
        return Array(5) { getRandomString() }
    }

    private fun getRandomDoctor(): Doctor {
        return Doctor(getRandomString(), getRandomString(), getRandomString(), getRandomDouble(),
            getRandomString(), getRandomDouble(), getRandomDouble(), getRandomBoolean(),
            getRandomInt(), getRandomArrayInteger(), getRandomString(), getRandomString(), getRandomString(),
            getRandomString(), getRandomArrayString(), getRandomString(), getRandomString()
        )
    }

    private fun getDoctorList(): MutableList<Doctor> {
        val results = mutableListOf<Doctor>()
        repeat(20) {
            results.add(getRandomDoctor())
        }
        return results
    }

    fun getRandomDoctorListResponse(): DoctorsListResponse {
        return DoctorsListResponse(getDoctorList(), getRandomString())
    }
}