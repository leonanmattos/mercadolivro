package com.mercadolivro.controller.request

import com.mercadolivro.validation.interfaces.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class PostCustomerRequest(
    @field:NotBlank(message = "Name is required")
    var name: String,

    @field:Email(message = "Email is invalid")
    @EmailAvailable
    var email: String,

    @field:NotEmpty(message = "Password is required")
    var password: String
)