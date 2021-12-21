package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "customer")
class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", length = 255, nullable = false)
    var name: String,

    @Column(name = "email", length = 255, nullable = false, unique = true)
    var email: String,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus

)