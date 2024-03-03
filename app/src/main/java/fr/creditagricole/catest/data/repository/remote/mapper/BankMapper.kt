package fr.creditagricole.catest.data.repository.remote.mapper

import fr.creditagricole.catest.data.model.Account
import fr.creditagricole.catest.data.model.Bank
import fr.creditagricole.catest.data.model.Operation
import fr.creditagricole.catest.data.repository.remote.response.AccountJson
import fr.creditagricole.catest.data.repository.remote.response.BankJson
import fr.creditagricole.catest.data.repository.remote.response.OperationJson
import java.util.Date

object BankMapper {

    fun transform(response: List<BankJson>?): List<Bank> =
        response?.mapNotNull { bankJson ->
            if (!bankJson.name.isNullOrEmpty()
                && bankJson.isCA != null
                && bankJson.accounts != null
            ) {
                Bank(
                    bankJson.name,
                    bankJson.isCA == 1,
                    transformAccounts(bankJson.accounts)
                )
            } else {
                null
            }
        } ?: emptyList()

    private fun transformAccounts(accountsJson: List<AccountJson>): List<Account> =
        accountsJson.mapNotNull { accountJson ->
            transformAccount(accountJson)
        }

    private fun transformAccount(accountJson: AccountJson?): Account? =
        accountJson?.let { json ->
            if (json.order != null
                && !json.id.isNullOrEmpty()
                && !json.holder.isNullOrEmpty()
                && json.role != null
                && !json.contractNumber.isNullOrEmpty()
                && !json.label.isNullOrEmpty()
                && !json.productCode.isNullOrEmpty()
                && json.balance != null
                && json.operations != null
            ) {
                Account(
                    json.order,
                    json.id,
                    json.holder,
                    json.role,
                    json.contractNumber,
                    json.label,
                    json.productCode,
                    json.balance,
                    transformOperations(json.operations)
                )
            } else {
                null
            }
        }

    private fun transformOperations(operationsJson: List<OperationJson>): List<Operation> =
        operationsJson.mapNotNull { operationJson ->
            transformOperation(operationJson)
        }

    private fun transformOperation(operationJson: OperationJson?): Operation? =
        operationJson?.let { json ->
            val date = json.date?.toLongOrNull()?.let { Date(it) }

            if (!json.id.isNullOrEmpty()
                && !json.title.isNullOrEmpty()
                && !json.amount.isNullOrEmpty()
                && !json.category.isNullOrEmpty()
                && date != null
            ) {
                Operation(
                    json.id,
                    json.title,
                    json.amount,
                    json.category,
                    date
                )
            } else {
                null
            }
        }
}