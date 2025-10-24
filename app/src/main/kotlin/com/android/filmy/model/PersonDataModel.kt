package com.android.filmy.model

import com.android.filmy.BuildConfig
import com.android.filmy.model.response.MediaContent

data class PersonDataModel(
    override val id: String,
    val name: String,
    val gender: Int,
    val adult: Boolean,
    val profilePath: String,
    val knownForDepartment: String,
    val popularity: Double,
    val knownFor: List<MediaContent>
): SegmentDataModel {
    val profileUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${profilePath}"
}
