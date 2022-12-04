package com.example.moviesmanager.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.moviesmanager.R
import com.example.moviesmanager.model.dao.FilmeDao
import com.example.moviesmanager.model.entity.Filme
import java.sql.SQLException

class FilmeSqlite(context: Context) : FilmeDao {

    companion object Constant {
        private const val FILME_DATABASE_FILE = "filme_room"
        private const val FILME_TABLE = "filme"
        private const val ID_COLUMN = "id"
        private const val NOME_COLUMN = "nome"
        private const val ANO_LANCAMENTO_COLUMN = "ano_lancamento"
        private const val ESTUDIO_COLUMN = "estudio"
        private const val TEMPO_DURACAO_COLUMN = "tempo_duracao"
        private const val ASSISTIDO_COLUMN = "assistido"
        private const val NOTA_COLUMN = "nota"
        private const val GENERO_COLUMN = "genero"

        private const val CREATE_FILME_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $FILME_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_COLUMN TEXT NOT NULL UNIQUE, " +
                    "$ANO_LANCAMENTO_COLUMN TEXT NOT NULL, " +
                    "$ESTUDIO_COLUMN TEXT NOT NULL, " +
                    "$TEMPO_DURACAO_COLUMN TEXT NOT NULL, " +
                    "$ASSISTIDO_COLUMN TEXT NOT NULL," +
                    "$NOTA_COLUMN TEXT," +
                    "$GENERO_COLUMN TEXT NOT NULL);"
    }

    // Referência para o banco de dados
    private val filmeSqliteDatabase: SQLiteDatabase

    init {
        // Criando ou abrindo o banco
        filmeSqliteDatabase = context.openOrCreateDatabase(
            FILME_DATABASE_FILE,
            Context.MODE_PRIVATE,
            null
        )
        try {
            filmeSqliteDatabase.execSQL(CREATE_FILME_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    override fun create(filme: Filme): Int {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Int): Filme? {
        TODO("Not yet implemented")
    }

    override fun getAll(): MutableList<Filme> {
        TODO("Not yet implemented")
    }

    override fun update(filme: Filme): Int {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }


}