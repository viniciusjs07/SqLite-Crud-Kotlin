package com.example.sqlite_crud

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_crud.adapter.CentralAdapter
import com.example.sqlite_crud.helper.SQLiteHelper
import com.example.sqlite_crud.model.CentralModel

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edModel: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button


    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var recyclerView: RecyclerView
    private var adapter: CentralAdapter? = null
    private var centralModel: CentralModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()

        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addCentral() }
        btnView.setOnClickListener { getCentral() }
        btnUpdate.setOnClickListener { updateCentral() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edModel.setText(it.model)
            centralModel = it
        }

        adapter?.setOnClickDeleteItem {
            deleteCentral(it.id)
        }
    }

    private fun updateCentral() {
        val name = edName.text.toString()
        val model = edModel.text.toString()
        if (name == centralModel?.name && model == centralModel?.model) {
            Toast.makeText(this, "Recorde central not changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (centralModel == null) return
        val ctrl = CentralModel(id = centralModel!!.id, name = name, model = model)
        val status  = sqliteHelper.updateCentral(ctrl)
        if (status > -1) {
            Toast.makeText(this, "Central update success...", Toast.LENGTH_SHORT).show()
            clearEditText()
            getCentral()
        } else {
            Toast.makeText(this, "Central update failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteCentral(id: Int) {
        val builder =AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete cental ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") {
            dialog, _ ->
            sqliteHelper.deleteCentralById(id)
            getCentral()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {dialog, _ -> dialog.dismiss()}

        val alert = builder.show()
        alert.show()
    }


    private fun addCentral() {
        val name = edName.text.toString()
        val model = edModel.text.toString()

        if (name.isEmpty() || model.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val central = CentralModel(name = name, model = model)
            val status = sqliteHelper.insertCentral(central)
            if (status > -1) {
                Toast.makeText(this, "Central Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getCentral()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()

            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCentral() {
        val centralList = sqliteHelper.getAllCentral()
        adapter?.addItems(centralList)
        adapter?.notifyDataSetChanged()
    }

    private fun clearEditText() {
        edName.setText("")
        edModel.setText("")
        edName.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.et_name)
        edModel = findViewById(R.id.et_model)
        btnAdd = findViewById(R.id.bt_add)
        btnView = findViewById(R.id.bt_view)
        btnUpdate = findViewById(R.id.bt_update)
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CentralAdapter()
        recyclerView.adapter = adapter
    }
}