package br.com.matheus.userprojectbrowser.base.extension

import android.support.v4.app.FragmentTransaction
import br.com.matheus.userprojectbrowser.base.BaseFragment

fun FragmentTransaction.detachIfHasDifferentTag(fragment: BaseFragment, tag: String): Boolean {
    if (tag == fragment.tag) return false
    detach(fragment)
    return true
}