package com.sergey.los.freelanceideas.konnects

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


      // ChinaTerm chinaTerminal;
    lateinit  var chinaTerminal : ChinaTerm
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
        chinaTerminal.setupLosSam()

         chinaTerminal.displayBuildAndShow()

    }


    override fun onDestroy() {
        super.onDestroy()

        // terminateThis
        chinaTerminal .terminateThis(this)

    }


}