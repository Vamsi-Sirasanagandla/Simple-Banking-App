package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var currentBalance: TextView
    private lateinit var transactionTextView: TextView
    private lateinit var amountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentBalance = findViewById(R.id.balanceTextView)
        transactionTextView = findViewById(R.id.transactionTextView)
        amountTextView = findViewById(R.id.amountTextView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.depositItem -> Intent(this, DepositActivity::class.java)
            R.id.withdrawItem -> Intent(this, WithdrawalActivity::class.java)
            else -> return super.onOptionsItemSelected(item)
        }
        startActivityForResult(intent, 1)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val transactionName = data?.getStringExtra("transactionName")
            val transactionAmount = data?.getStringExtra("transactionAmount")?.toDoubleOrNull()
            val isWithdrawal = data?.getBooleanExtra("isWithdrawal", false) ?: false

            transactionTextView.text = transactionName
            val amount1 = transactionAmount?.toString() ?: "0"
            if (isWithdrawal) {
                amountTextView.text = "- " + amount1
            } else {
                amountTextView.text = amount1
            }

            val oldBalance = currentBalance.text.toString().toDoubleOrNull() ?: 0.0
            val newBalance = if (isWithdrawal) {
                oldBalance - (transactionAmount ?: 0.0)
            } else {
                oldBalance + (transactionAmount ?: 0.0)
            }
            currentBalance.text = newBalance.toString()
        }
    }
}
