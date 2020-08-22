package com.group4.catchyourmeal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface cartDao {
    @Insert
    suspend fun insert(fav: carttable):Long
    @Update
    suspend fun update(fav: carttable)

    @Query("select * from table_cart where id=:key")
    suspend fun get(key:Long): carttable

    @Query("DELETE FROM table_cart")
    suspend fun deleteAll ()

    @Query("DELETE FROM table_cart where id=:key")
    suspend fun delete(key:Long)

    @Query("SELECT * FROM table_cart order by id DESC LIMIT 1")
    suspend fun getTopFav(): carttable?

    @Query("select * from table_cart")
    suspend fun getAll(): List<carttable>

}