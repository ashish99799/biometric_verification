package com.fingerprint.verification.biometric_interface

interface FingerprintCallback {
    fun onSdkVersionNotSupported()
    fun onFingerprintAuthenticationNotSupported()
    fun onFingerprintAuthenticationNotAvailable()
    fun onFingerprintAuthenticationPermissionNotGranted()
    fun onFingerprintAuthenticationInternalError(error: String?)
    fun onAuthenticationFailed()
    fun onAuthenticationCancelled()
    fun onAuthenticationSuccessful()
    fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?)
    fun onAuthenticationError(errorCode: Int, errString: CharSequence?)
}