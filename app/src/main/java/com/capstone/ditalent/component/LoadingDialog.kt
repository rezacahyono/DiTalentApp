package com.capstone.ditalent.component

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.capstone.ditalent.databinding.DialogLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialog(context: Context) {

    private var _binding: DialogLoadingBinding? = null
    private val binding get() = _binding as DialogLoadingBinding

    private var dialog: AlertDialog

    init {
        _binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        val builder = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .setCancelable(false)
        dialog = builder.create()
    }
    fun showDialog() {
        dialog.show()
    }

    fun hideDialog() {
        dialog.cancel()
        _binding = null
    }
}