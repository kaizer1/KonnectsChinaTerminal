package com.sergey.los.freelanceideas.konnects

import android.content.Intent

class ReturnIntent {

     fun createReturnIntet(totalString : String) : Intent {
         val Inty : Intent = Intent()
          Inty.setClassName("com.panasonic.smartpayment.android.salesmenu", "com.panasonic.smartpayment.android.salesmenu.MainActivity")
         Inty.putExtra("AmountUs", totalString)
         return Inty
     }
}