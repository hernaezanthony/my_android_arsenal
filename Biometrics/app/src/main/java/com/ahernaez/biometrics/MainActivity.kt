package com.ahernaez.biometrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.ahernaez.biometrics.databinding.ActivityMainBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var executor: Executor
    private lateinit var activity: MainActivity
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        executor = Executors.newSingleThreadExecutor()

        setUpBiometricPrompt()
        setUpPromptInfo()
        setUpBiometricManager()
    }

    private fun setUpBiometricPrompt(){

        biometricPrompt = BiometricPrompt(activity, executor, object: BiometricPrompt.AuthenticationCallback(){

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                runOnUiThread {
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        })
    }

    private fun setUpPromptInfo(){

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(true)
            .build()
    }

    private fun setUpBiometricManager(){

        binding.fingerprintIcon.setOnClickListener {


            val biometricManager = BiometricManager.from(this)

            when(biometricManager.canAuthenticate()){

                BiometricManager.BIOMETRIC_SUCCESS -> {

                    biometricPrompt.authenticate(promptInfo)
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    //No fingerprint sensor
                    Toast.makeText(activity, "Unsupported device", Toast.LENGTH_SHORT).show()

                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {

                    Toast.makeText(activity, "Please enroll at least one fingerprint", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}