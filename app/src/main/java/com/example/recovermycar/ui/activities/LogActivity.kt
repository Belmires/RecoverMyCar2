package com.example.recovermycar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recovermycar.R
import com.example.recovermycar.ui.activities.NavActivity
import com.example.recovermycar.ui.sql.DataBaseHelper
import kotlinx.android.synthetic.main.activity_log.*

class LogActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DataBaseHelper

    private val activity = this@LogActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        initObjects()

        btnEntrar.setOnClickListener {
            logar()
        }
        btnCadastrarUsuario.setOnClickListener {
            val intent = Intent(this@LogActivity, CadastrarActivity::class.java).apply {

            }
            startActivity(intent)

        }
    }

    private fun logar(){
        val cpf = editTextCPF.text.toString()
        val senha = editTextSenha.text.toString()

        if (cpf.isEmpty()){
            Toast.makeText(applicationContext, "CPF não preenchido", Toast.LENGTH_SHORT).show()
        } else if (senha.isEmpty()){
            Toast.makeText(applicationContext, "Senha não preenchida", Toast.LENGTH_SHORT).show()
        } else if (databaseHelper!!.checkUser(cpf!!.toString().trim { it <= ' ' }, senha!!.toString().trim { it <= ' ' })) {


            val accountsIntent = Intent(this, NavActivity::class.java)
            accountsIntent.putExtra("Cpf", cpf)
            Toast.makeText(applicationContext, "Logado", Toast.LENGTH_SHORT).show()
            startActivity(accountsIntent)
        }else{
            Toast.makeText(applicationContext, "Usuário não cadastrado", Toast.LENGTH_SHORT).show()
        }


    }

    /**
     * Este método é para inicializar objetos a serem usados
     */
    private fun initObjects() {
        databaseHelper = DataBaseHelper(activity)
    }

    private fun limparCampos() {
         editTextCPF.text.clear()
         editTextSenha.text.clear()
    }
}