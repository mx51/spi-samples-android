package com.assemblypayments.ramenpos.activities.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.assemblypayments.ramenpos.R
import com.assemblypayments.ramenpos.activities.connection.ConnectionActivity
import com.assemblypayments.ramenpos.activities.transactions.TransactionsActivity
import com.assemblypayments.ramenpos.logic.RamenPos
import com.assemblypayments.ramenpos.logic.settings.SettingsProvider
import com.assemblypayments.spi.model.Secrets
import com.assemblypayments.spi.model.SpiStatus
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {
    private val TAG = "MAIN ACTIVITY"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RamenPos.mainActivity = this

        Log.d(TAG, "In onCreate")

        val preferences = getSharedPreferences("SettingsProvider", Context.MODE_PRIVATE)
        RamenPos.settings = SettingsProvider(preferences)

        if (RamenPos.settings!!.hmacKey != "" && RamenPos.settings?.encriptionKey != "") {
            RamenPos.secrets = Secrets(RamenPos.settings!!.encriptionKey, RamenPos.settings!!.hmacKey)
        }

        RamenPos.initialize()

        val mBbtnConnection: Button = this.findViewById(R.id.btnConnection)
        mBbtnConnection.setOnClickListener {
            val intent = Intent(this, ConnectionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivityIfNeeded(intent, 0)
        }

        val mBtnTransactions: Button = this.findViewById(R.id.btnTransactions)
        mBtnTransactions.setOnClickListener {
            val intent = Intent(this, TransactionsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivityIfNeeded(intent, 0)
        }

        btnECRetrieve.setOnClickListener {
            if (RamenPos.spi?.currentStatus == SpiStatus.PAIRED_CONNECTED) {
                RamenPos.spi?.getTerminalConfiguration()
            }
        }

        btnESRetrieve.setOnClickListener {
            if (RamenPos.spi?.currentStatus == SpiStatus.PAIRED_CONNECTED) {
                RamenPos.spi?.getTerminalStatus()
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "In onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "In onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "In onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "In onDestroy")
    }

}
