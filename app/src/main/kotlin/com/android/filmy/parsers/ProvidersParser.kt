package com.android.filmy.parsers

import com.android.filmy.model.ProviderDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.Provider
import javax.inject.Inject

class ProvidersParser @Inject constructor(): ContentParser<Provider> {
    override fun parse(response: Provider): SegmentDataModel {
        return ProviderDataModel(
            id = response.id.toString(),
            providerName = response.providerName,
            logoPath = response.logoPath,
            logoUrl = response.logoUrl,
            displayPriority = response.displayPriority,
        )
    }
}