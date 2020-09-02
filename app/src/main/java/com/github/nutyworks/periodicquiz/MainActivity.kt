package com.github.nutyworks.periodicquiz

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.element_binding.view.*
import slush.Slush
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
        setContentView(R.layout.element_recycler)

        val json = loadFileFromAsset("periodic_table.json")
        elementsInstance = Gson().fromJson(json, Elements::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.element_recycler_view)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        Slush.SingleType<Element>()
            .setItemLayout(R.layout.element_binding)
            .setItems(elements)
            .setLayoutManager(LinearLayoutManager(this))
            .onBind { binding, element ->
                binding.element_symbol.text = element.symbol
                binding.element_category.text = element.category
            }
            .onItemClick { _, i ->
                Toast.makeText(this, elements[i].name, Toast.LENGTH_SHORT).show()
                Intent(this, ElementInfoActivity::class.java).apply {
                    putExtra(ElementInfoActivity.ELEMENT_INFO_NUMBER, i)
                    startActivity(this)
                }
            }
            .into(recyclerView)
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