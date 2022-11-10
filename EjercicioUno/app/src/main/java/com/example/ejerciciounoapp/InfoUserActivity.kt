package com.example.ejerciciounoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info_user.*
import java.time.Year
import java.util.*

class InfoUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_user)


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

        tvName.text = name
        tvDate.text = date
        tvNumCount.text = numCount
        tvEmail.text = email

        val calendar = GregorianCalendar()
        val mDate = date.split("/")
        calendar.set(mDate[0].toInt(), mDate[1].toInt(), mDate[2].toInt())
        val isLeap = calendar.isLeapYear(mDate[2].toInt())

        val dateY = Date()
        calendar.time = dateY
        val dateYear = calendar[Calendar.YEAR]

        val c = Calendar.getInstance()
        c.set(mDate[2].toInt(), mDate[1].toInt(), mDate[0].toInt())
        val dayOfYear = c[Calendar.DAY_OF_YEAR] // Aquí obtengo el dia del año

        val age =  dateYear - mDate[2].toInt()
        if(age > 1 || age ==0) {
            if (calendar[Calendar.DAY_OF_YEAR] - dayOfYear > 0){
                tvAge.text = getString(R.string.age_plu, age-1)
            }else {
                tvAge.text = getString(R.string.age_plu, age)
            }
        }else{
            if(calendar[Calendar.DAY_OF_YEAR] - dayOfYear > 0){
                tvAge.text = getString(R.string.age_single)
            }
        }

        if(isLeap){
            calculateSigneZodiac(dayOfYear = dayOfYear - 1)
        }else{
            calculateSigneZodiac(dayOfYear = dayOfYear)
        }

        calculateSigneChines(mDate[2].toInt())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun calculateSigneChines(year:Int){
        val signes = listOf("Rata", "Buey", "Tigre", "Conejo", "Dragon", "Serpiente", "Caballo","Cabra","Mono", "Gallo", "Perro", "Cerdo")
        var pos = 0
        for(my in 1960..year){
            pos++
            if(pos == 12) pos = 0
        }
        val signe = signes.get(pos-1)

        when(signe){
            "Rata" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.rata))
            }"Buey" ->{
            tvChinese.text = getString(R.string.signe_chino, signe)
            imageChines.setImageDrawable(getDrawable(R.drawable.buey))

            }"Tigre" ->{
            tvChinese.text = getString(R.string.signe_chino, signe)
            imageChines.setImageDrawable(getDrawable(R.drawable.tigre))

            }"Conejo" ->{
            tvChinese.text = getString(R.string.signe_chino, signe)
            imageChines.setImageDrawable(getDrawable(R.drawable.conejo))

            }"Dragon" ->{
            tvChinese.text = getString(R.string.signe_chino, signe)
            imageChines.setImageDrawable(getDrawable(R.drawable.dragon))

            }
            "Serpiente" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.serpiente))

            }
            "Caballo" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.caballo))

            }
            "Cabra" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.cabra))

            }
            "Mono" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.mono))

            }
            "Gallo" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.rata))

            }
            "Perro" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.perro))

            }
            "Cerdo" ->{
                tvChinese.text = getString(R.string.signe_chino, signe)
                imageChines.setImageDrawable(getDrawable(R.drawable.cerdo))

            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun calculateSigneZodiac(dayOfYear:Int){
        when (dayOfYear) {
            in 1..19 -> {
                tvSigne.text =getText(R.string.capricornio)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.capricornio))
            }in 21..49 -> {
                tvSigne.text =getText(R.string.acuario)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.acuario))
            } in 50..79 -> {//
                tvSigne.text =getText(R.string.picis)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.picis))

            } in 80..110 -> {
                tvSigne.text =getText(R.string.aries)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.aries))

            } in 111..141 -> {
                tvSigne.text =getText(R.string.tauro)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.tauro))

            } in 142..172 -> {
                tvSigne.text =getText(R.string.geminis)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.geminis))
            } in 173..203 -> {
                tvSigne.text =getText(R.string.cancer)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.cance))
            } in 204..235 -> {
                tvSigne.text =getText(R.string.leo)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.leo))
            } in 236..266 -> {
                tvSigne.text =getText(R.string.virgo)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.virgo))
            }  in 267..296 -> {
                tvSigne.text =getText(R.string.libra)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.libra))
            }  in 297..326 -> {
                tvSigne.text =getText(R.string.escorpion)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.escorpion))
            }  in 327..356 -> {
                tvSigne.text =getText(R.string.capricornio)
                imageZodiac.setImageDrawable(getDrawable(R.drawable.capricornio))
            } else -> {
                    println("😱")
                }
            }
    }
}