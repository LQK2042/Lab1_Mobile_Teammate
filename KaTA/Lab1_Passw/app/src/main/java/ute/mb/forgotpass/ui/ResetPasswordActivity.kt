package ute.mb.forgotpass.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ResetPasswordActivity {
    class ResetPasswordActivity : AppCompatActivity() {
        private lateinit var otpInput: EditText
        private lateinit var newPassInput: EditText
        private lateinit var resetBtn: Button
        private lateinit var email: String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_reset_password)

            email = intent.getStringExtra("email") ?: ""

            otpInput = findViewById(R.id.otpInput)
            newPassInput = findViewById(R.id.newPassInput)
            resetBtn = findViewById(R.id.resetBtn)

            resetBtn.setOnClickListener {
                val otp = otpInput.text.toString().trim()
                val newPass = newPassInput.text.toString().trim()
                if (otp.isEmpty() || newPass.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val url = "https://tpb-mobile-backend.onrender.com/api/auth/reset-password"
                val json = JSONObject().apply {
                    put("email", email)
                    put("otp", otp)
                    put("newPassword", newPass)
                }

                val request = JsonObjectRequest(
                    Request.Method.POST, url, json,
                    { _ -> Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show() },
                    { error -> Toast.makeText(this, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show() }
                )
                Volley.newRequestQueue(this).add(request)
            }
        }
    }
}