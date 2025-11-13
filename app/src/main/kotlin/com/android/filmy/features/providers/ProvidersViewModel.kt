package com.android.filmy.features.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmy.tracking.TrackingParam
import com.android.filmy.core.SegmentLCEState
import com.android.filmy.core.SegmentRepository
import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.ProviderDataModel
import com.android.filmy.mvi.ActionDispatcherImpl
import com.android.filmy.mvi.ProviderAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val segmentRepository: SegmentRepository,
    private val actionDispatcher: ActionDispatcherImpl,
) : ViewModel() {

    private val _providerState = MutableStateFlow(SegmentLCEState.Idle)
    val providerState: StateFlow<SegmentLCEState> = _providerState.asStateFlow()

    private val _contentState = MutableStateFlow<List<SegmentLCEState>>(emptyList())
    val contentState: StateFlow<List<SegmentLCEState>> = _contentState.asStateFlow()
    private val _selectedProvider = MutableStateFlow<ProviderDataModel?>(null)

    init {
        observeProviderActions()
        observeSelectedProvider()
        viewModelScope.launch {
            fetchProviders()
        }
    }

    private fun observeProviderActions() {
        viewModelScope.launch {
            actionDispatcher.actions
                .filterIsInstance<ProviderAction.OnProviderSelected>()
                .collectLatest { action ->
                    _selectedProvider.value = action.provider
                }
        }
    }

    private fun observeSelectedProvider() {
        viewModelScope.launch {
            _selectedProvider
                .filterNotNull()
                .collect { providerState ->
                    onProviderUpdated(providerState)
                }
        }
    }


    suspend fun fetchProviders() {
        segmentRepository.getSegment(ProvidersSegments.providerSegment).collect { state ->
            _providerState.value = state

            if (state.data != null && _selectedProvider.value == null) {
                val firstProvider = (state.data as? CollectionDataModel)
                    ?.feeds
                    ?.filterIsInstance<ProviderDataModel>()
                    ?.first()
                firstProvider?.let { provider ->
                    _selectedProvider.value = provider
                }
            }
        }
    }

    private suspend fun onProviderUpdated(provider: ProviderDataModel) {
        _contentState.value = emptyList()
        segmentRepository.getContentSegments(
            segments = ProvidersSegments.getContentSegments(
                watchProviderId = provider.id,
                watchProviderName = provider.providerName,
            )
        )
        viewModelScope.launch {
            segmentRepository.segmentState.collect { state ->
                _contentState.value = state
            }
        }
    }

    fun getTrackingSubject() = TrackingParam(
        id = "providers_screen:${UUID.randomUUID()}",
        name = "providers_screen",
        role = "screen",
        metadata = mapOf("screen_name" to "ProvidersScreen"),
    )
}