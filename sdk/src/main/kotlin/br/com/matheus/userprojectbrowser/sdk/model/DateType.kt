package br.com.matheus.userprojectbrowser.sdk.model

import android.os.Parcel
import br.com.matheus.userprojectbrowser.sdk.data.remote.adapter.DateTypeAdapter
import br.com.matheus.userprojectbrowser.sdk.extension.KParcelable
import br.com.matheus.userprojectbrowser.sdk.extension.parcelableCreator
import com.google.gson.annotations.Expose
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

data class DateType(@Expose val localDateTime: LocalDateTime = LocalDateTime.now()) : KParcelable {

    private constructor(source: Parcel) : this(source.readSerializable() as LocalDateTime)

    internal val apiFormat: String
        get() = ZonedDateTime.of(localDateTime, ZoneOffset.UTC).format(DateTypeAdapter.API_FORMAT)


    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeSerializable(localDateTime)
    }

    companion object {
        val CREATOR = parcelableCreator(::DateType)
    }
}
