package com.example.recovermycar.ui.activities

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.recovermycar.R
import com.example.recovermycar.ui.sql.DataBaseHelper
import kotlinx.android.synthetic.main.nav_header_main.*


class NavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var databaseHelper: DataBaseHelper

    private val activity = this@NavActivity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        initObjects()

        val usercpf = intent.getStringExtra("Cpf").toString()
        Toast.makeText(applicationContext, usercpf, Toast.LENGTH_SHORT).show()

        var usuario = databaseHelper!!.selectUser(usercpf)

        var idUsuario = usuario.id
        var nomeUsuario = usuario.name
        var nomeCompleto = usuario.userName
        var cpfUsuario = usuario.name
        var passwordUsuario= usuario.cpf
        var EmailUsuario= usuario.password
        var sexoUsuario= usuario.email
        var placaUsuario= usuario.sexo
        var placa = usuario.placa


        //textName.text = "nomeUsuario"
        //textEmail.text = userList.toString()
        //textUserName.text = userName

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initObjects() {
        databaseHelper = DataBaseHelper(activity)
    }
}