package com.myexample.vitaltrack

import DB.Vital
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myexample.vitaltrack.databinding.VitalsRowLayoutBinding

class VitalsRcAdapter() : RecyclerView.Adapter<MyViewHolder>() {
    private val vitals = ArrayList<Vital>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : VitalsRowLayoutBinding =
            DataBindingUtil.inflate(layoutInflater,R.layout.vitals_row_layout,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return vitals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(vitals[position])
    }

    fun setList(newVitals:List<Vital>){
        vitals.clear()
        vitals.addAll(newVitals)
    }

}
class MyViewHolder(val binding: VitalsRowLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(vital: Vital){
        binding.heartRatetv.text = "${vital.heartRate} bpm"
        binding.bloodPressuretv.text = "${vital.SysBp}/${vital.DiaBp} mmHg"
        binding.weighttv.text = "${vital.Weight} Kg"
        binding.bloodSugarTv.text = "${vital.Kicks} mg/dL"
        binding.formattedDateTv.text = vital.formattedDate
    }
}