package com.android.filmy.parsers

import com.android.filmy.model.PersonDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.Person
import javax.inject.Inject

class PersonParser @Inject constructor(): ContentParser<Person> {
    override fun parse(response: Person): SegmentDataModel {
        return PersonDataModel(
            id = response.id.toString(),
            name = response.name,
            adult = response.adult,
            gender = response.gender,
            popularity = response.popularity,
            knownForDepartment = response.knownForDepartment.orEmpty(),
            profilePath = response.profilePath ?: "",
            knownFor = response.knownFor ?: emptyList(),
        )
    }
}