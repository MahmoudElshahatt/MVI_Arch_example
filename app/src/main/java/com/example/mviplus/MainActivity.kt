package com.example.mviplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mviplus.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: AddNumberViewModel by lazy {
        ViewModelProvider(this)[AddNumberViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        render()
        binding.btnAddNumber.setOnClickListener {
            //send
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumberIntent)
            }

        }
    }

    private fun render() {
        //render
        lifecycleScope.launchWhenStarted {
            viewModel.viewStates.collect {
                when (it) {
                    is MainStates.Error -> binding.txtNumber.text = it.error
                    is MainStates.Idle -> binding.txtNumber.text = "Idle"
                    is MainStates.Number -> binding.txtNumber.text = "" + it.number
                }
            }
        }
    }
}