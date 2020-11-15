package com.example.recovermycar.ui.model


data class User constructor(val id: Int = -1, var userName: String, var name: String, val cpf: String, val password: String, var email: String, val sexo: String, val placa: String) {
}