package br.com.matheus.userprojectbrowser.sdk.model.domain

import android.os.Parcel
import br.com.matheus.userprojectbrowser.sdk.extension.KParcelable
import br.com.matheus.userprojectbrowser.sdk.extension.parcelableCreator
import br.com.matheus.userprojectbrowser.sdk.extension.readTypedObjectCompat
import br.com.matheus.userprojectbrowser.sdk.extension.writeTypedObjectCompat
import br.com.matheus.userprojectbrowser.sdk.model.DateType

data class ProjectVO(
        val id: String,
        val name: String,
        val logo: String,
        val description: String,
        val createdOn: DateType,
        val lastChangedOn: DateType
) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ProjectVO)
    }

    private constructor(parcel: Parcel) : this(
            id = parcel.readString(),
            name = parcel.readString(),
            logo = parcel.readString(),
            description = parcel.readString(),
            createdOn = parcel.readTypedObjectCompat(DateType.CREATOR)!!,
            lastChangedOn = parcel.readTypedObjectCompat(DateType.CREATOR)!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(logo)
        writeString(description)
        writeTypedObjectCompat(createdOn, flags)
        writeTypedObjectCompat(lastChangedOn, flags)
    }
}