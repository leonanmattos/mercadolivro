package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.RoleEnum
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", length = 255, nullable = false)
    var name: String,

    @Column(name = "email", length = 255, nullable = false, unique = true)
    var email: String,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleEnum::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    var roles: Set<RoleEnum> = setOf()

)