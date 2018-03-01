package br.com.matheus.userprojectbrowser.base.delegate

import br.com.matheus.userprojectbrowser.base.BaseActivity
import br.com.matheus.userprojectbrowser.base.BaseFragment
import kotlin.reflect.KProperty

class ExtraProviderDelegate<out T>(private val extraName: String, private val defaultValue: T) {

    private var extra: T? = null

    operator fun getValue(thisRef: BaseActivity, property: KProperty<*>): T {
        extra = getExtra(extra, extraName, thisRef)
        return extra ?: defaultValue
    }

    operator fun getValue(thisRef: BaseFragment, property: KProperty<*>): T {
        extra = getExtra(extra, extraName, thisRef)
        return extra ?: defaultValue
    }

}

fun <T> extraProvider(extra: String, default: T) = ExtraProviderDelegate(extra, default)
fun extraProvider(extra: String) = extraProvider(extra, null)

@Suppress("UNCHECKED_CAST")
private fun <T> getExtra(oldExtra: T?, extraName: String, thisRef: BaseActivity): T? =
        oldExtra ?: thisRef.intent?.extras?.get(extraName) as T?

@Suppress("UNCHECKED_CAST")
private fun <T> getExtra(oldExtra: T?, extraName: String, thisRef: BaseFragment): T? =
        oldExtra ?: thisRef.arguments?.get(extraName) as T?