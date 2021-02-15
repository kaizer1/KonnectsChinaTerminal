package com.sergey.los.freelanceideas.konnects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class CancelOne : Activity() {

     lateinit var buuRe : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setContentView(R.layout.acti_ca_ret)

        buuRe = findViewById(R.id.butreturn)

        var intGet = getIntent()
        var nameback = intGet.getStringExtra("TransString")
        Log.d("df", " my transString == $nameback")




        buuRe.setOnClickListener(View.OnClickListener {

            var inten: Intent
            var RequestCode: Int

            RequestCode = 3
            var returnIntent: ReturnIntent = ReturnIntent()
            inten = returnIntent.createReturnIntet(totalString = "10")

            startActivityForResult(inten, RequestCode)

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        Log.d("df", " in ResultActivity for CancelOnt \n")



        var intentRes = Intent(this@CancelOne, CancelResult::class.java)

          if( resultCode == 3){
               Log.d("df", " start MainACtivity from One activity\n")
              intentRes.putExtra("myResult", "From_ActivityMain")
              intentRes.putExtra("ResultCode", 0)

          }
             intentRes.putExtra("ResultCode", resultCode)

                startActivity(intentRes)
                finishAndRemoveTask()
    }

}