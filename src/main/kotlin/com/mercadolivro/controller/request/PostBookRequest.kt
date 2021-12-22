package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostBookRequest(
    @field:NotBlank(message = "Name is required")
    var name: String,

    @field:NotNull(message = "Price is required")
    @field:Positive(message = "Price is required")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)