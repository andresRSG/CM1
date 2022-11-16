package com.example.ejerciciounoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ejerciciounoapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.realpacific.clickshrinkeffect.applyClickShrink
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.widget.DatePicker
import com.vdx.designertoast.DesignerToast
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



class MainActivity : AppCompatActivity() {

    var mBDate = ""

    private lateinit var binding: ActivityMainBinding

    public var isFragmentShow = false
    lateinit var dpd : DatePickerDialog

    val PERMISSION_CAMERA_USER = android.Manifest.permission.CAMERA
    private val requestPermissionCamera = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing datePicker
        val cal = Calendar.getInstance()
        dpd = DatePickerDialog(this@MainActivity,
            { _: DatePicker?, _: Int, _: Int, _: Int -> }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)
        )


        //listeners events
        listeners()
    }

    @Override
    override fun onResume(){
        super.onResume()
        binding.mprogress.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onBackPressed() {
        if(isFragmentShow){
            supportFragmentManager.findFragmentById(R.id.frameQR)?.let {
                supportFragmentManager.beginTransaction().remove(it).commit()
            }
            isFragmentShow = false
        }else{
            super.onBackPressed()
        }
    }




    private fun listeners() {
        binding.btnVerify.applyClickShrink()
        binding.tvDate.setOnClickListener{
            if(!dpd.isShowing) showDatePicker() }

        binding.btnVerify.setOnClickListener{
            if(verifyData()){
                hideKeyboard(this)
                binding.mprogress.visibility = View.VISIBLE
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                val parmetros= Bundle()
                parmetros.putString("date",mBDate)
                parmetros.putString("numCount",binding.editNumCount.text.toString())
                parmetros.putString("email", binding.editEmail.text.toString())
                parmetros.putString("name",binding.editName.text.toString())


                val intent = Intent(this, InfoUserActivity::class.java).apply {
                    putExtras(parmetros)
                }
                object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
                        binding.mprogress.visibility = View.GONE
                    }
                }.start()

            }
        }

        binding.imageQr.setOnClickListener{
            getPictureFromCameraAskingPermissions()
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

        if(binding.editName.text.isEmpty()){
            binding.editName.error = getString(R.string.empty)
            binding.editName.requestFocus()
            DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }

        if(binding.tvDate.text.isEmpty()){
            binding.tvDate.requestFocus()
            DesignerToast.Warning(this, getString(R.string.info), getString(R.string.completa_el_campo),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }else if(!dateIsCorrect()){
            binding.tvDate.requestFocus()
            DesignerToast.Error(this,getString(R.string.info), getString(R.string.invalid_date),
                Gravity.TOP, LENGTH_LONG,null)
            return false
        }

       if(binding.editNumCount.text.isNotEmpty()){
           if(binding.editNumCount.text.length < 9){
               binding.editNumCount.error = getString(R.string.empty)
               binding.editNumCount.requestFocus()
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
        return if (binding.editEmail.text.isNotEmpty()) {
            val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
            if (!binding.editEmail.getText().toString().matches(regExpn.toRegex())) {
                if (binding.editEmail.getText().toString().contains(".mx")) {
                    true
                } else {
                    //toast
                    DesignerToast.Error(this, getString(R.string.info), getString(R.string.invalid_email),
                        Gravity.TOP, LENGTH_LONG,null)
                    binding.editEmail.requestFocus()
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
        dpd.show()

        dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvDate.text =
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

    private fun getPictureFromCameraAskingPermissions(){
        // Add permission to manifest
        //Check camera permission
        if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA_USER) != PackageManager.PERMISSION_GRANTED){
            //If not previously accepted
            ActivityCompat.requestPermissions(this, arrayOf(PERMISSION_CAMERA_USER), requestPermissionCamera)
        }else{
            //If previously accepted
            //show QR
            showFragment()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            requestPermissionCamera ->{
                if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permit accepted
                    //show QR
                    showFragment()
                }else{
                    //Permit denied
                    //onBackPressed
                    Toast.makeText(this, "No puedes tomar una foto si no aceptas los permisos ", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showFragment(){
        val scannerQRFragment = ScannerQRFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val transaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameQR, scannerQRFragment).commit()
        isFragmentShow = true
    }

    fun fillData(text:String){
        isFragmentShow = false

        val gson = Gson()
        val personInfo: PersonInfo = gson.fromJson(text, PersonInfo::class.java)
        supportFragmentManager.findFragmentById(R.id.frameQR)?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        };

        binding.editName.setText(personInfo.name)
        binding.tvDate.text = personInfo.birthday
        binding.editNumCount.setText(personInfo.noCount)
        binding.editEmail.setText(personInfo.email)
        mBDate = personInfo.birthday

    }

}