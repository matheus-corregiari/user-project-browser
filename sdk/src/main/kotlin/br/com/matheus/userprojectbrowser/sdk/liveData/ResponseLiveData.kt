package br.com.matheus.userprojectbrowser.sdk.liveData

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.WorkerThread
import br.com.matheus.userprojectbrowser.sdk.async
import br.com.matheus.userprojectbrowser.sdk.extension.*
import br.com.matheus.userprojectbrowser.sdk.model.DataResult
import br.com.matheus.userprojectbrowser.sdk.model.type.ERROR
import br.com.matheus.userprojectbrowser.sdk.model.type.LOADING
import br.com.matheus.userprojectbrowser.sdk.model.type.SUCCESS
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

abstract class ResponseLiveData<T> : LiveData<DataResult<T>>() {

    private val computing = AtomicBoolean(false)
    private val computed = AtomicBoolean(false)

    private val errorLiveData = MutableLiveData<Throwable>()

    private val loadingLiveData = MutableLiveData<Boolean>()

    fun observeSingle(owner: LifecycleOwner, observer: (data: DataResult<T>) -> Unit) = observeUntil(owner) {
        it.let(observer)
        it.resultStatus != LOADING
    }

    fun observeData(owner: LifecycleOwner, success: (data: T) -> Unit) = observe(owner) {
        when (it.resultStatus) {
            ERROR -> errorLiveData.value = it.error
            SUCCESS -> success(it.data ?: throw IllegalStateException("Data null! =("))
        }
        loadingLiveData.value = it.resultStatus == LOADING
    }

    fun observeLoading(owner: LifecycleOwner, loading: (loading: Boolean) -> Unit) = loadingLiveData.observe(owner, loading)

    fun observeError(owner: LifecycleOwner, error: (error: Throwable) -> Unit) = errorLiveData.observe(owner, error)

    fun observeSingleData(owner: LifecycleOwner, success: (data: T) -> Unit) = observeUntil(owner) {
        when (it.resultStatus) {
            ERROR -> errorLiveData.value = it.error
            SUCCESS -> success(it.data!!)
        }
        loadingLiveData.value = it.resultStatus == LOADING

        it.resultStatus != LOADING
    }

    fun observeSingleError(owner: LifecycleOwner, error: (error: Throwable) -> Unit) = errorLiveData.observeSingle(owner, error)

    fun observeSingleLoading(owner: LifecycleOwner, loading: (loading: Boolean) -> Unit) = loadingLiveData.observeSingle(owner, loading)

    fun getData() = value?.data

    fun getError() = value?.error

    fun getStatus() = value?.resultStatus

    fun getSTATUS() = value?.STATUS

    fun isRunning() = computing.get()

    fun isComputed() = computed.get()

    fun isRunningOrComputed() = computed.get() || computing.get()

    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData

    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

    @WorkerThread
    abstract fun compute()

    open fun invalidate() {
        if (!hasObservers() && computed.get()) computed.set(false)

        if (!computing.get() && hasObservers())
            async(this::executeRunnable)
    }

    override fun onActive() {
        super.onActive()
        if (!computed.get()) invalidate()
    }

    internal open fun setValue(value: T?) = postValue(value.toDataResponse(SUCCESS, getSTATUS()))

    private fun executeRunnable() = try {
        computing.set(true)
        compute()
        computed.set(true)
    } catch (error: Exception) {
        Timber.e(error)
        postValue(error.toErrorResponse())
        computed.set(false)
    } finally {
        computing.set(false)
    }

}