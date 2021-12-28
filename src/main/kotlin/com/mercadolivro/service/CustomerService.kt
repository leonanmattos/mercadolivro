package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.ErrorEnum
import com.mercadolivro.enums.RoleEnum
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun findAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it);
        }
        return customerRepository.findAll().toList()
    }

    fun create(customer: CustomerModel) {
        val newCustomer = customer.copy(
            roles = setOf(RoleEnum.CUSTOMER),
            password = bCrypt.encode(customer.password),
        )
        customerRepository.save(newCustomer)
    }

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow { NotFoundException(ErrorEnum.ML201.message.format(id), ErrorEnum.ML201.code) }
    }

    fun update(customer: CustomerModel) {
        if (!customerRepository.existsById(customer.id!!)) {
            throw Exception()
        }
        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO
        customerRepository.save(customer)
    }

    fun emailIsAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }
}