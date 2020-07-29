package com.github.nutyworks.periodicquiz

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.element_recycler)

        val rawJson = loadFileFromAsset("periodic_table.json")
        val gson = Gson()
        val elements = gson.fromJson(rawJson, Elements::class.java)

        val str = StringBuffer()
        elements.elements.joinTo(str) { element -> element.symbol }

        val recyclerView = findViewById<RecyclerView>(R.id.element_recycler_view)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        Slush.SingleType<Element>()
            .setItemLayout(R.layout.element_binding)
            .setItems(elements.elements)
            .setLayoutManager(LinearLayoutManager(this))
            .onBind { binding, element ->
                binding.element_symbol.text = element.symbol
                binding.element_category.text = element.category
            }
            .onItemClick { _, i ->
                Toast.makeText(this, elements.elements[i].name, Toast.LENGTH_SHORT).show()
            }
            .into(recyclerView)
    }

    /**
     * Loads file from asset
     * @return raw content of file
     */
    private fun loadFileFromAsset(fileName: String): String? {
        var json: String? = null
        json = try {
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
        return json
    }
}