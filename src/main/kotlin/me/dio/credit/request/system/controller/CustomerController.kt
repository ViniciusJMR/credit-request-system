package me.dio.credit.request.system.controller

import me.dio.credit.request.system.dto.CustomerDTO
import me.dio.credit.request.system.dto.CustomerView
import me.dio.credit.request.system.service.impl.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerService: CustomerService
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): String{
        val savedCustomer = this.customerService.save(customerDTO.toEntity())
        return "Customer  ${savedCustomer.email} saved!"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CustomerView {
        val customer = this.customerService.findById(id)
        return CustomerView(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)
}