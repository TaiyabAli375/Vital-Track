package com.myexample.vitaltrack

import DB.VitalDatabase
import DB.VitalRepository
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy

import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.myexample.vitaltrack.databinding.AddVitalsDialogBinding
import com.myexample.vitaltrack.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

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
        setPeriodicWorkRequest()
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

    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(RemindingWorker::class.java,24,TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "reminder_work",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}