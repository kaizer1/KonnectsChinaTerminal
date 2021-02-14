package com.sergey.los.freelanceideas.konnects

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback  {


      // ChinaTerm chinaTerminal;
    lateinit var chinaTerminal : ChinaTerm
    lateinit var chinaDisplay: ChinaDisplay
    lateinit var texSee : TextView
    lateinit var ButSee : Button
    var mHasPermission : Boolean = false

    val GOANOTHER = 1
    val REPRINT = 2
    val RETURN = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texSee =  findViewById<TextView>(R.id.texttosee)
        ButSee = findViewById<Button>(R.id.buttonconnect)

        // Check if we have the permission to access the terminal storage
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            mHasPermission = false
            // We don't have permission so prompt the user
            requestPermission()
        } else {
            mHasPermission = true
        }

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
        setContentView(R.layout.activity_main)

        if (isAllImageExisted()) {
            setImage()

        }



        texSee =  findViewById<TextView>(R.id.texttosee)
        ButSee = findViewById<Button>(R.id.buttonconnect)

          val intGo = Intent(this@MainActivity, CancelResult::class.java)

         ButSee.setOnClickListener(View.OnClickListener {

             startActivity(intGo)

         })


        println(" start Resume's ")
        chinaDisplay = ChinaDisplay(packageName, this)
        chinaTerminal.InitializeLos(application)
        println(" china terminal's ")


        chinaTerminal.setIPaymentApiInitializationListener(object : IPaymentApiInitializationLosListener {

            override fun onApiConnected() {

                Log.d("df", " my calling to onApiLos ok 1")
                chinaDisplay.initializeCustomerDisplay(chinaTerminal.getiPaymentDeviceManager()!!)
                Log.d("df", " my calling to onApiLos ok 2")
            }

            override fun onApiDisconnected() {
                Log.d("df", " my calling End Api (failed)")
                // TODO("Not yet implemented")
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

          val Inte = Intent(this@MainActivity, CancelResult::class.java)
           Inte.putExtra("ResultCode", resultCode)
         Inte.putExtra("myResult", "Result from activitys")

          startActivity(Inte)

    }


    override fun onDestroy() {
        super.onDestroy()

        // terminateThis
        chinaTerminal .terminateThis(this)
    }




    private fun isAllImageExisted(): Boolean {
        var existShortageFile = false



        val assetManager = resources.assets
        var filelist: Array<String>? = null
        try {
            filelist = assetManager.list("imageToChina")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val terminalDir = File(getExternalStoragePath())
        val files = terminalDir.listFiles()!!

        val sdcardFileName = arrayOfNulls<String>(files.size + 1)
        for (i in files.indices) {
            val file = files[i]
            sdcardFileName[i] = file.name
        }
        assert(filelist != null)
        // was arrayOfNulls<String>(filelist!!.size)
        mShortageFileList = arrayOfNulls<String>(filelist!!.size)
        var j = 0
        for (i in filelist.indices) {
            if (Arrays.asList(*sdcardFileName).contains(filelist[i])) {
                Log.d("sdf", "A file needed already exists in the terminal.")
            } else {
                existShortageFile = true
                //mShortageFileList.get(j) = filelist[i]
                mShortageFileList[j] = filelist[i]
                Log.d("dsf", "mShortageFileList[" + j + "] =" + mShortageFileList.get(j))
                mCount++
                j++
            }
        }
        Log.d("sdf", "isFileName =$existShortageFile")
        return existShortageFile
    }


    //Push shortage images from assets folder to "/sdcard/" directory
    fun setImage() {
        for (i in 0 until mCount) {
            try {
                  Log.d("df", " set image a01 $i")
                val externalFile = File(getExternalStoragePath(), mShortageFileList.get(i))
                val inputShortageFiles = resources.assets.open("imageToChina/" + mShortageFileList.get(i))
                val outputShortageFiles = FileOutputStream(externalFile)
                val DEFAULT_BUFFER_SIZE = 10240 * 4
                val buf = ByteArray(DEFAULT_BUFFER_SIZE)
                var length: Int
                while (inputShortageFiles.read(buf).also { length = it } != -1) {
                    outputShortageFiles.write(buf, 0, length)
                }
                outputShortageFiles.flush()
                outputShortageFiles.close()
                inputShortageFiles.close()
            } catch (e: IOException) {
                Log.d(" df", " SetImage los - not image copy ")
                e.printStackTrace()
            }
        }
    }

    //Request the external storage permission
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                MainActivity.PERMISSIONS_STORAGE,
                MainActivity.REQUEST_EXTERNAL_STORAGE
        )
    }

    //Get the result of requesting permission by callback
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == MainActivity.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("sdf", "We get the external storage permission")
                // We have permission
                mHasPermission = true
            } else {
                Log.d("df", "We wasn't able to get the external storage permission")
                // We don't have permission so prompt the user again
                mHasPermission = false
                requestPermission()
            }
        }
    }




    companion object {
        private var mCount = 0
        private const val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private lateinit var mShortageFileList: Array<String?>


        fun getExternalStoragePath(): String? {
            return Environment.getExternalStorageDirectory().absolutePath
        }
    }
}