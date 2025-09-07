package com.myexample.vitaltrack

import DB.VitalDatabase
import DB.VitalRepository
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.myexample.vitaltrack.R
import com.myexample.vitaltrack.databinding.AddVitalsDialogBinding
import com.myexample.vitaltrack.databinding.ActivityMainBinding
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : VitalViewModel
    private lateinit var adapter: VitalsRcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = VitalDatabase.getInstance(application).vitalDAO
        val repository = VitalRepository(dao)
        viewModel = ViewModelProvider(this,VitalViewModelFactory(repository))
            .get(VitalViewModel::class.java)

        initVitalsRc()
        addfabClicked()

    }

    fun initVitalsRc(){
        adapter = VitalsRcAdapter()
        binding.vitalRc.layoutManager = LinearLayoutManager(this)
        binding.vitalRc.adapter = adapter
        displayVitalsList()
    }
    fun addfabClicked(){
        binding.addFab.setOnClickListener{
            showDialog()
        }
    }
    fun showDialog(){
        val dialog = Dialog(this)
        val bindingDialog: AddVitalsDialogBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.add_vitals_dialog, null, false)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.myViewModel = viewModel
        bindingDialog.lifecycleOwner = this

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val widthInPx = (340 * resources.displayMetrics.density).toInt()
        dialog.window?.setLayout(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun displayVitalsList(){
        viewModel.vitals.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

}