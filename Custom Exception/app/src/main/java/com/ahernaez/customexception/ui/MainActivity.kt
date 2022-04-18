package com.ahernaez.customexception.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahernaez.customexception.databinding.ActivityMainBinding
import com.ahernaez.customexception.validation.CustomException
import com.ahernaez.customexception.validation.InputValidator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmBtn.setOnClickListener {

            if (areInputValid()){
                Toast.makeText(this, "All inputs are valid!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun  areInputValid(): Boolean{

        var areInputValid = true

        try {
            InputValidator.validateEmail(binding.emailTextInputEditText.text.toString())
            binding.emailTextInputLayout.error = null
        }
        catch (e: CustomException.InvalidEmailException){

            binding.emailTextInputLayout.error = e.message
            areInputValid = false
        }

        try {
            InputValidator.validatePassword(binding.passwordInputEditText.text.toString())
            binding.passwordTextInputLayout.error = null
        }
        catch (e: CustomException.InvalidPasswordException){

            binding.passwordTextInputLayout.error = e.message
            areInputValid = false
        }

        return areInputValid
    }
}