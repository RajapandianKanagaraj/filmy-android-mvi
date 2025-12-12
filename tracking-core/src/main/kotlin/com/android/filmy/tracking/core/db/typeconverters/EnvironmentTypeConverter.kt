package com.android.filmy.tracking.core.db.typeconverters

import androidx.room.TypeConverter
import com.android.filmy.tracking.core.db.entities.Environment
import com.google.gson.Gson

class EnvironmentTypeConverter {

    @TypeConverter
    fun fromEnvironment(environment: Environment?): String? {
        return Gson().toJson(environment)
    }

    @TypeConverter
    fun toEnvironment(json: String?): Environment? {
        return Gson().fromJson(json, Environment::class.java)
    }
}