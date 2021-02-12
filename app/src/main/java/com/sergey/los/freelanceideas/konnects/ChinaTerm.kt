package com.sergey.los.freelanceideas.konnects

import android.content.Context
import android.os.Handler
import android.os.RemoteException
import android.util.Log
import com.panasonic.smartpayment.android.api.*


class ChinaTerm {


    lateinit var mPaymentApi : PaymentApi
    private var mIPaymentApi: IPaymentApi? = null
    private var mListener: IPaymentApiInitializationLosListener? = null
    private var iPaymentDeviceManager : IPaymentDeviceManager? = null
    var callHandle : Handler = Handler()


    fun setIPaymentApiInitializationListener(listener: IPaymentApiInitializationLosListener) {
        Log.d("llsd", "[in] los0 setIPaymentApiInitializationListener()")
        this.mListener = listener
        Log.d(" dfw", "[out] los1 setIPaymentApiInitializationListener()")
    }

    private val mPaymentApiListener: IPaymentApiListener = object : IPaymentApiListener.Stub() {
        // PaymentApi is connected
        @Throws(RemoteException::class)
        override fun onApiConnected() {
            Log.d(TAG, "[in] onApiConnected()")
            callHandle.post(Runnable {

                try {
                    mIPaymentApi = mPaymentApi.paymentApi

                    iPaymentDeviceManager = mIPaymentApi!!.paymentDeviceManager

                    mListener?.onApiConnected()
                } catch (e: TransactionException) {
                    e.printStackTrace()
                } catch (e: FatalException) {
                    e.printStackTrace()
                }
            })

            Log.d(TAG, "[out] onApiConnected()")
        }

        @Throws(RemoteException::class)
        override fun onApiDisconnected() {
            Log.d(TAG, "PaymentApi is disconnected")
        }
    }


    // -> IPaymentDeviceManager
     fun getiPaymentDeviceManager(): IPaymentDeviceManager? {

         return iPaymentDeviceManager;
     }

     fun InitializeLos(Cont: Context) {
        try {
             println(" my display Custom Los ");

          //  final IPaymentDeviceManager iPaymentDeviceManager = mPaymentApiConnection.getIPaymentDeviceManager();

            mPaymentApi = PaymentApi()
            println(" my display Custom Los 9930");
            mPaymentApi.init(Cont, mPaymentApiListener)

            println(" my version sdk == " + mPaymentApi.sdkVersion)

        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

    fun terminateThis(Cont: Context) {
        try {

            mPaymentApi.term(Cont)

//                    ..  DisplayLosCustom!!.stopDetecting(TAG)
//            DisplayLoCustom!!.closeCustomerDisplay(TAG, false)
//            DisplayLosCustom!!.unregisterCustomerDisplayListeners(TAG)
        } catch (e: ArgumentException) {
            e.printStackTrace()
        } catch (e: FatalException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "com.sergey.los.freelanceideas.konnects"
//        private const val FILE_PATH_DISPLAY_IMAGE = "/sdcard/buttonsall.jpg"
//        private const val FILE_PATH_BUTTON_YES_IMAGE = "/sdcard/housebut.jpg"
//        private const val FILE_PATH_BUTTON_CANCEL_IMAGE = "/sdcard/wallpaperdog.jpg"
    }

    init {
        println(" loading ChineTerm")
    }
}