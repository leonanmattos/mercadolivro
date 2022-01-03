package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.helper.buildBook
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.awt.print.Book
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
internal class PurchaseServiceTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    private val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        Assertions.assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should return exception when book is not active`() {

        val book1 = buildBook(id = Random().nextInt(), status = BookStatus.CANCELADO)
        val book2 = buildBook(id = Random().nextInt(), status = BookStatus.DELETADO)
        val book3 = buildBook(id = Random().nextInt(), status = BookStatus.VENDIDO)
        val book4 = buildBook(status = BookStatus.VENDIDO)
        val book5 = buildBook(id = Random().nextInt())

        val books = mutableListOf<BookModel>(
            book1, book2, book3, book4, book5
        )
        val purchase = buildPurchase(books = books)

        val error = assertThrows<NotFoundException> { purchaseService.create(purchase) }

        Assertions.assertEquals("ML-103", error.errorCode)
    }

    @Test
    fun `should update purchase`() {
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase

        purchaseService.update(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }

    }

}