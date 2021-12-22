package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.ErrorEnum
import com.mercadolivro.exception.BadRequestException
import java.awt.print.Book
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", length = 255, nullable = false)
    var name: String,

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
) {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELADO || field == BookStatus.DELETADO) {
                throw BadRequestException(ErrorEnum.ML102.message.format(field), ErrorEnum.ML102.code)
            }
            field = value
        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookStatus?
    ) : this(id, name, price, customer) {
        this.status = status
    }
}