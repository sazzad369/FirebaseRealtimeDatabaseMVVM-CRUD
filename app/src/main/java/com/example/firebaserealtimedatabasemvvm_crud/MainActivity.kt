package com.example.firebaserealtimedatabasemvvm_crud

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtimedatabasemvvm_crud.adapter.Dataadapter
import com.example.firebaserealtimedatabasemvvm_crud.databinding.ActivityMainBinding
import com.example.firebaserealtimedatabasemvvm_crud.model.Data
import com.example.firebaserealtimedatabasemvvm_crud.viewmodel.DataViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val  dataViewModel : DataViewModel by  viewModels()
    private lateinit var adapter: Dataadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        adapter= Dataadapter(listOf(),this)

        binding.recyclerView.adapter =adapter
        binding.recyclerView.layoutManager =LinearLayoutManager(this)

        dataViewModel.dataList.observe(this, Observer {
            if (it!=null){
                adapter.updateData(it)
            }else{
                Toast.makeText(this@MainActivity,"Error Fetching Data", Toast.LENGTH_SHORT).show()
            }

        })

        dataViewModel.error.observe(this, Observer { errorMessage->
            errorMessage?.let {
                Toast.makeText(this@MainActivity,it,Toast.LENGTH_SHORT).show()
            }
        })

        binding.addBtn.setOnClickListener {
            val sudid = binding.idEtxt.text.toString()
            val name= binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectEtxt.text.toString()
            val birthdate = binding.birthdateEtxt.text.toString()

            if (sudid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthdate.isNotEmpty()){

                val data = Data(null,sudid, name , email, subject, birthdate)


                dataViewModel.addData(data, onSucces = {
                    clearInputField()
                    Toast.makeText(this@MainActivity,"Data Saved Successfully",Toast.LENGTH_SHORT).show()
                }, onFailure = {
                    Toast.makeText(this@MainActivity,"Failed to Add Data",Toast.LENGTH_SHORT).show()
                })
            }

        }

    }
    private fun clearInputField() {
        binding.idEtxt.text?.clear()
        binding.nameEtxt.text?.clear()
        binding.emailEtxt.text?.clear()
        binding.subjectEtxt.text?.clear()
        binding.birthdateEtxt.text?.clear()
    }

    fun onEditItemClick(data: Data) {

        binding.idEtxt.setText(data.studid)
        binding.nameEtxt.setText(data.name)
        binding.emailEtxt.setText(data.email)
        binding.subjectEtxt.setText(data.subject)
        binding.birthdateEtxt.setText(data.birthdate)

        binding.addBtn.setOnClickListener {
            val updateData = Data(data.id, binding.idEtxt.text.toString(),binding.nameEtxt.text.toString(),binding.emailEtxt.text.toString(),binding.subjectEtxt.text.toString(),binding.birthdateEtxt.text.toString())

            dataViewModel.updateData(updateData)
            clearInputField()
            Toast.makeText(this@MainActivity, "Data Update Successfully",Toast.LENGTH_SHORT).show()
        }

    }

    fun onDeleteClick(data: Data) {

        fun onDeleteClick(data: Data) {

            AlertDialog.Builder(this).apply {
                setTitle("Delete Confirmation")
                setMessage("Are You sure tou are want to delete this data?")
                setPositiveButton("Yes"){_,_->}
                dataViewModel.deleteData(data,
                    onSucces = {
                        Toast.makeText(this@MainActivity,"Data deleted",Toast.LENGTH_SHORT).show()
                    }, onFailure = {Toast.makeText(this@MainActivity,"Failed to delete data",Toast.LENGTH_SHORT).show()}
                )
            }
        }

    }

}