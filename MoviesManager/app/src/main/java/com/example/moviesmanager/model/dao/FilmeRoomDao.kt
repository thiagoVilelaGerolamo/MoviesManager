package com.example.moviesmanager.model.dao

import androidx.room.*
import com.example.moviesmanager.model.entity.Filme

@Dao
interface FilmeRoomDao {

    companion object Constant {
        const val FILME_DATABASE_FILE = "filme_room"
        const val FILME_TABLE = "filme"
        const val NOME_COLUMN = "nome"
        const val ANO_LANCAMENTO_COLUMN = "ano_lancamento"
        const val ESTUDIO_COLUMN = "estudio"
        const val GENERO_COLUMN = "genero"
    }

    @Insert
    fun create(filme: Filme)

    @Query("SELECT * FROM $FILME_TABLE WHERE $NOME_COLUMN = :nome")
    fun getOne(nome: String): Filme?

    @Query("SELECT * FROM $FILME_TABLE")
    fun getAll(): MutableList<Filme>

    @Update
    fun update(filme: Filme): Int

    @Delete
    fun delete(filme: Filme): Int

}