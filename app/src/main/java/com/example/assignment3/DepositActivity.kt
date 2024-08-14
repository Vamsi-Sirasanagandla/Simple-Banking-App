package com.example.assignment3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class DepositActivity : AppCompatActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deposit_form)

        // Initialize the permission launcher
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                handleSubmit()
            } else {
                // Handle permission denial
            }
        }

        val submitButton: Button = findViewById(R.id.depositSubmitButton)
        val cancelButton: Button = findViewById(R.id.depositCancelbutton)

        submitButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    handleSubmit()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                handleSubmit()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun handleSubmit() {
        val depositName = findViewById<EditText>(R.id.depositNameEditText).text.toString()
        val depositAmount = findViewById<EditText>(R.id.depositAmountTextNumber).text.toString()

        val resultIntent = Intent()
        resultIntent.putExtra("transactionName", depositName)
        resultIntent.putExtra("transactionAmount", depositAmount)
        setResult(RESULT_OK, resultIntent)

        // Send notification
        val notificationHelper = NotificationHelper(this)
        notificationHelper.sendDepositNotification(depositAmount)

        finish()
    }
}
