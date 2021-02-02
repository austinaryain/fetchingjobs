package com.austinaryain.fetchchallenge.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.austinaryain.fetchchallenge.R
import com.austinaryain.fetchchallenge.databinding.MainFragmentBinding
import com.austinaryain.fetchchallenge.presentation.models.header
import com.austinaryain.fetchchallenge.presentation.models.item
import com.google.android.material.snackbar.Snackbar
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.refresh.setOnRefreshListener {
            viewModel.loadData()
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            binding.refresh.isRefreshing = false
            when (state) {
                is MainViewModel.ViewState.Loading -> {
                    binding.loader.show()
                }
                is MainViewModel.ViewState.LoadFailed -> {
                    binding.loader.hide()
                    Snackbar.make(requireView(), state.resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry) {
                        viewModel.loadData()
                    }.show()
                }
                is MainViewModel.ViewState.LoadSucces -> {
                    binding.loader.hide()

                    binding.recycler.withModels {
                        state.data.forEach {
                            header {
                                id(it.key)
                                text(it.key.toString())
                            }
                            it.value.forEach { fetchItem ->
                                item {
                                    id(fetchItem.id)
                                    idText(fetchItem.id.toString())
                                    nameText(fetchItem.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
