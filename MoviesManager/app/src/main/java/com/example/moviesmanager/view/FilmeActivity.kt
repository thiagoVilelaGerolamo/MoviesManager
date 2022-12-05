package com.example.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesmanager.R
import com.example.moviesmanager.databinding.ActivityFilmeBinding
import com.example.moviesmanager.model.Constant
import com.example.moviesmanager.model.entity.Filme


class FilmeActivity : AppCompatActivity(){
    private val afb: ActivityFilmeBinding by lazy {
        ActivityFilmeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val spinner: Spinner = findViewById(R.id.generoSp)
        ArrayAdapter.createFromResource(
            this,
            R.array.generos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val receivedFilme = intent.getParcelableExtra<Filme>(Constant.EXTRA_FILME)
        receivedFilme?.let{ _receivedFilme ->
            with(afb) {
                with(_receivedFilme) {
                    nomeEt.setText(nome)
                    nomeEt.isEnabled = false
                    anoLancamentoEt.setText(ano_lancamento)
                    estudioEt.setText(estudio)
                    tempoDuracaoEt.setText(tempo_duracao)
                    notaEt.setText(nota)
                    generoSp.setSelection(resources.getStringArray(R.array.generos).indexOf(genero))
                    assistidoCb.isChecked = assistido.toBoolean()
                }
            }
        }
        val viewFilme = intent.getBooleanExtra(Constant.VIEW_FILME, false)
        if (viewFilme) {
            afb.nomeEt.isEnabled = false
            afb.anoLancamentoEt.isEnabled = false
            afb.estudioEt.isEnabled = false
            afb.tempoDuracaoEt.isEnabled = false
            afb.notaEt.isEnabled = false
            afb.generoSp.isEnabled = false
            afb.assistidoCb.isEnabled = false
            afb.saveBt.visibility = View.GONE
        }

        afb.saveBt.setOnClickListener {
            val filme = Filme(
                id = receivedFilme?.id,
                nome = afb.nomeEt.text.toString(),
                ano_lancamento = afb.anoLancamentoEt.text.toString(),
                estudio = afb.estudioEt.text.toString(),
                tempo_duracao = afb.tempoDuracaoEt.text.toString(),
                nota = afb.notaEt.text.toString(),
                genero = afb.generoSp.selectedItem.toString(),
                assistido = if(afb.assistidoCb.isChecked()) "true" else "false",
            )
            val resultIntent = Intent()
            resultIntent.putExtra(Constant.EXTRA_FILME, filme)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}