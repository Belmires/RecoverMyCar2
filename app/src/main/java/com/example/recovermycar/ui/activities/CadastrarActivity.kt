package com.example.recovermycar.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recovermycar.R
import com.example.recovermycar.ui.model.User
import com.example.recovermycar.ui.sql.DataBaseHelper
import kotlinx.android.synthetic.main.activity_cadastrar.*

class CadastrarActivity : AppCompatActivity() {

    var sexo = ""
    private val activity = this@CadastrarActivity

    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        initObjects()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.btnRadioMasc) {
                sexo = btnRadioMasc.text.toString()
            }
            if (checkedId == R.id.btnRadioFem) {
                sexo = btnRadioFem.text.toString()
            }
        }

         btnCadastrarUsuario.setOnClickListener {

             cadastrarUsuario()
         }

    }


    /**
     * Este método serve para validar os campos de texto de entrada e postar dados no SQLite
     */
    private fun cadastrarUsuario(){

        val userName =  inputTextUserNameCad.text.toString()
        val cpf = inputTextCPFCad.text.toString()
        val password = inputTextPasswordCad.text.toString()
        val confirmPassword = inputTextConfirmPasswordCad.text.toString()
        val nome = inputTextName.text.toString()
        val email = inputTextEmail.text.toString()
        val placa = inputTextPlaca.text.toString()

            if (userName.equals("")) {
                Toast.makeText(applicationContext, "Usuario não preenchido", Toast.LENGTH_SHORT)
                    .show()
            } else if (nome.equals("")) {
                Toast.makeText(applicationContext, "Nome não preenchido", Toast.LENGTH_SHORT).show()
            } else if (cpf.equals("")) {
                Toast.makeText(applicationContext, "Nome não preenchido", Toast.LENGTH_SHORT).show()
            } else if (password.equals("") || confirmPassword.equals("")) {
                Toast.makeText(applicationContext, "Senhas não preenchidas", Toast.LENGTH_SHORT)
                    .show()
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(applicationContext, "Senhas não conferem", Toast.LENGTH_SHORT).show()
            } else if (email.equals("")) {
                Toast.makeText(applicationContext, "Email não preenchido", Toast.LENGTH_SHORT)
                    .show()
            } else if (sexo.equals("")) {
                Toast.makeText(applicationContext, "Sexo não preenchido", Toast.LENGTH_SHORT).show()
            } else if (placa.equals("")) {
                Toast.makeText(applicationContext, "Placa não preenchida", Toast.LENGTH_SHORT)
                    .show()
            } else if (!databaseHelper!!.checkUser(cpf!!.trim())) {
                val user = User(
                    userName = userName!!.trim(),
                    name = nome!!.trim(),
                    cpf = cpf!!.trim(),
                    password = password!!.trim(),
                    email = email!!.trim(),
                    sexo = sexo!!.trim(),
                    placa = placa!!.trim()
                )

                databaseHelper!!.addUser(user)

                Toast.makeText(applicationContext, "Usuario Cadastrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Usuario ja Cadastrado", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    /**
     * Este método é para inicializar objetos a serem usados
     */
    private fun initObjects() {
        databaseHelper = DataBaseHelper(activity)


    }







}