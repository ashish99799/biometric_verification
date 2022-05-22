package com.fingerprint.verification

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fingerprint.verification.biometric_interface.FingerprintCallback
import com.fingerprint.verification.biometric_manager.FingerprintManager
import com.fingerprint.verification.biometric_utils.displayBiometricPrompt
import com.fingerprint.verification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FingerprintCallback {

    lateinit var mFingerprintManager: FingerprintManager
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDialog.setOnClickListener {
            mFingerprintManager = displayBiometricPrompt(
                getString(R.string.biometric_title),
                getString(R.string.biometric_subtitle),
                getString(R.string.biometric_description),
                getString(R.string.biometric_negative_button_text),
                this
            )
        }
    }

    override fun onSdkVersionNotSupported() {
        Toast.makeText(this, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onFingerprintAuthenticationNotSupported() {
        Toast.makeText(this, getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onFingerprintAuthenticationNotAvailable() {
        Toast.makeText(this, getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onFingerprintAuthenticationPermissionNotGranted() {
        Toast.makeText(this, getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show()
    }

    override fun onFingerprintAuthenticationInternalError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(this, getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(this, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()
        mFingerprintManager.cancelAuthentication()
    }

    override fun onAuthenticationSuccessful() {
        Toast.makeText(this, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        Toast.makeText(this, helpString, Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Toast.makeText(this, errString, Toast.LENGTH_LONG).show();
    }
}