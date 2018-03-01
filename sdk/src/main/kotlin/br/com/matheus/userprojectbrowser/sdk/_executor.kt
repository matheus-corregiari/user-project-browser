package br.com.matheus.userprojectbrowser.sdk

internal fun async(block: () -> Unit) {
    Thread(block).start()
}