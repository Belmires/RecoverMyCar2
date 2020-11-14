package com.example.recovermycar.ui.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recovermycar.ui.model.User
import java.util.*

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_NAME_COMPLET + " TEXT,"
            + COLUMN_USER_CPF + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_SEXO + " TEXT,"
            + COLUMN_USER_PLACA + " TEXT" +")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)

        // Create tables again
        onCreate(db)

    }

    /**
     * Este método busca todos os usuários e retorna a lista de registros do usuário
     *
     * @return list
     */
    fun getAllUser(): List<User> {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_CPF, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)

        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<User>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME_COMPLET)),
                    cpf = cursor.getString(cursor.getColumnIndex(COLUMN_USER_CPF)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    sexo = cursor.getString(cursor.getColumnIndex(COLUMN_USER_SEXO)),
                    placa = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PLACA))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }


    /**
     * Este método é para criar o registro do usuário
     *
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.userName)
        values.put(COLUMN_USER_NAME_COMPLET, user.name)
        values.put(COLUMN_USER_CPF, user.cpf)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_SEXO, user.sexo)
        values.put(COLUMN_USER_PLACA, user.placa)

        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    /**
     * Este método para atualizar o registro do usuário
     *
     * @param user
     */
    fun updateUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.userName)
        values.put(COLUMN_USER_NAME_COMPLET, user.name)
        values.put(COLUMN_USER_CPF, user.cpf)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_SEXO, user.sexo)
        values.put(COLUMN_USER_PLACA, user.placa)


        // updating row
        db.update(
            TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    /**
     * Este método é para excluir o registro do usuário
     *
     * @param user
     */
    fun deleteUser(user: User) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()


    }

    /**
     * Este método para verificar se o usuário existe ou não
     *
     * @param email
     * @return true/false
     */
    fun checkUser(cpf: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_CPF = ?"

        // selection argument
        val selectionArgs = arrayOf(cpf)

        // query user table with condition
        /**
         * Aqui, a função de consulta é usada para buscar registros da tabela do usuário. Esta função funciona como usamos a consulta sql.
         * Consulta SQL equivalente a esta função de consulta é
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    /**
     * Este método para verificar se o usuário existe ou não
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(cpf: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_CPF = ? AND $COLUMN_USER_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(cpf, password)

        // query user table with conditions
        /**
         * Aqui, a função de consulta é usada para buscar registros da tabela do usuário. Esta função funciona como usamos a consulta sql.
         * Consulta SQL equivalente a esta função de consulta é
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }

    fun selectUser(cpf: String) {

        val db = this.readableDatabase
        val query = "Select * from $TABLE_USER where $COLUMN_USER_CPF = $cpf"
        val result: Cursor = db.(query)

        val user = User(
                result.(result.getColumnIndex(COLUMN_USER_ID)).toInt(),
                result.getString(result.getColumnIndex(COLUMN_USER_NAME)),
                result.getString(result.getColumnIndex(COLUMN_USER_NAME_COMPLET)),
                result.getString (result.getColumnIndex(COLUMN_USER_CPF)),
                result.getString(result.getColumnIndex(COLUMN_USER_PASSWORD)),
                result.getString(result.getColumnIndex(COLUMN_USER_EMAIL)),
                result .getString (result.getColumnIndex(COLUMN_USER_SEXO)),
                result.getString(result.getColumnIndex(COLUMN_USER_PLACA))
        )

       return user
    }


    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "RecoverMyCar"

        // User table name
        private val TABLE_USER = "user"

        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_NAME_COMPLET = "user_nameComplet"
        private val COLUMN_USER_CPF = "user_cpf"
        private val COLUMN_USER_PASSWORD = "user_password"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_SEXO = "user_sexo"
        private val COLUMN_USER_PLACA = "user_placa"
    }
}
