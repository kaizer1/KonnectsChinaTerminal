package com.sergey.los.freelanceideas.konnects

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.panasonic.smartpayment.android.api.*


class ChinaDisplay {

    var contex : Context? = null
    lateinit var namePackea : String
    private var mListener: ChinaDisplayListener? = null
    var mCallbackHandler : Handler = Handler()
   lateinit var mICustomerDisplay : ICustomerDisplay

    constructor(packageName: String, cont: Context){
       contex = cont
       namePackea = packageName

    }

    fun setChinaTerminalListeners(listener: ChinaDisplayListener) {
       print(" ok listener China Display \n")
        this.mListener = listener

    }



    private val mICustomerDisplayChinaListener: ICustomerDisplayListener = object : ICustomerDisplayListener.Stub() {


//        val runBut = object : Runnable {
//            override fun run() {
//                Log.d(" calling's button detect's", "My call");
//            }
//        }



        override fun onOpenComplete(result: Boolean) {
            Log.d("sdf", "[in] onOpenComplete()")
            mCallbackHandler.post(Runnable {
                if (result) {
                    println(" my result openComple $result")
                    setupLosSam()
                    try {

                        displayBuildAndShow()

                    } catch (e: ArgumentException) {
                        e.printStackTrace()
                    } catch (e: FatalException) {
                        e.printStackTrace()
                    }
                } else {
                    println("OmusubiCustomerDisplay was not open")

                }
            })
            println("[out] onOpenComplete() ")
        }

        override fun onDetectButton(button: Int) {
          println("[in] onDetectButton()")
            mCallbackHandler.post(Runnable {
                mListener!!.onDetectButton(button)
            })
            println("[out] onDetectButton()")
        }
    }


    fun initializeCustomerDisplay(mIPaymentDeviceManager: IPaymentDeviceManager) {
        try {
            mICustomerDisplay = mIPaymentDeviceManager.customerDisplay
            mICustomerDisplay.registerCustomerDisplayListeners(namePackea, mICustomerDisplayChinaListener)
            mICustomerDisplay.openCustomerDisplay(namePackea)

        } catch (eArgument: ArgumentException) {
            eArgument.printStackTrace()
        } catch (eFatal: FatalException) {
            eFatal.printStackTrace()
            if (eFatal.additionalInformation == "113") {
                val dialog = AlertDialog.Builder(contex!!)
                dialog.setMessage(contex!!.getString(R.string.errorShutDown))
                        .setPositiveButton("OK", null)
                        .show()
            } else {
                println("Caught an exception except additional information \"113\"")
            }
        }
        println("[out] initializeCustomerDisplay()")
    }

    fun showDisplay(){
        setupLosSam()
        displayBuildAndShow()
    }

    private fun setupLosSam() {
        try {
            mICustomerDisplay.setCustomerImage(ChinaDisplay.TAG, ICustomerDisplay.IMAGE_KIND_DISPLAY, 25, ChinaDisplay.FILE_PATH_DISPLAY_IMAGE)
            mICustomerDisplay.setCustomerImage(ChinaDisplay.TAG, ICustomerDisplay.IMAGE_KIND_BUTTON, 10, ChinaDisplay.FILE_PATH_BUTTON_YES_IMAGE)
            mICustomerDisplay.setCustomerImage(ChinaDisplay.TAG, ICustomerDisplay.IMAGE_KIND_BUTTON, 11, ChinaDisplay.FILE_PATH_BUTTON_CANCEL_IMAGE)
        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

    private fun displayBuildAndShow(): Boolean {
        val displayString = StringBuilder()
        displayString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        displayString.append("    <customerDisplayApi id=\"xxxxxxxxx\">")
        displayString.append("    <screenPattern>3</screenPattern>")
        displayString.append("    <headerArea>")
        displayString.append("        <headerAreaNumber>1</headerAreaNumber>")
        displayString.append("        <customerString>Revolving</customerString>")
        displayString.append("    </headerArea>")
        displayString.append("    <headerArea>")
        displayString.append("        <headerAreaNumber>2</headerAreaNumber>")
        displayString.append("        <customerString>Credit</customerString>")
        displayString.append("    </headerArea>")
        displayString.append("    <headerArea>")
        displayString.append("        <headerAreaNumber>3</headerAreaNumber>")
        displayString.append("        <customerString>$123.45</customerString>")
        displayString.append("    </headerArea>")
        displayString.append("    <imageArea>")
        displayString.append("        <imageAreaNumber>1</imageAreaNumber>")
        displayString.append("        <imageNumber>25</imageNumber>") // ID=25の画像を指定
        displayString.append("    </imageArea>")
        displayString.append("    <messageArea>")
        displayString.append("        <messageAreaNumber>1</messageAreaNumber>")
        displayString.append("        <customerString>PrEss and pay program mon</customerString>")
        displayString.append("    </messageArea>")
        displayString.append("    <buttonTop>")
        displayString.append("        <buttonNumber>1</buttonNumber>")
        displayString.append("        <imageNumber>10</imageNumber>") // ID=10のボタンを指定
        displayString.append("    </buttonTop>")
        displayString.append("    <buttonTop>")
        displayString.append("        <buttonNumber>2</buttonNumber>")
        displayString.append("        <imageNumber>11</imageNumber>") // ID=11のボタンを指定
        displayString.append("    </buttonTop>")
        displayString.append("</customerDisplayApi>")
        return try {
            mICustomerDisplay!!.doDisplayScreen(ChinaDisplay.TAG, displayString.toString())
            true
        } catch (e: ArgumentException) {
            e.printStackTrace()
            false
        } catch (e: FatalException) {
            e.printStackTrace()
            false
        }
    }

    fun terminateChinaDisplay(boolean: Boolean){
          Log.d("df", " China Display terminate ")
        try {
            mICustomerDisplay.closeCustomerDisplay(namePackea, boolean)
            mICustomerDisplay.unregisterCustomerDisplayListeners(namePackea)
        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }


    companion object {
        private const val TAG = "com.sergey.los.freelanceideas.konnects"
        private val FILE_PATH_DISPLAY_IMAGE : String = MainActivity.getExternalStoragePath().toString() +  "/buttonsall.jpg" // was .png
        private val FILE_PATH_BUTTON_YES_IMAGE : String = MainActivity.getExternalStoragePath().toString() + "/housebut.jpg"
        private val FILE_PATH_BUTTON_CANCEL_IMAGE : String = MainActivity.getExternalStoragePath().toString() + "/wallpaperdog.jpg"
        //private val FILE_PATH_BUTTON_YES_IMAGE2: String = MainActivity.getExternalStoragePath().toString() + "/YES.jpg"
    }
}