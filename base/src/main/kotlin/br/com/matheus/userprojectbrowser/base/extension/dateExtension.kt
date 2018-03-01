package br.com.matheus.userprojectbrowser.base.extension

import br.com.matheus.userprojectbrowser.sdk.model.DateType
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

fun DateType.fullDate(): String {
    return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault()))
}