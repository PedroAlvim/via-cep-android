package com.example.viacep

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viacep.api.Api
import com.example.viacep.databinding.ActivityMainBinding
import com.example.viacep.model.EnderecoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FF018786")
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF018786")))

        //Retrofit
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://viacep.com.br/ws/")
            .build()
            .create(Api::class.java)

        binding.btnSearchCep.setOnClickListener {
            val cep = binding.editTextCep.text.toString()

            if (cep.isEmpty()) {
                Toast.makeText(this, getString(R.string.cep_empty), Toast.LENGTH_SHORT).show()
            } else {

                retrofit.setEndereco(cep).enqueue(object : Callback<EnderecoModel>{
                    override fun onResponse(
                        call: Call<EnderecoModel>,
                        response: Response<EnderecoModel>
                    ) {
                       if(response.code() == 200){
                           binding.editTextLogradouro.setText(response.body()?.logradouro)
                           binding.editTextBairro.setText(response.body()?.bairro)
                           binding.editTextCity.setText(response.body()?.localidade)
                           binding.editTextEstado.setText(response.body()?.uf)
                       }else{
                           Toast.makeText(this@MainActivity, "não foi possivel realizar a busca", Toast.LENGTH_SHORT).show()
                       }
                    }

                    override fun onFailure(call: Call<EnderecoModel>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "error in call", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}