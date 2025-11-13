package com.android.filmy.model

import com.android.filmy.BuildConfig
import com.android.filmy.model.response.MediaContent
import com.filmy.tracking.TrackingParam

data class PersonDataModel(
    override val id: String,
    override val contentType: ContentType = ContentType.PERSON,
    override val trackingParam: TrackingParam = TrackingParam.Empty,
    val name: String,
    val gender: Int,
    val adult: Boolean,
    val profilePath: String,
    val knownForDepartment: String,
    val popularity: Double,
    val knownFor: List<MediaContent>
): SegmentDataModel, Trackable {
    val profileUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${profilePath}"
}
