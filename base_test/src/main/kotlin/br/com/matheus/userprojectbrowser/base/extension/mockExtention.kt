package br.com.matheus.userprojectbrowser.base.extension

import br.com.matheus.userprojectbrowser.sdk.liveData.ResponseLiveData
import br.com.matheus.userprojectbrowser.sdk.model.DataResult
import br.com.matheus.userprojectbrowser.sdk.model.type.ERROR
import br.com.matheus.userprojectbrowser.sdk.model.type.LOADING
import br.com.matheus.userprojectbrowser.sdk.model.type.SUCCESS
import com.nhaarman.mockito_kotlin.whenever
import net.vidageek.mirror.dsl.Mirror
import org.mockito.Mockito
import java.lang.reflect.*
import java.lang.reflect.Array

fun <T> mockRepository(aClass: Class<T>): T {
    val mockedRepository = Mockito.mock(aClass) { invocation ->
        if (invocation.method.returnType != ResponseLiveData::class.java) invocation.callRealMethod()
        else {
            val parameterizedType = invocation.method.genericReturnType as ParameterizedType
            typedResponseLiveData(getRawType(parameterizedType.actualTypeArguments[0]))
        }
    }
    Mirror().on(aClass).set().field("INSTANCE").withValue(mockedRepository)
    return mockedRepository
}

fun <T> ResponseLiveData<T>?.mockCreation(value: T): T {
    return mockCreation(DataResult(value, null, SUCCESS, "SUCCESS")).data!!
}

fun <T> ResponseLiveData<T>?.mockCreation(value: DataResult<T>): DataResult<T> {
    val liveData: ResponseLiveData<T> = object : ResponseLiveData<T>() {
        override fun compute() = Unit
    }
    whenever(this).thenReturn(liveData)
    Mirror().on(liveData).invoke().method("postValue").withArgs(value)
    return value
}

fun <T> ResponseLiveData<T>?.mockValue(value: T) = mockResponse(DataResult(value, null, SUCCESS, "SUCCESS")).data!!

fun <T> ResponseLiveData<T>?.mockLoading() = mockResponse(DataResult(null, null, LOADING, "LOADING"))
fun <T> ResponseLiveData<T>?.mockError(error: Throwable) = mockResponse(DataResult(null, error, ERROR, "ERROR")).error!!
fun <T> ResponseLiveData<T>?.mockResponse(value: DataResult<T>): DataResult<T> {
    Mirror().on(this).invoke().method("postValue").withArgs(value)
    return value
}

@Suppress("UNUSED_PARAMETER")
private fun <T> typedResponseLiveData(aClass: Class<T>): ResponseLiveData<T> {
    return object : ResponseLiveData<T>() {
        override fun compute() = Unit
    }
}

private fun getRawType(type: Type): Class<*> = when (type) {
    is Class<*> -> type
    is ParameterizedType -> type.rawType as? Class<*> ?: throw IllegalArgumentException()
    is GenericArrayType -> {
        val componentType = type.genericComponentType
        Array.newInstance(getRawType(componentType), 0).javaClass
    }
    is TypeVariable<*> -> Any::class.java
    is WildcardType -> getRawType(type.upperBounds[0])
    else -> throw IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <$type> is of type ${type.javaClass.name}")
}
