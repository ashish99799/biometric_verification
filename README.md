# Biometric Verification
This library are used for verify mobile biometric verification.

## Integrate the library.
To get a Git project into your build:

<b>Step 1.</b> Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```kotlin
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

<b>Step 2.</b> Add the dependency

```kotlin
dependencies {
	implementation 'com.github.ashish99799:biometric_verification:1.0.0'
}
```

## How to use.

<b>Step 1.</b> Create `FingerprintManager` variable in `Activity` or `Fragment` while click on any object.

```kotlin
lateinit var mFingerprintManager: FingerprintManager

viewClick.setOnClickListener {
	mFingerprintManager = FingerprintManager.BiometricBuilder(this)
		.setTitle(title)
		.setSubtitle(subtitle)
		.setDescription(description)
		.setNegativeButtonText(negativeButtonText)
		.build().also {
		    //start authentication
		    it.authenticate(fingerprintCallback)
		}
}
```

<b>Step 2.</b> Create `FingerprintCallback` variable with the following callback methods.

```kotlin
val fingerprintCallback = object : FingerprintCallback {
    override fun onSdkVersionNotSupported() {
	// Will be called if the device SDK version does not support Biometric authentication.
    }

    override fun onFingerprintAuthenticationNotSupported() {
	// Will be called if the device does not contain any fingerprint sensors.
    }

    override fun onFingerprintAuthenticationNotAvailable() {
	// The device does not have any biometrics registered in the device.
    }

    override fun onFingerprintAuthenticationPermissionNotGranted() {
	// android.permission.USE_BIOMETRIC permission is not granted to the app.
    }

    override fun onFingerprintAuthenticationInternalError(error: String?) {
	// This method is called if one of the fields text is empty.
	// such as the title, subtitle, description or negative button.
    }

    override fun onAuthenticationFailed() {
	// When the fingerprint doesn’t match with any of the fingerprints registered on the device.
    }

    override fun onAuthenticationCancelled() {
	// The authentication is cancelled by the user.
	mFingerprintManager.cancelAuthentication()
    }

    override fun onAuthenticationSuccessful() {
	// When the fingerprint is has been successfully matched with one of the fingerprints registered on the device.
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
	// This method is called when a non-fatal error has occurred during the authentication process.
	// The callback will be provided with an help code to identify the cause of the error, along with a help message.
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
	// When an unrecoverable error has been encountered and the authentication process has completed without success.
	// The callback is provided with an error code to identify the cause of the error, along with the error message. 
    }

}
```

![image](https://user-images.githubusercontent.com/25197921/169692550-00176a2f-a83b-496a-a0f1-6b6beaa74e4b.png)









