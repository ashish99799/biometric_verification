package com.fingerprint.verification.biometric_utils

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.fingerprint.verification.biometric_interface.FingerprintCallback
import com.fingerprint.verification.biometric_manager.FingerprintManager

fun isBiometricPromptEnabled(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}

fun isSdkVersionSupported(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun Context.isHardwareSupported(): Boolean {
    return FingerprintManagerCompat.from(this).isHardwareDetected
}

fun Context.isFingerprintAvailable(): Boolean {
    return FingerprintManagerCompat.from(this).hasEnrolledFingerprints()
}

fun Context.isPermissionGranted(): Boolean {
    return (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED)
}

@TargetApi(Build.VERSION_CODES.P)
fun Context.displayBiometricPrompt(
    title: String,
    subtitle: String,
    description: String,
    negativeButtonText: String,
    biometricCallback: FingerprintCallback
): FingerprintManager {
    return FingerprintManager.BiometricBuilder(this)
        .setTitle(title)
        .setSubtitle(subtitle)
        .setDescription(description)
        .setNegativeButtonText(negativeButtonText)
        .build().also {
            //start authentication
            it.authenticate(biometricCallback)
        }
}