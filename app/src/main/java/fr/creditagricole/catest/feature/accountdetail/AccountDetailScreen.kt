package fr.creditagricole.catest.feature.accountdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import fr.creditagricole.catest.DateUtils
import fr.creditagricole.catest.R
import fr.creditagricole.catest.data.model.Account
import fr.creditagricole.catest.data.model.Operation
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    account: Account,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = account.label,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackButtonClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.Black
                        )
                    }
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
                item {
                    Text(
                        text = String.format("%.2f €", account.balance),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = TextUnit(24F, TextUnitType.Sp),
                        textAlign = TextAlign.Center
                    )
                }
                items(account.operations.sortedByDescending { operation -> operation.date }) { operation ->
                    OperationItem(operation = operation)
                }
            }

            if (account.operations.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_operation),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun OperationItem(
    operation: Operation
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = operation.title,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = DateUtils.formatDate(operation.date),
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_large))
                )
            }
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "${operation.amount} €",
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountDetailScreenPreview() {
    AccountDetailScreen(
        account = Account(
            1,
            "1",
            "Antoine Lapeyre",
            1,
            "123456",
            "Compte courant",
            "123",
            1234.56,
            listOf(
                Operation(
                    "1",
                    "Retrait au distributeur",
                    "30,00",
                    "Retrait",
                    Date(1709303935000)
                ),
                Operation(
                        "2",
                "Paiement en chèque",
                "53,84",
                "Chèque",
                Date(1709390335000)
            )
            )
        ),
        onBackButtonClick = {}
    )
}