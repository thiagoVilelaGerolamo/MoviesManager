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

    private fun Filme.toContentValues() = with(ContentValues()) {
        put(NOME_COLUMN, nome)
        put(ANO_LANCAMENTO_COLUMN, ano_lancamento)
        put(ESTUDIO_COLUMN, estudio)
        put(TEMPO_DURACAO_COLUMN, tempo_duracao)
        put(ASSISTIDO_COLUMN, assistido)
        put(NOTA_COLUMN, nota)
        put(GENERO_COLUMN, genero)
        this
    }

    private fun filmeToContentValues(filme: Filme) = with(ContentValues()) {
        put(NOME_COLUMN, filme.nome)
        put(ANO_LANCAMENTO_COLUMN, filme.ano_lancamento)
        put(ESTUDIO_COLUMN, filme.estudio)
        put(TEMPO_DURACAO_COLUMN, filme.tempo_duracao)
        put(ASSISTIDO_COLUMN, filme.assistido)
        put(NOTA_COLUMN, filme.nota)
        put(GENERO_COLUMN, filme.genero)
        this
    }

    private fun Cursor.rowToFilme() = Filme(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NOME_COLUMN)),
        getString(getColumnIndexOrThrow(ANO_LANCAMENTO_COLUMN)),
        getString(getColumnIndexOrThrow(ESTUDIO_COLUMN)),
        getString(getColumnIndexOrThrow(TEMPO_DURACAO_COLUMN)),
        getString(getColumnIndexOrThrow(ASSISTIDO_COLUMN)),
        getString(getColumnIndexOrThrow(NOTA_COLUMN)),
        getString(getColumnIndexOrThrow(GENERO_COLUMN))
    )

    override fun create(filme: Filme) = filmeSqliteDatabase.insert(
        FILME_TABLE,
        null,
        filmeToContentValues(filme)
    ).toInt()

    override fun getOne(id: Int): Filme? {
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM $FILME_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val filme = if (cursor.moveToFirst()) cursor.rowToFilme() else null

        cursor.close()
        return filme
    }

    override fun getAll(): MutableList<Filme> {
        val filmeList = mutableListOf<Filme>()
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM $FILME_TABLE",
            null
        )
        while (cursor.moveToNext()) {
            filmeList.add(cursor.rowToFilme())
        }
        cursor.close()
        return filmeList
    }

    override fun update(filme: Filme) = filmeSqliteDatabase.update(
        FILME_TABLE,
        filme.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(filme.nome)
    )

    override fun delete(id: Int) =
        filmeSqliteDatabase.delete(
            FILME_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )
}