package com.fingerprint.verification.biometric_manager

import android.annotation.TargetApi
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import com.fingerprint.verification.biometric_callback.FingerprintCallbackV28
import com.fingerprint.verification.biometric_interface.FingerprintCallback
import com.fingerprint.verification.biometric_utils.*

open class FingerprintManager(biometricBuilder: BiometricBuilder) : FingerprintManagerV23() {

    private var mCancellationSignal = CancellationSignal()

    fun authenticate(biometricCallback: FingerprintCallback) {
        if (title == null) {
            biometricCallback.onFingerprintAuthenticationInternalError("Biometric Dialog title cannot be null")
            return
        }
        if (subtitle == null) {
            biometricCallback.onFingerprintAuthenticationInternalError("Biometric Dialog subtitle cannot be null")
            return
        }
        if (description == null) {
            biometricCallback.onFingerprintAuthenticationInternalError("Biometric Dialog description cannot be null")
            return
        }
        if (negativeButtonText == null) {
            biometricCallback.onFingerprintAuthenticationInternalError("Biometric Dialog negative button text cannot be null")
            return
        }
        if (!isSdkVersionSupported()) {
            biometricCallback.onSdkVersionNotSupported()
            return
        }
        if (!context!!.isPermissionGranted()) {
            biometricCallback.onFingerprintAuthenticationPermissionNotGranted()
            return
        }
        if (!context!!.isHardwareSupported()) {
            biometricCallback.onFingerprintAuthenticationNotSupported()
            return
        }
        if (!context!!.isFingerprintAvailable()) {
            biometricCallback.onFingerprintAuthenticationNotAvailable()
            return
        }
        displayBiometricDialog(biometricCallback)
    }

    fun cancelAuthentication() {
        if (isBiometricPromptEnabled()) {
            if (!mCancellationSignal.isCanceled) mCancellationSignal.cancel()
        } else {
            if (!mCancellationSignalV23.isCanceled) mCancellationSignalV23.cancel()
        }
    }

    private fun displayBiometricDialog(biometricCallback: FingerprintCallback) {
        if (isBiometricPromptEnabled()) {
            displayBiometricPrompt(biometricCallback)
        } else {
            displayBiometricPromptV23(biometricCallback)
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(biometricCallback: FingerprintCallback) {
        BiometricPrompt.Builder(context)
            .setTitle(title ?: "")
            .setSubtitle(subtitle ?: "")
            .setDescription(description ?: "")
            .setNegativeButton(negativeButtonText ?: "", context!!.mainExecutor) { dialogInterface, i ->
                biometricCallback.onAuthenticationCancelled()
            }
            .build()
            .authenticate(
                mCancellationSignal, context!!.mainExecutor,
                FingerprintCallbackV28(biometricCallback)
            )
    }

    class BiometricBuilder(val context: Context) {
        var title: String? = null
        var subtitle: String? = null
        var description: String? = null
        var negativeButtonText: String? = null

        fun setTitle(title: String): BiometricBuilder {
            this.title = title
            return this
        }

        fun setSubtitle(subtitle: String): BiometricBuilder {
            this.subtitle = subtitle
            return this
        }

        fun setDescription(description: String): BiometricBuilder {
            this.description = description
            return this
        }

        fun setNegativeButtonText(negativeButtonText: String): BiometricBuilder {
            this.negativeButtonText = negativeButtonText
            return this
        }

        fun build(): FingerprintManager {
            return FingerprintManager(this)
        }
    }

    init {
        context = biometricBuilder.context
        title = biometricBuilder.title
        subtitle = biometricBuilder.subtitle
        description = biometricBuilder.description
        negativeButtonText = biometricBuilder.negativeButtonText
    }
}