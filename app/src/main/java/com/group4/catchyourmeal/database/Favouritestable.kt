package com.group4.catchyourmeal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favourites")
data class Favouritestable (
    @PrimaryKey(autoGenerate = true)
    var id:Long= 0,

    @ColumnInfo(name="item_name")
    var Name:String="",

    @ColumnInfo(name="item_image")
    var Image:String="",
    @ColumnInfo(name="item_price")
    var Price:String=""
)