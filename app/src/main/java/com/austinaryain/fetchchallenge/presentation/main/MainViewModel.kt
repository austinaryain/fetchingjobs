package com.austinaryain.fetchchallenge.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austinaryain.fetchchallenge.R
import com.austinaryain.fetchchallenge.data.models.FetchItem
import com.austinaryain.fetchchallenge.domain.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FetchRepository) : ViewModel() {

    sealed class ViewState {
        object Loading : ViewState()
        data class LoadFailed(val resId: Int) : ViewState()
        data class LoadSuccess(val data: Map<Int, List<FetchItem>>) : ViewState()
        data class Empty(val resId: Int) : ViewState()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState>
        get() = _viewState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _viewState.tryEmit(ViewState.Loading)
        viewModelScope.launch {
            repository.getData().fold(
                {
                    Log.e("LOAD_ERROR", it.localizedMessage.orEmpty())
                    _viewState.emit(ViewState.LoadFailed(R.string.failed_to_load))
                },
                {
                    _viewState.emit(
                        when (it.data.isEmpty()) {
                            true -> ViewState.Empty(R.string.no_results)
                            false -> ViewState.LoadSuccess(processResponse(it.data))
                        }
                    )
                }
            )
        }
    }

    private fun processResponse(data: List<FetchItem>): Map<Int, List<FetchItem>> {
        return data.sortedBy { it.id }
            .groupBy { it.listId }.toSortedMap()
    }
}
