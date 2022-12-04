package com.example.moviesmanager.model.dao

import com.example.moviesmanager.model.entity.Filme

interface FilmeDao {

    fun create(filme: Filme): Int
    fun getOne(id: Int): Filme?
    fun getAll(): MutableList<Filme>
    fun update(filme: Filme): Int
    fun delete(id: Int): Int
}