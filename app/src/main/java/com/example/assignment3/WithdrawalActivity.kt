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

class WithdrawalActivity : AppCompatActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.withdraw_form)

        // Initialize the permission launcher
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                handleSubmit()
            } else {
                // Handle permission denial
            }
        }

        val submitButton: Button = findViewById(R.id.expenseSubmitButton)
        val cancelButton: Button = findViewById(R.id.expenseCancelbutton)

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
        val expenseName = findViewById<EditText>(R.id.expenseNameEditText).text.toString()
        val expenseAmount = findViewById<EditText>(R.id.expenseAmountTextNumber).text.toString()

        val resultIntent = Intent()
        resultIntent.putExtra("transactionName", expenseName)
        resultIntent.putExtra("transactionAmount", expenseAmount)
        resultIntent.putExtra("isWithdrawal", true)
        setResult(RESULT_OK, resultIntent)

        // Send notification
        val notificationHelper = NotificationHelper(this)
        notificationHelper.sendWithdrawNotification(expenseAmount)

        finish()
    }
}
