package fr.creditagricole.catest.feature.accountdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.creditagricole.catest.data.model.Account
import fr.creditagricole.catest.ui.theme.CATestTheme

class AccountDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CATestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getSerializableExtra(ACCOUNT_EXTRA, Account::class.java)
                    } else {
                        intent.getSerializableExtra(ACCOUNT_EXTRA) as? Account
                    }?.let { account ->
                        AccountDetailScreen(
                            account = account,
                            onBackButtonClick = { finish() }
                        )
                    } ?: finish()
                }
            }
        }
    }

    companion object {
        private const val ACCOUNT_EXTRA = "account_extra"

        fun getIntent(context: Context, account: Account) =
            Intent(context, AccountDetailActivity::class.java).apply {
                putExtra(ACCOUNT_EXTRA, account)
            }
    }
}