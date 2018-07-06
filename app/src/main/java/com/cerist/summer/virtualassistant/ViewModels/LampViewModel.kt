package com.cerist.summer.virtualassistant.ViewModels

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.cerist.summer.virtualassistant.Entities.LampProfile
import com.cerist.summer.virtualassistant.Utils.toLiveData
import com.cerist.summer.virtualassistant.Repositories.LampRepository
import com.cerist.summer.virtualassistant.Utils.Status
import io.reactivex.disposables.CompositeDisposable

class LampViewModel(val lampRepository:LampRepository):ViewModel(){

    val lampBleConnectionState by lazy {
        lampRepository.getLampConnectionState().toLiveData()
    }

    val compositeDisposable:CompositeDisposable= CompositeDisposable()
    fun getLampLumonisitiyLevelLiveData() = lampRepository.getLampLuminosityLevel().toLiveData()
    fun getLampLightningStateLiveData() =lampRepository.getLampLightningState().toLiveData()


    fun setLampLightningState(state:LampProfile.LAMP_STATE){

       val disposable =lampRepository.setLampLightningState(state)
                .subscribe{
                    if(it.status == Status.SUCCESS)
                        Log.d("LampViewModel","state: ${it.data?.name}")
                    else
                        Log.d("LampViewModel","state: ${it.message}")
                }

        compositeDisposable.addAll(disposable)
    }
    fun setLampLumonisitiyLevel(level: LampProfile.LAMP_LUMINOSITY){

        val disposable = lampRepository.getLampLuminosityLevel(level)
                .subscribe{
                    if(it.status == Status.SUCCESS)
                        Log.d("LampViewModel","state: ${it.data?.name}")
                    else
                        Log.d("LampViewModel","state: ${it.message}")
                }
            compositeDisposable.addAll(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }






}