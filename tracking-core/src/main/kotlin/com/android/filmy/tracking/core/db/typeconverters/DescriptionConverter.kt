package com.android.filmy.tracking.core.db.typeconverters

import androidx.room.TypeConverter
import com.android.filmy.tracking.core.db.entities.SubjectDescription
import com.google.gson.Gson

class DescriptionConverter {

    @TypeConverter
    fun fromDescription(description: SubjectDescription?): String? {
        return Gson().toJson(description)
    }

    @TypeConverter
    fun toDescription(json: String?): SubjectDescription? {
        return Gson().fromJson(json, SubjectDescription::class.java)
    }
}