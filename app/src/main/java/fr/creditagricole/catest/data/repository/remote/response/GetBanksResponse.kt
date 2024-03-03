package fr.creditagricole.catest.data.repository.remote.response

import com.squareup.moshi.Json

data class BankJson(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "isCA") val isCA: Int?,
    @field:Json(name = "accounts") val accounts: List<AccountJson>?
)

data class AccountJson(
    @field:Json(name = "order") val order: Int?,
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "holder") val holder: String?,
    @field:Json(name = "role") val role: Int?,
    @field:Json(name = "contract_number") val contractNumber: String?,
    @field:Json(name = "label") val label: String?,
    @field:Json(name = "product_code") val productCode: String?,
    @field:Json(name = "balance") val balance: Double?,
    @field:Json(name = "operations") val operations: List<OperationJson>?
)

data class OperationJson(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "amount") val amount: String?,
    @field:Json(name = "category") val category: String?,
    @field:Json(name = "date") val date: String?
)
