package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.ErrorEnum
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun create(purchase: PurchaseModel) {

        val idsNotAvailable = mutableListOf<Int>()
        purchase.books.map { book ->
            if (book.status != BookStatus.ATIVO) {
                book.id?.let { idsNotAvailable.add(it) }
            }
        }

        if(idsNotAvailable.isNotEmpty()) {
            throw NotFoundException(ErrorEnum.ML103.message.format(idsNotAvailable.joinToString(", ")), ErrorEnum.ML103.code)
        }

        purchaseRepository.save(purchase)

        applicationEventPublisher.publishEvent(
            PurchaseEvent(
                source = this,
                purchaseModel = purchase
            )
        )
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

}
