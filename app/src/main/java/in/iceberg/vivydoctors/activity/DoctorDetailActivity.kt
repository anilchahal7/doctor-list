package `in`.iceberg.vivydoctors.activity

import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.db.SharedPreferencesCreator
import `in`.iceberg.vivydoctors.util.TextUtils
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_doctor_detail.*
import java.math.RoundingMode

class DoctorDetailActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun newInstance(context: Context, id: String): Intent {
            val intent = Intent(context, DoctorDetailActivity::class.java)
            intent.putExtra("ID", id)
            return intent
        }
    }

    private var doctorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setIntentProperties()
        setContentView(R.layout.activity_doctor_detail)
        toolbar.title = "Doctor Detail"
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{finish()}
        val doctorData = SharedPreferencesCreator(this).getDoctorItem(doctorId!!)

        if (TextUtils.isNotNullOrEmpty(doctorData?.name)) {
            val name = "Name - "
            doctorName.text = """$name${doctorData?.name}"""
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.photoId)) {
            Picasso.with(this).load(doctorData?.photoId).into(doctorPhotoId)
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.address)) {
            val address = "Address - "
            doctorAddress.text = """$address${doctorData?.address}"""
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.phoneNumber)) {
            val phoneNumber = "Phone "
            doctorContactNumber.text = """$phoneNumber${doctorData?.phoneNumber}"""
        }
        if (doctorData?.rating!! > 0) {
            val number = doctorData.rating
            val rounded = number.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            val ratingNumber = "Rating "
            doctorRating.text = """$ratingNumber${rounded}"""
        }
    }

    private fun setIntentProperties() {
        doctorId = intent.getStringExtra("ID")
    }
}
