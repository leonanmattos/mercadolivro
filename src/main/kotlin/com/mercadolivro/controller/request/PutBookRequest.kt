package com.mercadolivro.controller.request

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class PutBookRequest(
    @field:NotBlank(message = "Name is required")
    var name: String?,

    @field:NotNull(message = "Price is required")
    @field:Positive(message = "Price is required")
    var price: BigDecimal?
)
