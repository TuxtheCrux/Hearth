package com.hsharz.redline.feature.passwords.data

import androidx.room.TypeConverter

class WebOrAppTypeConverter {
    @TypeConverter
    fun entryTypeToString(entryType: EntryType): String {
        return if (entryType == EntryType.APP) {
            "App"
        } else {
            "Website"
        }
    }

    @TypeConverter
    fun fromString(webOrApp: String): EntryType {
        return if (webOrApp == "App") {
            EntryType.APP
        } else {
            EntryType.WEBSITE
        }
    }
}