package com.austinaryain.fetchchallenge.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.austinaryain.fetchchallenge.R
import com.austinaryain.fetchchallenge.data.models.FetchItem
import com.austinaryain.fetchchallenge.databinding.MainFragmentBinding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ListScreen(mainViewModel = viewModel)
                }
            }
        }
    }

    @Composable
    fun ListScreen(mainViewModel: MainViewModel) {

        val refreshing by mainViewModel.isRefreshing.collectAsState()

        val state = mainViewModel.viewState.observeAsState()

        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { mainViewModel.loadData() }) {
            if (!refreshing) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    when (val viewState = state.value) {
                        is MainViewModel.ViewState.Empty -> Info(getString(viewState.resId))
                        is MainViewModel.ViewState.LoadFailed -> Info(
                            getString(viewState.resId),
                            true
                        )
                        is MainViewModel.ViewState.LoadSuccess -> List(viewState.data)
                        MainViewModel.ViewState.Loading -> {
                            Loading()
                        }
                        else -> {
                            Info(stringResource(R.string.something_went_wrong), true)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun List(
        grouped: Map<Int, List<FetchItem>>
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            grouped.forEach { (group, groupItems) ->
                stickyHeader {
                    GroupHeader(group)
                }

                items(groupItems) { item ->
                    GroupItem(item)
                    Divider()
                }
            }
        }
    }

    @Composable
    private fun GroupHeader(
        groupId: Int
    ) {
        Text(
            text = "Group: $groupId",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp),
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun GroupItem(
        item: FetchItem
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(12.dp),
            text = "${item.name}"
        )
    }

    @Composable
    private fun Info(
        message: String, isError: Boolean = false
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = if (isError) Color.Red else Color.Black,
            text = message,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }

    @Composable
    private fun Loading() {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        Text(stringResource(R.string.loading))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
