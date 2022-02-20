package com.whiterabbittest.employeedirectory.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.room.Room
import com.whiterabbittest.employeedirectory.R
import com.whiterabbittest.employeedirectory.adapter.MyEmployeeRecyclerViewAdapter
import com.whiterabbittest.employeedirectory.data.repository.EmployeeRepository
import com.whiterabbittest.employeedirectory.data.viewmodel.EmployeeViewModel
import com.whiterabbittest.employeedirectory.data.webservice.WebService
import com.whiterabbittest.employeedirectory.data.webservice.WebServiceProvider
import com.whiterabbittest.employeedirectory.utils.AppDatabase
import com.whiterabbittest.employeedirectory.utils.Utils

class EmployeeFragment : Fragment() {

    private var columnCount = 1
    private var employeeRepository: EmployeeRepository? = null
    private var progressBar:ProgressBar? = null
    private var recyclerView:RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list)
        progressBar = view.findViewById(R.id.progressBar)
        getEmployee()
    }

    private fun getEmployee() {
        progressBar?.visibility = View.VISIBLE
        val webService: WebService? = WebServiceProvider().getInstance()
        val db = Room.databaseBuilder(
            requireActivity(),
            AppDatabase::class.java, Utils.EMPLOYEE_DIRECTORY_DATABASE
        ).build()
        employeeRepository = EmployeeRepository(webService!!, db.employeeListDao(),requireActivity() )
        if (webService != null && employeeRepository != null) {
            val viewModel = EmployeeViewModel(employeeRepository!!)
            viewModel.getEmployeeList(viewLifecycleOwner)?.observe(viewLifecycleOwner) { employeeList ->
                progressBar?.visibility = View.GONE
                if (employeeList != null && employeeList.isNotEmpty() && recyclerView != null) {
                    with(recyclerView!!) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = MyEmployeeRecyclerViewAdapter(context,employeeList)
                    }
                } else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}