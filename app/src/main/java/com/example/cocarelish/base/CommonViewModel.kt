package com.example.cocarelish.base

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.utils.base.CommonItemMenuAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class CommonViewModel(application: Application): AndroidViewModel(application), CommonItemMenuAction {

    protected val TAG by lazy { this::class.simpleName}
    protected val evenSender = Channel<CommonEvent>()
    val eventReceiver = evenSender.receiveAsFlow().conflate()

    var eventLoading = MutableLiveData<Event<Boolean>>()
        private set
    var eventError = MutableLiveData<Event<Boolean>>()
        private set

    fun showLoading(value: Boolean){
        eventLoading.value = Event(value)
    }

    fun showError(errorString: Boolean){
        eventError.value = Event(errorString)
    }

    override fun onClickClose(){
        viewModelScope.launch {
            evenSender.send(CommonEvent.OnCloseApp)
        }
    }

    override fun onBackStack() {
        viewModelScope.launch {
            evenSender.send(CommonEvent.OnBackScreen)
        }
    }

    fun getString(stringID: Int) = getApplication<Application>().getString(stringID)

    fun showToast(stringID: Int){
        viewModelScope.launch {
            evenSender.send(CommonEvent.OnShowToast(getString(stringID)))
        }
    }

    fun showLoadingDialog(isShowing: Boolean){
        viewModelScope.launch {
            evenSender.send(CommonEvent.OnShowLoadingDialog(isShowing))
        }
    }

    fun navigate(destination: Int, bundle: Bundle? = null){
        viewModelScope.launch {
            evenSender.send(CommonEvent.OnNavigation(destination, bundle))
        }
    }
}

sealed class CommonEvent{
    class OnNavigation(val destination: Int, val bundle: Bundle? = null) : CommonEvent()
    class OnOpenAnotherApp(val packageName: String) : CommonEvent()
    object OnCloseApp : CommonEvent()
    object OnBackScreen : CommonEvent()
    class OnShowToast(val content: String, val type: Int = Toast.LENGTH_SHORT) : CommonEvent()
    class OnShowLoadingDialog(val isShowing: Boolean) : CommonEvent()
}
