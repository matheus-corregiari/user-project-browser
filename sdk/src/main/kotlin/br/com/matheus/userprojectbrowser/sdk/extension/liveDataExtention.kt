@file:JvmName("LiveDataUtils")

package br.com.matheus.userprojectbrowser.sdk.extension

import android.arch.lifecycle.*
import br.com.matheus.userprojectbrowser.sdk.liveData.ResponseLiveData
import br.com.matheus.userprojectbrowser.sdk.model.DataResult
import br.com.matheus.userprojectbrowser.sdk.model.type.ERROR
import br.com.matheus.userprojectbrowser.sdk.model.type.LOADING
import br.com.matheus.userprojectbrowser.sdk.model.type.SUCCESS

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) = observe(owner, Observer { it?.let(observer) })

fun <T> LiveData<T>.observeSingle(owner: LifecycleOwner, observer: ((T) -> Unit)) = observeUntil(owner) {
    it.let(observer)
    true
}

fun <T> LiveData<T>.observeUntil(owner: LifecycleOwner, observer: ((T) -> Boolean)) {
    observe(owner, object : Observer<T> {
        override fun onChanged(data: T?) {
            if (data?.let(observer) == true) removeObserver(this)
        }
    })
}

fun <T> MediatorLiveData<T>.addSource(source: LiveData<T>) = addSource(source) {
    value = it
}

fun <T> MediatorLiveData<T>.addSources(vararg sources: LiveData<T>, observer: (T?) -> Unit) {
    sources.forEach {
        addSource(it, observer)
    }
}

fun <T> LiveData<T>.and(another: LiveData<T>, withResult: (fromThis: T, fromAnother: T) -> T = { fromThis: T, _: T -> fromThis }): LiveData<T> {
    val mediator = MediatorLiveData<T>()
    var firstResult: T? = null

    mediator.addSource(this) { value: T? ->
        value?.let {
            firstResult?.let {
                mediator.value = withResult.invoke(value, it)
                firstResult = null
            }
            if (firstResult == null) firstResult = it
        }
    }
    mediator.addSource(another) { value: T? ->
        value?.let {
            firstResult?.let {
                mediator.value = withResult.invoke(it, value)
                firstResult = null
            }
            if (firstResult == null) firstResult = it
        }
    }
    return mediator
}

infix operator fun <T> LiveData<T>.plus(liveData: LiveData<T>): LiveData<T> {
    val mediator = MediatorLiveData<T>()
    mediator.addSource(this)
    mediator.addSource(liveData)
    return mediator
}

infix fun <T> LiveData<T>.or(liveData: LiveData<T>): LiveData<T> {
    val mediator = MediatorLiveData<T>()
    mediator.addSource(this) {
        if (it == null) mediator.addSource(liveData)
        else mediator.value = it
    }
    return mediator
}

fun <T> LiveData<DataResult<T>>.toSimpleLiveData(): LiveData<T> {
    val newLiveData = MutableLiveData<T>()
    return Transformations.switchMap(this) {
        newLiveData.value = it.data
        newLiveData
    }
}

fun <T, R> ResponseLiveData<T>.map(transformation: (T) -> R) = object : ResponseLiveData<R>() {
    override fun compute() = Unit
    override fun invalidate() = this@map.invalidate()

    override fun observe(owner: LifecycleOwner, observer: Observer<DataResult<R>>) {
        super.observe(owner, observer)
        this@map.observe(owner) { result ->
            val newValue = when (result.resultStatus) {
                SUCCESS -> result.data?.let(transformation).toDataResponse(SUCCESS, result.STATUS)
                ERROR -> result.error?.toErrorResponse()
                LOADING -> loadingResponse()
                else -> null
            }
            if (value != newValue) postValue(newValue)
        }
    }

}

fun <T> ResponseLiveData<T>.whenDataReceived(action: (T) -> Unit) = object : ResponseLiveData<T>() {
    override fun compute() = Unit
    override fun invalidate() = this@whenDataReceived.invalidate()

    override fun observe(owner: LifecycleOwner, observer: Observer<DataResult<T>>) {
        super.observe(owner, observer)
        this@whenDataReceived.observe(owner) { result ->
            result.data?.let(action)
            value = result
        }
    }

}