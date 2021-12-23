package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest(

    @JsonAlias("customer_id")
    @field:NotNull(message = "Customer is required")
    @field:Positive
    val customerId: Int,

    @JsonAlias("book_ids")
    @field:NotNull(message = "Books is required")
    val bookIds: Set<Int>
)
