package com.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class PutCustomerRequest(
    @field:NotBlank(message = "Name is required")
    var name: String,
    @field:Email(message = "Email is invalid")
    var email: String
)