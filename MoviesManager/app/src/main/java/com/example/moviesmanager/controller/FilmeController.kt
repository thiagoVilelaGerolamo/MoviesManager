package com.example.moviesmanager.controller

import com.example.moviesmanager.model.dao.FilmeDao
import com.example.moviesmanager.model.database.FilmeSqlite
import com.example.moviesmanager.model.entity.Filme
import com.example.moviesmanager.view.MainActivity

class FilmeController(private val mainActivity: MainActivity) {

    private val filmeDaoImpl:FilmeDao = FilmeSqlite(mainActivity)

    fun insert(filme: Filme) = filmeDaoImpl.create(filme)
    fun getOne(id: Int) = filmeDaoImpl.getOne(id)
    fun getAll() {
        Thread {
            val returnedList = filmeDaoImpl.getAll()
            mainActivity.updateFilmeList(returnedList)
        }.start()
    }
    fun updateFilme(filme: Filme) = filmeDaoImpl.update(filme)
    fun deleteFilme(id: Int) = filmeDaoImpl.delete(id)
}