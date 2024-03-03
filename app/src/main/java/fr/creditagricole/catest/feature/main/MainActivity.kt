package fr.creditagricole.catest.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import fr.creditagricole.catest.data.model.Account
import fr.creditagricole.catest.feature.accountdetail.AccountDetailActivity
import fr.creditagricole.catest.ui.theme.CATestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CATestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsState()

                    MainScreen(
                        isLoading = uiState.isLoading,
                        banks = uiState.banks,
                        errors = uiState.errors,
                        onAccountClick = { account -> openAccountDetail(account) },
                        onDismissError = { error -> setErrorHandled(error) }
                    )
                }
            }
        }
    }

    private fun openAccountDetail(account: Account) {
        startActivity(AccountDetailActivity.getIntent(this, account))
    }

    private fun setErrorHandled(error: ErrorType) {
        viewModel.setErrorHandled(error)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CATestTheme {
        MainScreen(
            isLoading = false,
            banks = emptyList(),
            errors = emptyList(),
            onAccountClick = {},
            onDismissError = {}
        )
    }
}