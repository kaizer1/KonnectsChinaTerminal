package com.sergey.los.freelanceideas.konnects

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.panasonic.smartpayment.android.api.IPaymentDeviceManager
import java.util.*

class MainActivity : AppCompatActivity() {


      // ChinaTerm chinaTerminal;
    lateinit var chinaTerminal : ChinaTerm
    lateinit var texSee : TextView
    lateinit var ButSee : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texSee =  findViewById<TextView>(R.id.texttosee)
        ButSee = findViewById<Button>(R.id.buttonconnect)
         // buttonconnect
         println(" china terminal's ")

        chinaTerminal = ChinaTerm()

         ButSee.setOnClickListener(View.OnClickListener {

             println(" pressed buttons ! ")


         })

         // = ChinaTerm()

//        if(chinaTerminal.displayBuildAndShow()){
//
//        }else{
//
//        }

         //   terminateThis
    }


    override fun onResume() {
        super.onResume()

        println(" start Resume's ")
        chinaTerminal.InitializeLos(application)
        println(" china terminal's ")


//        // check if PaymentApi is connected
//        mPaymentApiConnection.setIPaymentApiInitializationListener(object : IPaymentApiInitializationListener() {
//            // PaymentApi is connected
//            fun onApiConnected() {
//                Log.d(MainActivity.TAG, "[in] onApiConnected")
//                mCallbackHandler.post(Runnable {
//                    mUsingCustomerDisplay = true
//                    // Get PaymentDeviceManager instance
//                    val iPaymentDeviceManager: IPaymentDeviceManager = mPaymentApiConnection.getIPaymentDeviceManager()
//
//                    // Show WELCOME image on CustomerDisplay
//                    mOmusubiCustomerDisplay.initializeCustomerDisplay(iPaymentDeviceManager)
//                }.also { mRunnable = it })
//                Log.d(MainActivity.TAG, "[out] onApiConnected")
//            }
//
//            // PaymentApi isn't connected
//            fun onApiDisconnected() {
//                Log.d(MainActivity.TAG, "PaymentApi is disconnected")
//            }
//        })



        chinaTerminal.setIPaymentApiInitializationListener(object : IPaymentApiInitializationLosListener {

            override fun onApiConnected() {

                 Log.d("df", " my calling to onApiLos ok 1")
                chinaTerminal.ListenersCall()
                Log.d("df", " my calling to onApiLos ok 2")
            }

            override fun onApiDisconnected() {
                Log.d("df", " my calling EndApi (failed)")
               // TODO("Not yet implemented")
            }
        })

        //chinaTerminal.setupLosSam()
        //chinaTerminal.displayBuildAndShow()

    }


    override fun onDestroy() {
        super.onDestroy()

        // terminateThis
        chinaTerminal .terminateThis(this)

    }


}