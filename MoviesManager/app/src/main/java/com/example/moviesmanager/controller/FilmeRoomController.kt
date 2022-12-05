package com.example.moviesmanager.controller

import android.os.AsyncTask
import androidx.room.Room
import com.example.moviesmanager.model.dao.FilmeRoomDao
import com.example.moviesmanager.model.database.FilmeRoomDatabase
import com.example.moviesmanager.model.entity.Filme
import com.example.moviesmanager.view.MainActivity

class FilmeRoomController (private val mainActivity: MainActivity) {

    private val filmeDaoImpl: FilmeRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            FilmeRoomDatabase::class.java,
            FilmeRoomDao.FILME_DATABASE_FILE
        ).build().getFilmeRoomDao()
    }

    fun insert(filme: Filme) {
        Thread {
            filmeDaoImpl.create(filme)
            getAll()
        }.start()
    }

    fun listOneFilme(nome: String) = filmeDaoImpl.getOne(nome)

    fun getAll() {
        object: AsyncTask<Unit, Unit, MutableList<Filme>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Filme> {
                val returnList = mutableListOf<Filme>()
                returnList.addAll(filmeDaoImpl.getAll())
                return returnList
            }

            override fun onPostExecute(result: MutableList<Filme>?) {
                super.onPostExecute(result)
                if (result != null){
                    mainActivity.updateFilmeList(result)
                }
            }
        }.execute()
    }

    fun update(filme: Filme) {
        Thread {
            filmeDaoImpl.update(filme)
            getAll()
        }.start()
    }

    fun delete(filme: Filme) {
        Thread {
            filmeDaoImpl.delete(filme)
            getAll()
        }.start()
    }
}
