package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class PostCustomerRequest(
    @field:NotBlank(message = "Name is required")
    var name: String,

    @field:Email(message = "Email is invalid")
    @EmailAvailable
    var email: String
)