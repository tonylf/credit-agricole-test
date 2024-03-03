package fr.creditagricole.catest.feature.main

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.creditagricole.catest.data.model.Bank
import fr.creditagricole.catest.domain.GetBanksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getBanksUseCase: GetBanksUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        getBanks()
    }

    fun setErrorHandled(error: ErrorType) {
        _uiState.update {
            val errors = it.errors.toMutableList()
            errors.remove(error)
            it.copy(errors = errors.toMutableStateList())
        }
    }

    private fun getBanks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val result = getBanksUseCase()
            when {
                result.isSuccess -> {
                    result.getOrNull()?.let { banks ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                banks = banks.toMutableStateList()
                            )
                        }
                    }
                }

                result.isFailure -> {
                    _uiState.update {
                        val errors = it.errors.toMutableList()
                        errors.add(ErrorType.GetBanksError)
                        it.copy(
                            isLoading = false,
                            errors = errors
                        )
                    }
                }
            }
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val errors: List<ErrorType> = emptyList(),
    val banks: List<Bank> = emptyList()
)

sealed class ErrorType {
    object GetBanksError : ErrorType()
}