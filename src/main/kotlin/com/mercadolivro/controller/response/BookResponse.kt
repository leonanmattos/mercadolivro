package com.mercadolivro.controller.response

import java.math.BigDecimal

data class BookResponse(
    val id: Int? = null,
    val name: String,
    val price: BigDecimal,
    val status: String? = null,
    val customer: CustomerResponse? = null
)
