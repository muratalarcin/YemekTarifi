package com.muratalarcin.yemektarifi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muratalarcin.yemektarifi.model.Specification

@Dao
interface SpecificationDAO {

    @Insert
    suspend fun insertAll(vararg specifications: Specification) : List<Long>

    /*//Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys*/

    //Toplu aldım
    @Query("SELECT * FROM specification ")
    suspend fun getAllSpecifications() : List<Specification>

    //Tek tek aldım
    @Query("SELECT * FROM specification WHERE uuid = :specificationId")
    suspend fun getSpecification(specificationId : Int) : Specification

    @Query("DELETE FROM specification ")
    suspend fun deleteAllSpecifications()


}