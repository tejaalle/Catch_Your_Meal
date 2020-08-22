package com.group4.catchyourmeal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [carttable::class,Favouritestable::class],version = 3,exportSchema = false)
abstract class mealdatabase: RoomDatabase() {
    abstract val cartDao:cartDao
    abstract val favouritesDao:FavouritesDao
    companion object{
        @Volatile
        private var INSTANCE:mealdatabase?=null

        fun getInstance(context: Context) :mealdatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        mealdatabase::class.java,
                        "meal_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE=instance
                }
                return instance
            }


        }


    }
}