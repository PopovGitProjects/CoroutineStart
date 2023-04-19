package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        updateData()
    }

    private fun updateData() = with(binding) {
        btnUpdate.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() = with(binding) {
        progressBar.isVisible = true
        btnUpdate.isEnabled = false
        loadCity { city ->
            tvCity.text = city
            loadTemp(city = city) { temp ->
                tvTemp.text = temp.toString()
                progressBar.isVisible = false
                btnUpdate.isEnabled = true
            }

        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            callback.invoke("Moscow")
        }

    }

    private fun loadTemp(city: String, callback: (Int) -> Unit) {
        thread {
            Toast.makeText(
                this,
                "Load temperature for city $city",
                Toast.LENGTH_LONG
            ).show()
            Thread.sleep(5000)
            callback.invoke(17)
        }
    }
}