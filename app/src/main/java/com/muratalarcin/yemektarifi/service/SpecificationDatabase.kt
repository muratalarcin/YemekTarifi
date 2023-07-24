package com.muratalarcin.yemektarifi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muratalarcin.yemektarifi.model.Specification

//Veritabanunda değişiklik yaparsak, room a birşeyler eklersek veriyon u değiştirmemiz gerekiyor
@Database(entities = arrayOf(Specification::class), version = 1)
abstract class SpecificationDatabase : RoomDatabase(){

    abstract fun specificationDao(): SpecificationDAO

    //bu işleri singleton yapcaz, treadler birbirine girip conflig olmasın diye

    companion object {

        //Volatile = diğer tread lerde görünürlük sağlıyor
        @Volatile private var instance: SpecificationDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext, SpecificationDatabase::class.java, "specificationdatabase",
        ).build()
    }

}