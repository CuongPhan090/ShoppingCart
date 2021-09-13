package com.example.groceryshopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.LOGIN_END_POINT
import com.example.groceryshopper.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(baseContext)

        setupEvents()
    }

    private fun setupEvents() {
        binding.btnLogin.setOnClickListener{
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if(email.isEmpty()) {
                binding.etLoginEmail.error = "Email cannot be blank"
            }
            if (password.isEmpty()) {
                binding.etLoginPassword.error = "Password cannot be blank"
            }

            val userAuthentication = JSONObject()
            userAuthentication.put("email", email)
            userAuthentication.put("password", password)

            val userAuthenticator = JsonObjectRequest(
                Request.Method.POST,
                "${BASE_URL}$LOGIN_END_POINT",
                userAuthentication,
                {
                    val error = it.getString("error").toBoolean()
                    if (error) {
                        Toast.makeText(baseContext, "Incorrect username or password, please try again", Toast.LENGTH_LONG).show()
                    } else {
                        startActivity(Intent(baseContext, CategoryActivity::class.java))
                    }
                }, {
                    it.printStackTrace()
                    Toast.makeText(baseContext, "Unable to retrieve the user data, please try again!", Toast.LENGTH_LONG).show()
                }
            )

            requestQueue.add(userAuthenticator)
        }

        binding.btnRegister.setOnClickListener{
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }

        binding.btnFacebook.setOnClickListener{

        }

        binding.btnGoogle.setOnClickListener{

        }
    }
}