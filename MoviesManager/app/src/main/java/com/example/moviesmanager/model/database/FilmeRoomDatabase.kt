package com.example.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesmanager.model.dao.FilmeRoomDao
import com.example.moviesmanager.model.entity.Filme

@Database(entities = [Filme::class], version = 1)
abstract class FilmeRoomDatabase: RoomDatabase() {
    abstract fun getFilmeRoomDao(): FilmeRoomDao

}