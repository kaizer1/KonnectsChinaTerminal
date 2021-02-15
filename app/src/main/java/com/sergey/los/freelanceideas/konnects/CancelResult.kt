package com.sergey.los.freelanceideas.konnects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import com.panasonic.smartpayment.android.api.IPaymentDeviceManager
import com.panasonic.smartpayment.android.api.Result

class CancelResult : Activity() {

     var chinaT : ChinaTerm = ChinaTerm()
    var callHandleBack : Handler = Handler()
    lateinit var IdeviceManage : IPaymentDeviceManager
    lateinit var butBack : Button
    var mCallbackHandler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setContentView(R.layout.acti_result)

         butBack = findViewById(R.id.but01)
        chinaT.InitializeLos(this)

      chinaT.setIPaymentApiInitializationListener(object : IPaymentApiInitializationLosListener {
          override fun onApiConnected() {
              callHandleBack.post(Runnable {
                  Log.d("df", " my calling to onApiLos ok in Result One")
                  //  chinaTerminal.ListenersCall()
                  IdeviceManage = chinaT.getiPaymentDeviceManager()!!
                  doResultActivity()
                  Log.d("df", " my calling to onApiLos ok in Result  Two")

              })
          }

          override fun onApiDisconnected() {
              Log.d("df", "Payment disconnect \n")
          }
          //}

      })
    }


    fun doResultActivity(){

           Log.d(" er", " Result do 01 ")

         // setupDisplay
        val chinaD1 = ChinaDisplay(packageName, this@CancelResult)

          val inte = intent
          val resultCode = inte.getIntExtra("ResultCode", 0)
                 println(" gettings = my Result Codes == $resultCode")
        if(resultCode == 0){

              println(" calling my activity  and result code == 0 \n")
           chinaD1.initializeCustomerDisplay(IdeviceManage)

            chinaD1.setChinaTerminalListeners(object : ChinaDisplayListener {
                // Check if CustomerDisplay is connected
                override fun onOpenChinaDisplay(boolean: Boolean) {
                    if (boolean) {
                        println ("OmusubiCustomerDisplayCoupon was open. result=$boolean")
                    } else {
                        println("OmusubiCustomerDisplayCoupon wasn't open. result=$boolean")
                    }
                }

                // Get kinds of touched button on CustomerDisplay
               override fun onDetectButton(button: Int) {
                    println("[in] onDetectButton")
                    println( "button=$button")
                    mCallbackHandler.post(Runnable {
                        if (button == 1) {
                          //  mPrintCoupon = true

                            chinaD1.showDisplay()
                          //  omusubiCustomerDisplayCoupon.showDisplay(OmusubiCustomerDisplay.THANKYOU)

                           // val omusubiFreeCoupon = OmusubiFreeCoupon(this@ResultActivity)
                           // omusubiFreeCoupon.printCoupon(mIPaymentDeviceManager)
//                            omusubiFreeCoupon.setIOmusubiFreeCouponListener(object : IOmusubiFreeCouponListener() {
//                                fun onPrintReceipt(result: Result) {
//                                    Log.d(com.panasonic.sample.posapp.omusubi.posapp_sample.ResultActivity.TAG, "[in] onPrintReceipt")
//                                    mCallbackHandler.post(Runnable {
//                                        if (result.resultCode == PrinterErrorActivity.SUCCESS) {
//                                            btnGoToTop.setEnabled(true)
//                                            Log.d(com.panasonic.sample.posapp.omusubi.posapp_sample.ResultActivity.TAG, "Printing the coupon was success")
//                                        } else {
//                                            // Do ResultActivity to deal with printing error
//                                            val intentPrinterError = Intent(this@ResultActivity,
//                                                    PrinterErrorActivity::class.java)
//                                            intentPrinterError.putExtra("ErrorResultCode", String.format("0x%08X", result.resultCode))
//                                            intentPrinterError.putExtra("ErrorMessage", result.message)
//                                            intentPrinterError.putExtra("ErrorAdditionalInformation", result.additionalInformation)
//                                            startActivity(intentPrinterError)
//                                        }
//                                    })
//                                    Log.d(com.panasonic.sample.posapp.omusubi.posapp_sample.ResultActivity.TAG, "[out] onPrintReceipt")
//                                }
//                            })

                            chinaD1.terminateChinaDisplay(false)
                        } else {
                           // mPrintCoupon = false
                            // Show image of thank you on CustomerDisplay
                            // omusubiCustomerDisplayCoupon.showDisplay(OmusubiCustomerDisplay.THANKYOU)
                            chinaD1.showDisplay()
                           // btnGoToTop.setEnabled(true)
                            println( "Detected button was$button")
                        }
                    })
                    println("[out] onDetectButton")
                }
            })
          }

        if (resultCode == 3){
            println(" calling my activity  and result code == 3 \n")
            chinaD1.initializeCustomerDisplay(IdeviceManage)

            chinaD1.setChinaTerminalListeners(object : ChinaDisplayListener {
                // Check if CustomerDisplay is connected
                override fun onOpenChinaDisplay(boolean: Boolean) {
                    if (boolean) {
                        println("OmusubiCustomerDisplayCoupon was open. 222result=$boolean")
                        chinaD1.showDisplay()
                    } else {
                        println("OmusubiCustomerDisplayCoupon wasn't open222333. result=$boolean")
                    }
                }

                override fun onDetectButton(button: Int){

                }
            });

            }


        butBack.setOnClickListener(View.OnClickListener {
            onButtonClick()

            // terminateDisplay
             chinaD1.terminateChinaDisplay(true)

        })


        chinaT.terminateThis(this)
    }




    private fun onButtonClick() {
        // Show MainActivity
        val intent = Intent(this@CancelResult, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Do nothing when back button is touched.
    }

    override fun onPause() {
        super.onPause()
        // Finish this Activity
        finishAndRemoveTask()
    }
}