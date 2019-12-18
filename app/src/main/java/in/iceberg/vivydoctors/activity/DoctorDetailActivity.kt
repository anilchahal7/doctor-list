package `in`.iceberg.vivydoctors.activity

import `in`.iceberg.vivydoctors.R
import `in`.iceberg.vivydoctors.db.SharedPreferencesCreator
import `in`.iceberg.vivydoctors.util.TextUtils
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            doctorName.text = resources.getString(R.string.name_text, doctorData?.name)
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.photoId)) {
            Picasso.with(this).load(doctorData?.photoId).placeholder(R.drawable.ic_doctor).
                into(doctorPhotoId)
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.address)) {
            doctorAddress.text = resources.getString(R.string.address_text, doctorData?.address)
        }
        if (TextUtils.isNotNullOrEmpty(doctorData?.phoneNumber)) {
            doctorContactNumber.text = resources.getString(R.string.phone_text, doctorData?.phoneNumber)
        }
        if (doctorData?.rating!! > 0) {
            val rounded = doctorData.rating.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            doctorRating.text = resources.getString(R.string.rating_text, rounded.toString())
        }
        if (doctorData.reviewCount > 0) {
            doctorReviewCount.text = resources.getString(R.string.review_text, doctorData.reviewCount.toString())
        }
        if (TextUtils.isNotNullOrEmpty(doctorData.website)) {
            doctorWebSite.text = resources.getString(R.string.site_text, doctorData.website)
        }
    }

    private fun setIntentProperties() {
        doctorId = intent.getStringExtra("ID")
    }
}
