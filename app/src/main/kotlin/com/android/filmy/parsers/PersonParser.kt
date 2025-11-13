package com.android.filmy.parsers

import com.filmy.tracking.TrackingParam
import com.android.filmy.model.ContentType
import com.android.filmy.model.PersonDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.Person
import javax.inject.Inject

class PersonParser @Inject constructor(): ContentParser<Person> {
    override fun parse(response: Person, contentType: ContentType): SegmentDataModel {
        return PersonDataModel(
            id = response.id.toString(),
            name = response.name,
            contentType = contentType,
            trackingParam = TrackingParam(
                id = "person_card:${response.id}",
                name = "person_card",
                role = "Component",
                metadata = mutableMapOf(
                    "person_id" to response.id.toString(),
                    "person_name" to response.name,
                    "gender" to response.gender.toString(),
                    "popularity" to response.popularity.toString(),
                    "knownForDepartment" to response.knownForDepartment.toString(),
                    "knownFor" to response.knownFor.toString(),
                )
            ),
            adult = response.adult,
            gender = response.gender,
            popularity = response.popularity,
            knownForDepartment = response.knownForDepartment.orEmpty(),
            profilePath = response.profilePath ?: "",
            knownFor = response.knownFor ?: emptyList(),
        )
    }
}