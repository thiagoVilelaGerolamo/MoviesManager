package com.example.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["nome"], unique = true)])
data class Filme(
    @PrimaryKey (autoGenerate = true)
    var id: Int?,
    @NonNull
    var nome: String,
    @NonNull
    var ano_lancamento: String,
    @NonNull
    var estudio: String,
    @NonNull
    var tempo_duracao: String,
    @NonNull
    var assistido: String,
    var nota: String,
    @NonNull
    var genero: String,
): Parcelable

