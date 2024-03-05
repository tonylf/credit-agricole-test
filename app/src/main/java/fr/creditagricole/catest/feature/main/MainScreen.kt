package fr.creditagricole.catest.feature.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import fr.creditagricole.catest.R
import fr.creditagricole.catest.data.model.Account
import fr.creditagricole.catest.data.model.Bank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isLoading: Boolean,
    banks: List<Bank>,
    errors: List<ErrorType>,
    onAccountClick: (Account) -> Unit,
    onDismissError: (ErrorType) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.my_accounts),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn {
                // Crédit Agricole
                item {
                    Text(
                        text = stringResource(id = R.string.credit_agricole),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
                items(banks.filter { bank -> bank.isCreditAgricole }) { bank ->
                    BankItem(
                        bank = bank,
                        onAccountClick = onAccountClick
                    )
                }

                // Other banks
                val otherBanks = banks.filter { bank -> !bank.isCreditAgricole }
                if (otherBanks.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.other_banks),
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                    items(otherBanks) { bank ->
                        BankItem(
                            bank = bank,
                            onAccountClick = onAccountClick
                        )
                    }
                }
            }

            // View helpers
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (banks.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_account),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
            if (errors.isNotEmpty()) {
                ErrorAlertDialog(error = errors[0], onDismissError)
            }
        }
    }
}

@Composable
fun BankItem(
    bank: Bank,
    onAccountClick: (Account) -> Unit
) {
    var isAccountsListOpened by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable { isAccountsListOpened = !isAccountsListOpened },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = bank.name,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = String.format(
                            "%.2f €",
                            bank.accounts.sumOf { account -> account.balance }),
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = if (isAccountsListOpened) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentDescription = stringResource(id = R.string.open_bank_accounts_list),
                        tint = Color.Gray
                    )
                }
            }

            if (isAccountsListOpened) {
                Column {
                    for (account in bank.accounts.sortedBy { account -> account.label }) {
                        AccountItem(
                            account = account,
                            onAccountClick = onAccountClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountItem(
    account: Account,
    onAccountClick: (Account) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable { onAccountClick(account) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = account.label,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = String.format("%.2f €", account.balance),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentDescription = stringResource(id = R.string.open_account_details),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ErrorAlertDialog(error: ErrorType, onDismissError: (ErrorType) -> Unit) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            onDismissError(error)
        },
        title = {
            Text(text = stringResource(id = R.string.error))
        },
        text = {
            Text(
                text = stringResource(id = R.string.get_accounts_error)
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismissError(error)
                }) {
                Text(stringResource(id = R.string.ok))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        isLoading = false,
        banks = listOf(
            Bank(
                "Banque Crédit Agricole",
                true,
                listOf(
                    Account(
                        1,
                        "1",
                        "Antoine Lapeyre",
                        1,
                        "123456",
                        "Compte courant",
                        "123",
                        1234.56,
                        emptyList()
                    ),
                    Account(
                        2,
                        "2",
                        "Antoine Lapeyre",
                        1,
                        "123457",
                        "Livet A",
                        "124",
                        22950.00,
                        emptyList()
                    )
                )
            ),
            Bank(
                "BNP Paribas",
                false,
                listOf(
                    Account(
                        1,
                        "1",
                        "Antoine Lapeyre",
                        1,
                        "654321",
                        "Compte courant",
                        "321",
                        4321.65,
                        emptyList()
                    ),
                    Account(
                        2,
                        "2",
                        "Antoine Lapeyre",
                        1,
                        "754321",
                        "Livet A",
                        "421",
                        10000.00,
                        emptyList()
                    )
                )
            )
        ),
        errors = emptyList(),
        onAccountClick = {},
        onDismissError = {}
    )
}