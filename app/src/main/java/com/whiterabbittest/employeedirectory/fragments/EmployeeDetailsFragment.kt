package com.whiterabbittest.employeedirectory.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.whiterabbittest.employeedirectory.R
import com.whiterabbittest.employeedirectory.adapter.MyEmployeeRecyclerViewAdapter
import com.whiterabbittest.employeedirectory.data.repository.EmployeeRepository
import com.whiterabbittest.employeedirectory.data.viewmodel.EmployeeViewModel
import com.whiterabbittest.employeedirectory.data.webservice.WebService
import com.whiterabbittest.employeedirectory.data.webservice.WebServiceProvider
import com.whiterabbittest.employeedirectory.utils.AppDatabase
import com.whiterabbittest.employeedirectory.utils.Utils
import kotlinx.android.synthetic.main.fragment_employee_details.*


class EmployeeDetailsFragment : Fragment() {

    private var employeeId: String? = null
    private var employeeRepository: EmployeeRepository? = null
    companion object{
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
    }
    private var imageView:ImageView? = null
    private var nameTxtVw:TextView? = null
    private var emailTxtVw:TextView? = null
    private var websiteTxtVw:TextView? = null
    private var adressTxtVw:TextView? = null
    private var userNameTxtVw:TextView? = null
    private var phoneTxtVw:TextView? = null
    private var detailsTxtVw:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employeeId = it.getString(EMPLOYEE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.imageView)
        nameTxtVw = view.findViewById(R.id.nameTxtVw)
        emailTxtVw = view.findViewById(R.id.emailTxtVw)
        adressTxtVw = view.findViewById(R.id.adressTxtVw)
        websiteTxtVw = view.findViewById(R.id.websiteTxtVw)
        userNameTxtVw = view.findViewById(R.id.userNameTxtVw)
        phoneTxtVw = view.findViewById(R.id.phoneTxtVw)
        detailsTxtVw = view.findViewById(R.id.detailsTxtVw)
        getEmployee()
    }

    private fun getEmployee() {
        val webService: WebService? = WebServiceProvider().getInstance()
        val db = Room.databaseBuilder(
            requireActivity(),
            AppDatabase::class.java, Utils.EMPLOYEE_DIRECTORY_DATABASE
        ).build()
        employeeRepository = EmployeeRepository(webService!!, db.employeeListDao(),requireActivity() )
        if (employeeId != null && employeeRepository != null) {
            val viewModel = EmployeeViewModel(employeeRepository!!)
            viewModel.getEmployee(viewLifecycleOwner,employeeId!!)?.observe(viewLifecycleOwner) { employee ->
                if (employee != null) {
                    nameTxtVw?.text = "Name: ${employee.name}"
                    userNameTxtVw?.text = "User Name: ${employee.username}"
                    websiteTxtVw?.text ="Website: ${employee.website}"
                    phoneTxtVw?.text = "Phone: ${employee.phone}"
                    if(employee.company != null) {
                        detailsTxtVw?.text =  "Company: ${employee.company}"
                    }
                    if(employee.address != null) {
                        detailsTxtVw?.text = "Address: ${employee.address}"
                    }
                    emailTxtVw?.text = "Email: ${employee.email}"
                    if(imageView != null)
                        Glide.with(requireContext()).load(employee.profile_image).into(imageView!!)
                } else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}