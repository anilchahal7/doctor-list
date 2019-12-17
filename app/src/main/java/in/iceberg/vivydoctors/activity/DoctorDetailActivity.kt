package `in`.iceberg.vivydoctors.activity

import `in`.iceberg.vivydoctors.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_doctor_detail.*

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
        doctorName.text = doctorId
    }

    private fun setIntentProperties() {
        doctorId = intent.getStringExtra("ID")
    }
}
