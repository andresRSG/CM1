package com.example.ejerciciounoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ejerciciounoapp.databinding.ActivityInfoUserBinding
import com.vdx.designertoast.DesignerToast
import java.util.*

class InfoUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


    // getting the bundle back from the android
        val bundle = intent.extras

    // performing the safety null check
        var name:String? = null
        var email:String? = null
        var numCount:String? = null
        var date:String? = null

    // getting the string back
        date = bundle!!.getString("date", "11/10/2022")
        numCount = bundle.getString("numCount", "315245923")
        email = bundle.getString("email", "a@gmail.com")
        name = bundle.getString("name", "Guillermo Andres")

        binding.tvName.text = name
        binding.tvDate.text = date
        binding.tvNumCount.text = numCount
        binding.tvEmail.text = email
        //Averigua si es año bisiesto
        val calendar = GregorianCalendar()
        val mDate = date.split("/")
        calendar.set(mDate[0].toInt(), mDate[1].toInt(), mDate[2].toInt())
        val isLeap = calendar.isLeapYear(mDate[2].toInt())

        //Fecha actual
        val dateY = Date()
        calendar.time = dateY
        val dateYear = calendar[Calendar.YEAR]

        //Fecha que se ingreso
        val c = Calendar.getInstance()
        c.set(mDate[2].toInt(), mDate[1].toInt()-1, mDate[0].toInt())
        val dayOfYear = c[Calendar.DAY_OF_YEAR] // Aquí obtengo el dia del año

        //calcula la edad
        val age =  dateYear - mDate[2].toInt()
        if(age > 1 || age ==0) {
            if (calendar[Calendar.DAY_OF_YEAR] - dayOfYear > 0){
                binding.tvAge.text = getString(R.string.age_plu, age-1)
            }else {
                binding.tvAge.text = getString(R.string.age_plu, age)
            }
        }else{
            if(calendar[Calendar.DAY_OF_YEAR] - dayOfYear > 0){
                binding.tvAge.text = getString(R.string.age_single)
            }
        }

        //si es año bisiesto se le resta un numero para entrar dentro de los rangos de los dias
        if(isLeap){
            calculateSigneZodiac(dayOfYear = dayOfYear - 1)//calcula dependiendo el dia del año
        }else{
            calculateSigneZodiac(dayOfYear = dayOfYear)
        }

        calculateSigneChines(mDate[2].toInt())//calcula dependiendo el año
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun calculateSigneChines(year:Int){
        val signes = listOf("Rata", "Buey", "Tigre", "Conejo", "Dragon", "Serpiente", "Caballo","Cabra","Mono", "Gallo", "Perro", "Cerdo")
        var pos = 0
        for(my in 1960..year){
            pos++
            if(pos == 12) pos = 0
        }
        var signe = ""
        try {
            signe = signes.get(pos-1)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            DesignerToast.Info(this, getString(R.string.info), getString(R.string.signe_no_found),
                Gravity.TOP, Toast.LENGTH_LONG,null)
            return
        }

        when(signe){
            "Rata" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.rata))
            }"Buey" ->{
            binding.tvChinese.text = getString(R.string.signe_chino, signe)
            binding.imageChines.setImageDrawable(getDrawable(R.drawable.buey))

            }"Tigre" ->{
            binding.tvChinese.text = getString(R.string.signe_chino, signe)
            binding.imageChines.setImageDrawable(getDrawable(R.drawable.tigre))

            }"Conejo" ->{
            binding.tvChinese.text = getString(R.string.signe_chino, signe)
            binding.imageChines.setImageDrawable(getDrawable(R.drawable.conejo))

            }"Dragon" ->{
            binding.tvChinese.text = getString(R.string.signe_chino, signe)
            binding.imageChines.setImageDrawable(getDrawable(R.drawable.dragon))

            }
            "Serpiente" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.serpiente))

            }
            "Caballo" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.caballo))

            }
            "Cabra" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.cabra))

            }
            "Mono" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.mono))

            }
            "Gallo" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.rata))

            }
            "Perro" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.perro))

            }
            "Cerdo" ->{
                binding.tvChinese.text = getString(R.string.signe_chino, signe)
                binding.imageChines.setImageDrawable(getDrawable(R.drawable.cerdo))

            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun calculateSigneZodiac(dayOfYear:Int){
//        Toast.makeText(this, dayOfYear.toString(), Toast.LENGTH_SHORT).show()
        when (dayOfYear) {
            in 1..19 -> {
                binding.tvSigne.text =getText(R.string.capricornio)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.capricornio))
            }in 21..49 -> {
                binding.tvSigne.text =getText(R.string.acuario)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.acuario))
            } in 50..79 -> {//
                binding.tvSigne.text =getText(R.string.picis)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.picis))

            } in 80..110 -> {
                binding.tvSigne.text =getText(R.string.aries)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.aries))

            } in 111..141 -> {
                binding.tvSigne.text =getText(R.string.tauro)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.tauro))

            } in 142..172 -> {
                binding.tvSigne.text =getText(R.string.geminis)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.geminis))
            } in 173..203 -> {
                binding.tvSigne.text =getText(R.string.cancer)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.cance))
            } in 204..235 -> {
                binding.tvSigne.text =getText(R.string.leo)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.leo))
            } in 236..266 -> {
                binding.tvSigne.text =getText(R.string.virgo)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.virgo))
            }  in 267..296 -> {
                binding.tvSigne.text =getText(R.string.libra)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.libra))
            }  in 297..326 -> {
                binding.tvSigne.text =getText(R.string.escorpion)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.escorpion))
            }  in 327..365 -> {
                binding.tvSigne.text =getText(R.string.capricornio)
                binding.imageZodiac.setImageDrawable(getDrawable(R.drawable.capricornio))
            } else -> {
                    println("😱")
                }
            }
    }
}