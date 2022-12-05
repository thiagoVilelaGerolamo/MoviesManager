package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.FilmeAdapter
import com.example.moviesmanager.controller.FilmeRoomController
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant
import com.example.moviesmanager.model.entity.Filme

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val filmeList: MutableList<Filme> = mutableListOf()

    // Adapter
    private lateinit var filmeAdapter: FilmeAdapter

    private lateinit var arl: ActivityResultLauncher<Intent>

    // Controller
    private val filmeController: FilmeRoomController by lazy {
        FilmeRoomController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        filmeAdapter = FilmeAdapter(this, filmeList)
        amb.filmesLv.adapter = filmeAdapter

        arl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val filme = result.data?.getParcelableExtra<Filme>(Constant.EXTRA_FILME)
                filme?.let { _filme->
                    if (_filme.id != null) {
                        val position = filmeList.indexOfFirst { it.id == _filme.id }
                        if (position != -1) {
                            // Alterar na posição
                            filmeController.update(_filme)
                        }
                    }
                    else {
                        filmeController.insert(_filme)
                    }
                }
            }
        }

        registerForContextMenu(amb.filmesLv)

        amb.filmesLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val contact = filmeList[position]
                val contactIntent = Intent(this@MainActivity, FilmeActivity::class.java)
                contactIntent.putExtra(Constant.EXTRA_FILME, contact)
                contactIntent.putExtra(Constant.VIEW_FILME, true)
                startActivity(contactIntent)
            }

        // Buscando contatos no banco
        filmeController.getAll()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordenar_main, menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.ordenarNotaMi -> {
                filmeList.sortBy { it.nota }
                filmeAdapter.notifyDataSetChanged()
                true
            }
            R.id.ordenarNomeMi -> {
                filmeList.sortBy { it.nome }
                filmeAdapter.notifyDataSetChanged()
                true
            }
            R.id.addFilmeMi -> {
                arl.launch(Intent(this, FilmeActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val filme = filmeList[position]
        return when(item.itemId) {
            R.id.removeFilmeMi -> {
                // Remove o filme
                filmeController.delete(filme)
                true
            }
            R.id.editFilmeMi -> {
                // Chama a tela para editar o filme
                val filmeIntent = Intent(this, FilmeActivity::class.java)
                filmeIntent.putExtra(Constant.EXTRA_FILME, filme)
                filmeIntent.putExtra(Constant.VIEW_FILME, false)
                arl.launch(filmeIntent)
                true
            }
            else -> { false }
        }
    }

    fun updateFilmeList(_filmeList: MutableList<Filme>) {
        filmeList.clear()
        filmeList.addAll(_filmeList)
        filmeAdapter.notifyDataSetChanged()
    }

}