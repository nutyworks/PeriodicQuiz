package com.github.nutyworks.periodicquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.github.nutyworks.periodicquiz.quiz.QuizOptionActivity
import com.github.nutyworks.periodicquiz.quiz.SelectQuizActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var elementsInstance: Elements
        val elements
            get() = elementsInstance.elements
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)

        quiz_btn.setOnClickListener {
            Intent(this, QuizOptionActivity::class.java).apply {
                startActivity(this)
            }
        }

        val json = loadFileFromAsset("periodic_table.json")
        elementsInstance = Gson().fromJson(json, Elements::class.java)

//        val recyclerView = findViewById<RecyclerView>(R.id.element_recycler_view)
//
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//
//        Slush.SingleType<Element>()
//            .setItemLayout(R.layout.element_binding)
//            .setItems(elements)
//            .setLayoutManager(LinearLayoutManager(this))
//            .onBind { binding, element ->
//                binding.element_symbol.text = element.symbol
//                binding.element_category.text = element.category
//            }
//            .onItemClick { _, i ->
//                Toast.makeText(this, elements[i].name, Toast.LENGTH_SHORT).show()
//                Intent(this, ElementInfoActivity::class.java).apply {
//                    putExtra(ElementInfoActivity.ELEMENT_INFO_NUMBER, i)
//                    startActivity(this)
//                }
//            }
//            .into(recyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_settings -> {
            Log.d("TOOLBAR TEST", "settings")
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }

            true
        }
        R.id.action_favorite -> {
            Log.d("TOOLBAR TEST", "mark as favorite")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        val expandListener = object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Log.d("LISTENER", "menu item collapse")
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Log.d("LISTENER", "menu item expand")
                return true
            }
        }

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchItem.setOnActionExpandListener(expandListener)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Loads file from asset
     * @return raw content of file
     */
    private fun loadFileFromAsset(fileName: String): String? {
        return try {
            val inputStream: InputStream = assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            buffer.toString(Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}