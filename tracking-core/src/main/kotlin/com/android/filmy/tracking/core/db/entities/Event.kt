package com.android.filmy.tracking.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val subjectDescription: SubjectDescription,
    val environment: Environment,
)
