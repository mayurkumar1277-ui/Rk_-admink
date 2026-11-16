package com.rkadmin.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import android.text.InputType

class MainActivity : Activity() {
    private val users = mutableListOf("Demo User", "Test Account")
    private lateinit var content: LinearLayout
    private val navy = Color.rgb(20,33,61)
    private val blue = Color.rgb(36,87,214)
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showLogin() }
    private fun base(title: String) {
        val root = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(245,247,251)); setPadding(22,24,22,18) }
        val head = TextView(this).apply { text=title; textSize=25f; setTextColor(navy); gravity=Gravity.CENTER_VERTICAL; setPadding(0,8,0,22); setTypeface(null,1) }
        root.addView(head)
        content = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL }
        root.addView(content, LinearLayout.LayoutParams(-1,0,1f))
        setContentView(root)
    }
    private fun button(label:String, action:()->Unit) {
        val b=Button(this).apply { text=label; setTextColor(Color.WHITE); setBackgroundTintList(android.content.res.ColorStateList.valueOf(blue)); setOnClickListener { action() } }
        content.addView(b, LinearLayout.LayoutParams(-1,-2).apply { bottomMargin=10 })
    }
    private fun line(s:String, size:Float=17f) {
        content.addView(TextView(this).apply { text=s; textSize=size; setTextColor(navy); setPadding(4,10,4,10) })
    }
    private fun showLogin() {
        base("RK ADMIN")
        line("Admin Login")
        val pin=EditText(this).apply { hint="Enter PIN (default 1234)"; inputType=InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD }
        content.addView(pin)
        button("LOGIN") { if(pin.text.toString()=="1234") showDashboard() else Toast.makeText(this,"Incorrect PIN",Toast.LENGTH_SHORT).show() }
        line("Offline admin dashboard")
    }
    private fun showDashboard() {
        base("RK ADMIN DASHBOARD")
        line("Welcome, Administrator")
        line("Total users: ${users.size}",22f)
        button("Manage Users") { showUsers() }
        button("Reports") { base("REPORTS"); line("Total users: ${users.size}"); line("Active local records: ${users.size}"); button("Back to Dashboard"){showDashboard()} }
        button("Settings / Change PIN") { showSettings() }
        button("Logout") { showLogin() }
    }
    private fun showUsers() {
        base("MANAGE USERS")
        line("Users stored on this device")
        users.forEachIndexed { i, name ->
            val row=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; gravity=Gravity.CENTER_VERTICAL }
            row.addView(TextView(this).apply { text="${i+1}. $name"; textSize=16f; setTextColor(navy) }, LinearLayout.LayoutParams(0,-2,1f))
            row.addView(Button(this).apply { text="DELETE"; setOnClickListener { users.removeAt(i); showUsers() } })
            content.addView(row)
        }
        button("Add User") {
            val input=EditText(this).apply { hint="User name" }
            android.app.AlertDialog.Builder(this).setTitle("Add user").setView(input)
                .setPositiveButton("ADD") { _,_-> if(input.text.isNotBlank()){users.add(input.text.toString());showUsers()} }
                .setNegativeButton("CANCEL",null).show()
        }
        button("Back"){showDashboard()}
    }
    private fun showSettings() {
        base("SETTINGS")
        line("Default PIN: 1234")
        line("This starter version uses local offline data.")
        button("Back"){showDashboard()}
    }
}
