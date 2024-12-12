package com.example.car_request2.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.car_request2.R

object Dialog {
    fun showRoundedErrorDialog(context: Context, title: String, message: String) {
        val dialog = AlertDialog.Builder(context).create()

        // Inflar um layout customizado
        val customView = LayoutInflater.from(context).inflate(R.layout.dialog_error, null)

        // Configurar título e mensagem no layout
        customView.findViewById<TextView>(R.id.dialogTitle).text = title
        customView.findViewById<TextView>(R.id.dialogMessage).text = message
        customView.findViewById<Button>(R.id.dialogButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(customView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}