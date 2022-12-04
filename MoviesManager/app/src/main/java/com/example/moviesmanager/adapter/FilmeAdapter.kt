package com.example.moviesmanager.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moviesmanager.R
import com.example.moviesmanager.model.entity.Filme

class FilmeAdapter (
    context: Context,
    private val filmeList: MutableList<Filme>
) : ArrayAdapter<Filme>(context, R.layout.tile_filme, filmeList) {

    private data class TileFilmeHolder(val nomeTv: TextView,
                                       val notaTv: TextView,
                                       val generoTv: TextView,
                                       val estudioTv: TextView)

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val filme = filmeList[position]
        var filmeTileView = convertView
        if (filmeTileView == null) {
            filmeTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_filme,
                    parent,
                    false
                )

        val tileFilmeHolder = TileFilmeHolder(
            filmeTileView.findViewById(R.id.nomeTv),
            filmeTileView.findViewById(R.id.notaTv),
            filmeTileView.findViewById(R.id.generoTv),
            filmeTileView.findViewById(R.id.estudioTv),
            )
            filmeTileView.tag = tileFilmeHolder
        }

        with(filmeTileView?.tag as TileFilmeHolder) {
            if(filme.assistido == "true") nomeTv.setTextColor(Color.BLACK) else nomeTv.setTextColor(Color.LTGRAY)
            nomeTv.text = filme.nome
            notaTv.text = "Nota: " + filme.nota
            generoTv.text ="Gen: " + filme.genero
            estudioTv.text ="Est/Ano: "  + filme.estudio +"/"+filme.ano_lancamento
        }
        return filmeTileView
    }
}
