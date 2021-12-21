package com.mercadolivro.controller.response

data class CustomerResponse (
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: String
)
