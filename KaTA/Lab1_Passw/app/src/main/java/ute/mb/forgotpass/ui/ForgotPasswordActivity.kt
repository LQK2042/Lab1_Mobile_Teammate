package ute.mb.forgotpass.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var sendOtpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailInput = findViewById(R.id.emailInput)
        sendOtpBtn = findViewById(R.id.sendOtpBtn)

        sendOtpBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "https://tpb-mobile-backend.onrender.com/api/auth/forgot-password"
            val json = JSONObject().apply { put("email", email) }

            val request = JsonObjectRequest(
                Request.Method.POST, url, json,
                { _ ->
                    Toast.makeText(this, "Đã gửi OTP qua email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ResetPasswordActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                },
                { error ->
                    Toast.makeText(this, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        }
    }
}
