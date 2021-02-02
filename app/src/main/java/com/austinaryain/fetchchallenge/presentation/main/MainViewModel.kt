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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FetchRepository) : ViewModel() {

    sealed class ViewState {
        object Loading : ViewState()
        data class LoadFailed(val resId: Int) : ViewState()
        data class LoadSucces(val data: Map<Int, List<FetchItem>>) : ViewState()
    }

    private val _viewState = MutableLiveData<ViewState>(ViewState.Loading)
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        loadData()
    }

    fun loadData() {
        _viewState.postValue(ViewState.Loading)
        viewModelScope.launch {
            repository.getData().fold(
                {
                    Log.e("LOAD_ERROR", it.localizedMessage.orEmpty())
                    _viewState.postValue(ViewState.LoadFailed(R.string.failed_to_load))
                },
                {
                    _viewState.postValue(ViewState.LoadSucces(it.data))
                }
            )
        }
    }
}
