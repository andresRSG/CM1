package com.example.ejerciciounoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*
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
        tvDate.setOnClickListener{ showDatePicker() }
        btnVerify.setOnClickListener{
            if(verifyData()){
                val parmetros= Bundle()
                parmetros.putString("date",mBDate)
                parmetros.putString("numCount",editNumCount.text.toString())
                parmetros.putString("email", editEmail.text.toString())
                parmetros.putString("name",editName.text.toString())

                val intent = Intent(this, InfoUserActivity::class.java).apply {
                    putExtras(parmetros)
                }
                startActivity(intent)
            }
        }
    }

    private fun verifyData():Boolean{
        if(editName.text.isEmpty()){
            editName.error = getString(R.string.completa_el_campo)
            editName.requestFocus()
            return false
        }

        if(tvDate.text.isEmpty()){
            tvDate.requestFocus()
            Toast.makeText(this, getString(R.string.completa_el_campo), Toast.LENGTH_LONG).show()
            return false
        }else if(!dateIsCorrect()){
            tvDate.requestFocus()
            Toast.makeText(this, getString(R.string.invalid_date), Toast.LENGTH_LONG).show()
            return false
        }

        if(editNumCount.text.length < 9){
            editNumCount.error = getString(R.string.invalid_digits)
            Toast.makeText(this, getString(R.string.invalid_digits), Toast.LENGTH_LONG).show()
            editNumCount.requestFocus()
            return false
        }

//        if(validatingEmail()){
//            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
//            editEmail.error = ""
//            editEmail.requestFocus()
//            return false
//        }

        return true
    }

    private fun validatingEmail(): Boolean {
        return if (editEmail.text.isNotEmpty()){
            val reg_Expression =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            editEmail.text.matches(regex = reg_Expression.toRegex())
        }else{
            editEmail.error = getString(R.string.completa_el_campo)
            editEmail.requestFocus()
            false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateIsCorrect():Boolean{
        val mDate = stringToDate(mBDate)
        var aDate = Calendar.getInstance().time
        val sADate = dateToString(aDate)
        aDate = stringToDate(sADate)
        if(mDate < aDate){
            Toast.makeText(this, "Valido", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Invalido", Toast.LENGTH_SHORT).show()
        }
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