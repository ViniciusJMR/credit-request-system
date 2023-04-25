package me.dio.credit.request.system.controller

import me.dio.credit.request.system.dto.CustomerDTO
import me.dio.credit.request.system.service.impl.CustomerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerService: CustomerService
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): String{
        val savedCustomer = customerService.save(customerDTO.toEntity())
        return "Customer  ${savedCustomer.email} saved!"
    }
}