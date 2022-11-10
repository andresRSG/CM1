package com.example.ejerciciounoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.realpacific.clickshrinkeffect.applyClickShrink
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.widget.DatePicker
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_info_user.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tvDate
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var mBDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listeners()
    }

    private fun listeners() {
        btnVerify.applyClickShrink()
        tvDate.setOnClickListener{ showDatePicker() }

        btnVerify.setOnClickListener{
            if(verifyData()){
                hideKeyboard(this)
                mprogress.visibility = View.VISIBLE
                val parmetros= Bundle()
                parmetros.putString("date",mBDate)
                parmetros.putString("numCount",editNumCount.text.toString())
                parmetros.putString("email", editEmail.text.toString())
                parmetros.putString("name",editName.text.toString())

                val intent = Intent(this, InfoUserActivity::class.java).apply {
                    putExtras(parmetros)
                }
                object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        startActivity(intent)
                        mprogress.visibility = View.GONE
                    }
                }.start()

            }
        }
    }


    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun verifyData():Boolean{

        if(editName.text.isEmpty()){
            editName.error = getString(R.string.empty)
            editName.requestFocus()
            DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }

        if(tvDate.text.isEmpty()){
            tvDate.requestFocus()
            DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }else if(!dateIsCorrect()){
            tvDate.requestFocus()
            DesignerToast.Error(this,getString(R.string.info), getString(R.string.invalid_date),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }

       if(editNumCount.text.isNotEmpty()){
           if(editNumCount.text.length < 9){
               editNumCount.error = getString(R.string.empty)
               editNumCount.requestFocus()
               DesignerToast.Error(this,getString(R.string.info), getString(R.string.invalid_digits),
                   Gravity.TOP, LENGTH_LONG,null)
               return false
           }
       }else{
           DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
               Gravity.TOP, LENGTH_LONG,null)
           return false
       }

        if(!validatingEmail()){
            return false
        }

        return true
    }


    private fun validatingEmail(): Boolean {
        return if (editEmail.text.isNotEmpty()) {
            val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
            if (!editEmail.getText().toString().matches(regExpn.toRegex())) {
                if (editEmail.getText().toString().contains(".mx")) {
                    true
                } else {
                    //toast
                    DesignerToast.Error(this, getString(R.string.info), getString(R.string.invalid_email),
                        Gravity.TOP, LENGTH_LONG,null)
                    editEmail.requestFocus()
                    false
                }
            } else {
                true
            }
        } else {
            DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
                Gravity.TOP, LENGTH_LONG,null)
            false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateIsCorrect():Boolean{
        val mDate = stringToDate(mBDate)
        var aDate = Calendar.getInstance().time
        val sADate = dateToString(aDate)
        aDate = stringToDate(sADate)
        return mDate < aDate
    }


    @SuppressLint("DefaultLocale")
    private fun showDatePicker() {

        val cal = Calendar.getInstance()
        val dpd = DatePickerDialog(this@MainActivity,
            { _: DatePicker?, _: Int, _: Int, _: Int -> }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)
        )
        dpd.show()
        dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
            tvDate.text =
                Editable.Factory.getInstance().newEditable("${dayOfMonth}/${month + 1}/${year}")
            mBDate = "${dayOfMonth}/${month + 1}/${year}"
        }
    }

    fun stringToDate(date: String?): Date {
        val format: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        var dateFormat: Date? = null
        try {
            dateFormat = date?.let { format.parse(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateFormat!!
    }

    fun dateToString(date: Date?): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }


}