package com.whiterabbittest.employeedirectory.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.whiterabbittest.employeedirectory.R

import com.whiterabbittest.employeedirectory.databinding.FragmentEmployeeBinding
import com.whiterabbittest.employeedirectory.fragments.EmployeeDetailsFragment
import com.whiterabbittest.employeedirectory.utils.RoomModels
import org.json.JSONObject


class MyEmployeeRecyclerViewAdapter(var context:Context? = null,
    private val values: List<RoomModels.EmployeeDaoModel>
) : RecyclerView.Adapter<MyEmployeeRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if(context != null)
            Glide.with(context!!).load(values!![position]?.profile_image).into(holder.employeeImgVw)

        holder.employeeNameTxtVw.text = item.name
        val company = item.company
        if(company != null) {
            val jsonObject = JSONObject(company)
            holder.companyNameTxtVw.text = jsonObject.getString("name")
        }
        holder.rootView.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_emplyeeFragment_to_employeeDetailsFragment, bundleOf(
                    EmployeeDetailsFragment.EMPLOYEE_ID to item.id
                )
            )
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val employeeNameTxtVw: TextView = binding.employeeNameTxtVw
        val companyNameTxtVw: TextView = binding.companyNameTxtVw
        val employeeImgVw: ImageView = binding.employeeImgVw
        val rootView: LinearLayout = binding.parentLayout
    }

}