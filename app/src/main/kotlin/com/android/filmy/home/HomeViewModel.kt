package com.android.filmy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filmy.data.MovieRepository
import com.android.filmy.mvi.HomeAction
import com.android.filmy.mvi.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val movieRepository: MovieRepository): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
    private val actions = MutableSharedFlow<HomeAction>()

    init {
        viewModelScope.launch {
            fetchNowPlayingMovies()
        }
    }

    private suspend fun fetchNowPlayingMovies() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
            )
        }

        try {
            val movies = movieRepository.getNowPlayingMovies()
            _state.update {
                it.copy(
                    isLoading = false,
                    movies = movies,
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message,
                )
            }
        }
    }

}