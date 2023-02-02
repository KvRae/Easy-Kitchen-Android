package devsec.app.easykitchen.utils.session

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import devsec.app.easykitchen.ui.main.view.LoginActivity

class SessionPref {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context
    var PRIVATE_MODE = 0

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        const val PREF_NAME = "login_pref"
        const val IS_LOGGED_IN = "is_logged_in"
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
        const val USER_PHONE = "user_phone"
        const val USER_ADDRESS = "user_address"
        const val USER_IMAGE = "user_image"
    }

    fun createLoginSession(username: String, password: String) {
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(USER_NAME, username)
        editor.putString(USER_PASSWORD, password)
        editor.commit()
    }

    fun createRegisterSession(id: String, username: String, email: String, password: String, phone: String)
    {
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(USER_ID, id)
        editor.putString(USER_NAME, username)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_PASSWORD, password)
        editor.putString(USER_PHONE, phone)
        editor.commit()
    }

    fun updateProfileSession(id: String, username: String, email: String,password: String, phone: String)
    {
        editor.putString(USER_ID, id)
        editor.putString(USER_NAME, username)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_PASSWORD, password)
        editor.putString(USER_PHONE, phone)
        editor.commit()
    }


    fun checkLogin() {
        if (!this.isLoggedIn()) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun getUserPref(): HashMap<String, String> {
        val user = HashMap<String, String>()
        user.put(USER_ID, pref.getString(USER_ID, null)!!)
        user.put(USER_NAME, pref.getString(USER_NAME, "username")!!)
        user.put(USER_EMAIL, pref.getString(USER_EMAIL, "email")!!)
        user.put(USER_PASSWORD, pref.getString(USER_PASSWORD, "password")!!)
        user.put(USER_PHONE, pref.getString(USER_PHONE, "phone")!!)
//        user.put(USER_IMAGE, pref.getString(USER_IMAGE, null)!!)
        return user

    }

    fun setUserPref(username: String, email: String, password: String, phone: String)
    {
        editor.putString(USER_NAME, username)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_PASSWORD, password)
        editor.putString(USER_PHONE, phone)
        editor.commit()
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun getId(): String {
        return pref.getString(USER_ID, "id")!!
    }
}