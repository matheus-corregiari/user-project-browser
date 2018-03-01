package br.com.matheus.userprojectbrowser.sdk.data.remote.factory

import br.com.matheus.userprojectbrowser.sdk.data.remote.gson
import br.com.matheus.userprojectbrowser.sdk.extension.loadingResponse
import br.com.matheus.userprojectbrowser.sdk.extension.makeRequest
import br.com.matheus.userprojectbrowser.sdk.extension.toDataResponse
import br.com.matheus.userprojectbrowser.sdk.liveData.ResponseLiveData
import br.com.matheus.userprojectbrowser.sdk.model.type.SUCCESS
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != ResponseLiveData::class.java) return null

        val type = getParameterUpperBound(0, returnType as ParameterizedType)
        return LiveDataCallAdapter<Any>(type)
    }
}

internal class LiveDataCallAdapter<RESULT>(private val responseType: Type) : CallAdapter<JsonObject, ResponseLiveData<RESULT>> {

    override fun responseType() = JsonObject::class.java.genericSuperclass!!

    override fun adapt(call: Call<JsonObject>) = object : ResponseLiveData<RESULT>() {
        override fun compute() {
            postValue(loadingResponse())
            val jsonResult = call.makeRequest()
            val status = jsonResult["STATUS"].asString
            val data = gson.fromJson<RESULT>(jsonResult[jsonResult.keySet().findLast { it != "STATUS" }].toString(), responseType)
            postValue(data.toDataResponse(SUCCESS, status))
        }
    }
}