package com.whiterabbittest.employeedirectory.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.whiterabbittest.employeedirectory.R
import com.whiterabbittest.employeedirectory.adapter.MyEmployeeRecyclerViewAdapter
import com.whiterabbittest.employeedirectory.data.repository.EmployeeRepository
import com.whiterabbittest.employeedirectory.data.viewmodel.EmployeeViewModel
import com.whiterabbittest.employeedirectory.data.webservice.WebService
import com.whiterabbittest.employeedirectory.data.webservice.WebServiceProvider
import com.whiterabbittest.employeedirectory.utils.AppDatabase
import com.whiterabbittest.employeedirectory.utils.Utils


class EmployeeDetailsFragment : Fragment() {

    private var employeeId: String? = null
    private var employeeRepository: EmployeeRepository? = null
    companion object{
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
    }

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

    private fun getProfile() {
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

                } else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}