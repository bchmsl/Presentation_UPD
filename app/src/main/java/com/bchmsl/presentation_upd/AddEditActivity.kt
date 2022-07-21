package com.bchmsl.presentation_upd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log.d
import android.widget.Toast
import com.bchmsl.presentation_upd.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    companion object {
        const val NONE = -1
    }

    private lateinit var binding: ActivityAddEditBinding
    private val userId by lazy { intent.getIntExtra("userId", NONE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        listeners()
        if (userId != NONE) {
            editingUser(userId)
        }
    }

    private fun editingUser(id: Int) {
        val user = usersList.find { user -> user.id == id }
        if (user != null) setContent(user)
    }

    private fun setContent(user: User) {
        binding.apply {
            etName.setText(user.name)
            etEmail.setText(user.email)
        }
    }

    private fun listeners() {
        binding.btnSave.setOnClickListener {
            if (userId == NONE) {
                addUser()
            } else {
                editUser()
            }
        }
    }

    private fun addUser() {
        val name = binding.etName.text
        val email = binding.etEmail.text
        if (checkFields(name, email)) {
            val newUser = User(name.toString(), email.toString())
            usersList.add(newUser)
            finish()
        }
    }

    private fun editUser() {
        val name = binding.etName.text
        val email = binding.etEmail.text
        if (checkFields(name, email)) {
            val user = usersList.find { user -> user.id == userId }
            if (user != null) {
                val newUser = User(name.toString(), email.toString())
                newUser.id = user.id
                usersList[usersList.indexOf(user)] = newUser
                finish()
            } else {
                d("TAG", user.toString())
            }
        }
    }

    private fun checkFields(vararg editTexts: Editable?): Boolean {
        editTexts.forEach { editText ->
            if (editText.isNullOrEmpty()) {
                Toast.makeText(this@AddEditActivity, "Fill all fields!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}