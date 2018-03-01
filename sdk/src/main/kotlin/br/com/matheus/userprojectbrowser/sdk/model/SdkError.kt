package br.com.matheus.userprojectbrowser.sdk.model

import android.os.Parcel
import br.com.matheus.userprojectbrowser.sdk.extension.KParcelable
import br.com.matheus.userprojectbrowser.sdk.extension.parcelableCreator
import com.google.gson.annotations.Expose

data class SdkError(
        @Expose val message: String,
        @Expose val statusCode: Long
) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::SdkError)
    }

    private constructor(source: Parcel) : this(
            source.readString(),
            source.readLong()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(message)
        writeLong(statusCode)
    }
}