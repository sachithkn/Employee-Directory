package com.whiterabbittest.employeedirectory.data.repository

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.whiterabbittest.employeedirectory.data.dao.EmployeeDao
import com.whiterabbittest.employeedirectory.utils.Models
import com.whiterabbittest.employeedirectory.data.webservice.WebService
import com.whiterabbittest.employeedirectory.utils.RoomModels
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class EmployeeRepository(private val webService: WebService,
                        private val employeeDao: EmployeeDao,
                         private val activity: Activity?) {

    private var disposable: Disposable? = null

    fun getEmployeeList(owner: LifecycleOwner): LiveData<List<RoomModels.EmployeeDaoModel>>? {
        val data: MutableLiveData<List<RoomModels.EmployeeDaoModel>>? = MutableLiveData()
        var list:List<RoomModels.EmployeeDaoModel>
        employeeDao.getAll().observe(owner, androidx.lifecycle.Observer {
            list = it
            if (list.isNotEmpty()) {
                activity?.runOnUiThread { data?.value = list }
            }else{
                webService.getEmployeeList().subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<List<Models.Employee>?> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onError(e: Throwable) {}
                        override fun onComplete() {}
                        override fun onNext(employeeList: List<Models.Employee>) {
                            disposable = Observable.fromCallable {
                                val employeeRoomList = convertServiceModelToRoomModel(employeeList)
                                employeeDao.insertAll(*employeeRoomList.toTypedArray())
                                activity?.runOnUiThread { data?.value = employeeRoomList }
                            }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe {}
                        }
                    })
            }
        })

        return data
    }

    fun getEmployee(owner: LifecycleOwner,id:String):MutableLiveData<RoomModels.EmployeeDaoModel>{
        val data:MutableLiveData<RoomModels.EmployeeDaoModel> = MutableLiveData()
        var employee: RoomModels.EmployeeDaoModel
        employeeDao.getEmployee(id).observe(owner) {
            employee = it
            data.value = employee
        }
        return data
    }

    fun convertServiceModelToRoomModel(employeeList: List<Models.Employee>):List<RoomModels.EmployeeDaoModel>{
        val list:ArrayList<RoomModels.EmployeeDaoModel> = ArrayList()
        for(employee in employeeList ){
            val employeeModel = RoomModels.EmployeeDaoModel(employee.id!!);
            val addressModel = RoomModels.AddressDaoModel();
            val companyModel = RoomModels.CompanyDaoModel();

            val geoModel = RoomModels.GeoDaoModel();
            geoModel.lat = employee.address?.geo?.lat
            geoModel.lng = employee.address?.geo?.lng

            addressModel.city = employee.address?.city
            addressModel.street = employee.address?.street
            addressModel.suite = employee.address?.suite
            addressModel.zipcode = employee.address?.zipcode
            addressModel.geo = geoModel

            companyModel.bs = employee.company?.bs
            companyModel.name = employee.company?.name
            companyModel.catchPhrase = employee.company?.catchPhrase

            employeeModel.name = employee.name
            employeeModel.email = employee.email
            employeeModel.phone = employee.phone
            employeeModel.website = employee.website
            employeeModel.username = employee.username
            employeeModel.profile_image = employee.profile_image
            //employeeModel.company = companyModel
            //employeeModel.address = addressModel
            list.add(employeeModel)
        }
        return list;
    }

}