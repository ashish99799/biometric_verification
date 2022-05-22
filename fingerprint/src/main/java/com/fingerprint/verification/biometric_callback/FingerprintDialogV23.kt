package com.fingerprint.verification.biometric_callback

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.fingerprint.verification.R
import com.fingerprint.verification.biometric_interface.FingerprintCallback
import com.fingerprint.verification.databinding.ViewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FingerprintDialogV23(val mContext: Context, private var fingerprintCallback: FingerprintCallback?) : BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme), View.OnClickListener {

    private var btnCancel: Button? = null
    private var imgLogo: ImageView? = null
    private var itemTitle: TextView? = null
    private var itemDescription: TextView? = null
    private var itemSubtitle: TextView? = null
    private var itemStatus: TextView? = null

    init {
        setDialogView()
    }

    private fun setDialogView() {
        val bottomSheetView = ViewBottomSheetBinding.inflate(LayoutInflater.from(mContext), null, false)
        setContentView(bottomSheetView.root)
        btnCancel = bottomSheetView.btnCancel
        btnCancel!!.setOnClickListener(this)
        imgLogo = bottomSheetView.imgLogo
        itemTitle = bottomSheetView.itemTitle
        itemStatus = bottomSheetView.itemStatus
        itemSubtitle = bottomSheetView.itemSubtitle
        itemDescription = bottomSheetView.itemDescription
        updateLogo()
    }

    fun setTitle(title: String?) {
        itemTitle!!.text = title
    }

    fun updateStatus(status: String?) {
        itemStatus!!.text = status
    }

    fun setSubtitle(subtitle: String?) {
        itemSubtitle!!.text = subtitle
    }

    fun setDescription(description: String?) {
        itemDescription!!.text = description
    }

    fun setButtonText(negativeButtonText: String?) {
        btnCancel!!.text = negativeButtonText
    }

    private fun updateLogo() {
        try {
            val drawable = context.packageManager.getApplicationIcon(context.packageName)
            imgLogo!!.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View) {
        dismiss()
        fingerprintCallback!!.onAuthenticationCancelled()
    }
}