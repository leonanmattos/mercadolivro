package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.helper.buildBook
import com.mercadolivro.repository.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
internal class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    @Test
    @DisplayName("should create a book")
    fun shouldCreateABook() {

        val bookFake = buildBook()

        every { bookRepository.save(bookFake) } returns bookFake

        bookService.create(bookFake)

        verify(exactly = 1) { bookRepository.save(bookFake) }

    }

    @Test
    fun `should return all books`() {

        val pageable = PageRequest.of(0, 10)

        every { bookRepository.findAll(pageable) } returns PageImpl(listOf(buildBook()))

        val books = bookService.findAll(pageable)

        verify(exactly = 1) { bookRepository.findAll(pageable) }
        Assertions.assertEquals(1, books.totalElements)

    }

    @Test
    fun `should return all active books`() {

        val pageable = PageRequest.of(0, 10)

        every { bookRepository.findByStatus(pageable, BookStatus.ATIVO) } returns PageImpl(
            listOf(
                buildBook(),
                buildBook()
            )
        )

        val books = bookService.findActives(pageable)

        verify(exactly = 1) { bookRepository.findByStatus(pageable, BookStatus.ATIVO) }
        Assertions.assertTrue(books.content[0].status == BookStatus.ATIVO)

    }

}