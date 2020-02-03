package com.cricketpanchayat.models.home

import android.os.Parcel
import android.os.Parcelable

class Feed() : Parcelable {
    var id: Int = 0
    var lang_id: Int = 0
    lateinit var title: String
    lateinit var slug: String
    lateinit var short_description: String
    lateinit var description: String
    var tags: String = ""
    lateinit var article_image: String
    lateinit var author_name: String
    lateinit var author_email: String
    var category_slug: String = ""


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        lang_id = parcel.readInt()
        title = parcel.readString()!!
        slug = parcel.readString()!!
        short_description = parcel.readString()!!
        description = parcel.readString()!!
        tags = parcel.readString()!!
        article_image = parcel.readString()!!
        author_name = parcel.readString()!!
        author_email = parcel.readString()!!
        category_slug = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(lang_id)
        parcel.writeString(title)
        parcel.writeString(slug)
        parcel.writeString(short_description)
        parcel.writeString(description)
        parcel.writeString(tags)
        parcel.writeString(article_image)
        parcel.writeString(author_name)
        parcel.writeString(author_email)
        parcel.writeString(category_slug)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feed> {
        override fun createFromParcel(parcel: Parcel): Feed {
            return Feed(parcel)
        }

        override fun newArray(size: Int): Array<Feed?> {
            return arrayOfNulls(size)
        }
    }
}