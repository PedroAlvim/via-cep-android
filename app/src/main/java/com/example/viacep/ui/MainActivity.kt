package com.example.viacep.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.viacep.R
import com.example.viacep.databinding.ActivityMainBinding
import com.example.viacep.ui.event.MessageEvent
import com.example.viacep.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()

        binding.btnSearchCep.setOnClickListener {
            viewModel.getAddress(binding.cep.text.toString())
            hideKeyboard()
        }
    }

    private fun initObservers() {
        viewModel.location.observe(this) { binding.location.setText(it) }
        viewModel.neighborhood.observe(this) { binding.neighborhood.setText(it) }
        viewModel.city.observe(this) { binding.city.setText(it) }
        viewModel.uf.observe(this) { binding.uf.setText(it) }
        viewModel.eventMessage.observe(this) { onEvent(it) }
    }

    private fun onEvent(event: MessageEvent) {
        when (event) {
            MessageEvent.ShowEmptyCep -> showToast(R.string.cep_empty)
            MessageEvent.ShowLoadingCep -> showToast(R.string.loading_info)
            MessageEvent.ShowFindAddress -> showToast(R.string.loading_finish)
            MessageEvent.ShowNotFindAddress -> showToast(R.string.loading_finish_error)
            MessageEvent.ShowErrorFindAddress -> showToast(R.string.error_find_address)

        }
    }

    private fun showToast(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}