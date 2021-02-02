package com.sergey.los.freelanceideas.konnects

import android.content.Context
import android.os.Handler
import android.os.RemoteException
import android.util.Log
import com.panasonic.smartpayment.android.api.*


class ChinaTerm {

    var DisplayLosCustom: ICustomerDisplay? = null
    lateinit var mPaymentApi : PaymentApi
    private var mIPaymentApi: IPaymentApi? = null
    lateinit var iPaymentDeviceManager : IPaymentDeviceManager
    var callHandle : Handler = Handler()

    private val mLosCustomDisplay: ICustomerDisplayListener = object : ICustomerDisplayListener.Stub() {
        @Throws(RemoteException::class)
        override fun onDetectButton(i: Int) {
            Log.d(" df", "Buttons press'eds ")
        }

        @Throws(RemoteException::class)
        override fun onOpenComplete(b: Boolean) {
            Log.d(" df", "Opens complete !!! ")
        } //           @Override
        //           public IBinder adisplayStringinder() {
        //                return null;
        //           }
    }


    private val mPaymentApiListener: IPaymentApiListener = object : IPaymentApiListener.Stub() {
        // PaymentApi is connected
        @Throws(RemoteException::class)
        override fun onApiConnected() {
            Log.d(TAG, "[in] onApiConnected()")
            callHandle.post(Runnable {

                //  mCallbackHandler.post(Runnable {
                try {
                    // Get IPaymentApi instance.
                    mIPaymentApi = mPaymentApi.paymentApi

                    // Get PaymentDeviceManager instance.
                    iPaymentDeviceManager = mIPaymentApi!!.getPaymentDeviceManager()

                    // Set IPaymentApiInitializationListener
                    // mListener.onApiConnected()
                } catch (e: TransactionException) {
                    e.printStackTrace()
                } catch (e: FatalException) {
                    e.printStackTrace()
                }
            })


           // })
            Log.d(TAG, "[out] onApiConnected()")
        }

        // PaymentApi isn't connected
        @Throws(RemoteException::class)
        override fun onApiDisconnected() {
            Log.d(TAG, "PaymentApi is disconnected")
        }
    }



     fun InitializeLos(Cont: Context) {
        try {
             println(" my display Custom Los ");

          //  final IPaymentDeviceManager iPaymentDeviceManager = mPaymentApiConnection.getIPaymentDeviceManager();

            mPaymentApi = PaymentApi()
            println(" my display Custom Los 9930");
            mPaymentApi.init(Cont, mPaymentApiListener)

            println(" my version sdk == " + mPaymentApi.sdkVersion)

            DisplayLosCustom = iPaymentDeviceManager.getCustomerDisplay();
            DisplayLosCustom!!.registerCustomerDisplayListeners(TAG, mLosCustomDisplay)
            //  mLosCustomDisplay.reg
            println(" my display Custom Los 01 ");
            DisplayLosCustom!!.openCustomerDisplay(TAG)
            println(" my display Custom Los 03");

        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

     fun setupLosSam() {
        try {
            DisplayLosCustom!!.setCustomerImage(TAG, ICustomerDisplay.IMAGE_KIND_DISPLAY, 25, FILE_PATH_DISPLAY_IMAGE)
            DisplayLosCustom!!.setCustomerImage(TAG, ICustomerDisplay.IMAGE_KIND_BUTTON, 10, FILE_PATH_BUTTON_YES_IMAGE)
            DisplayLosCustom!!.setCustomerImage(TAG, ICustomerDisplay.IMAGE_KIND_BUTTON, 11, FILE_PATH_BUTTON_CANCEL_IMAGE)
        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

    fun displayBuildAndShow(): Boolean {
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
            DisplayLosCustom!!.doDisplayScreen(TAG, displayString.toString())
            true
        } catch (e: ArgumentException) {
            e.printStackTrace()
            false
        } catch (e: FatalException) {
            e.printStackTrace()
            false
        }
    }

    fun terminateThis(Cont: Context) {
        try {

            mPaymentApi.term(Cont)

            DisplayLosCustom!!.stopDetecting(TAG)
            DisplayLosCustom!!.closeCustomerDisplay(TAG, false)
            DisplayLosCustom!!.unregisterCustomerDisplayListeners(TAG)
        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "com.sergey.los.freelanceideas.konnects"
        private const val FILE_PATH_DISPLAY_IMAGE = "/sdcard/buttonsall.jpg"
        private const val FILE_PATH_BUTTON_YES_IMAGE = "/sdcard/housebut.jpg"
        private const val FILE_PATH_BUTTON_CANCEL_IMAGE = "/sdcard/wallpaperdog.jpg"
    }

    init {
        println(" loading ChineTerm")
       // InitializeLos()
       // setupLosSam()
    }
}