package com.group4.catchyourmeal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavouritesDao {
    @Insert
    suspend fun insert(fav: Favouritestable)
    @Update
    suspend fun update(fav: Favouritestable)

    @Query("select * from table_favourites where id=:key")
    suspend fun get(key:Long): Favouritestable

    @Query("DELETE FROM table_favourites")
    suspend fun deleteAll ()

    @Query("DELETE FROM table_favourites where id=:key")
    suspend fun delete(key:Long)

    @Query("SELECT * FROM table_favourites order by id DESC LIMIT 1")
    suspend fun getTopFav(): Favouritestable?

    @Query("select * from table_favourites")
    suspend fun getAll(): List<Favouritestable>
}