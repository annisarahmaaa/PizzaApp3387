package com.example.pizzaapp3387

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pizzaapp3387.client.RetrofitClient
import com.example.pizzaapp3387.response.account.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pizzaapp3387.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur padding untuk sistem bar (status bar/navigasi)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets -> // Use binding.root
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Inisialisasi UI (Hubungkan variabel dengan ID di XML)
        val txtUsername: EditText = findViewById(R.id.editTextUsername)
        val txtPassword: EditText = findViewById(R.id.editTextPassword)
        val btnLogin: Button = findViewById(R.id.buttonLogin)

        // 2. Event Klik Tombol Login
        binding.buttonLogin.setOnClickListener {
            // Ambil teks dari inputan dan hapus spasi di awal/akhir (trim)
            val user = binding.editTextUsername.text.toString().trim()
            val pwd = binding.editTextPassword.text.toString().trim()

            // --- VALIDASI: Cek apakah input kosong ---
            if (user.isEmpty()) {
                binding.editTextUsername.error = "Email required"
                binding.editTextUsername.requestFocus()
                return@setOnClickListener
            }
            if (pwd.isEmpty()) {
                binding.editTextPassword.error = "Password required"
                binding.editTextPassword.requestFocus()
                return@setOnClickListener
            }

            // --- RETROFIT: Melakukan Request Login ke Server ---
            // ... (Kode di atasnya biarkan saja)

            // --- RETROFIT: Melakukan Request Login ke Server ---
            RetrofitClient.instance.postLogin(user, pwd).enqueue(object : Callback<LoginResponse> {

                // Perbaikan 1: Tambahkan tanda tanya (?) pada Call dan Response
                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                    // Perbaikan 2: Pakai tanda tanya (?) sebelum .body()
                    val account = response?.body()

                    // Perbaikan 3: Ubah 'succes' jadi 'success' (double s)
                    if (account?.success == true) {
                        Toast.makeText(this@LoginActivity, account.message, Toast.LENGTH_SHORT).show()

                        val intentLogin = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intentLogin)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, account?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                // Perbaikan 4: Tambahkan tanda tanya (?) pada Call dan Throwable
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "Error: ${t?.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}
