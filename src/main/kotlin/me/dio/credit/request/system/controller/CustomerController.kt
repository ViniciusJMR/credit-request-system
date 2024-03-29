package me.dio.credit.request.system.controller

import jakarta.validation.Valid
import me.dio.credit.request.system.dto.CustomerDTO
import me.dio.credit.request.system.dto.CustomerUpdateDTO
import me.dio.credit.request.system.dto.CustomerView
import me.dio.credit.request.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerService: CustomerService
){

    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDTO: CustomerDTO): ResponseEntity<CustomerView>{
        val savedCustomer = this.customerService.save(customerDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CustomerView(savedCustomer))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerView>{
        val customer = this.customerService.findById(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId")id: Long,
                       @RequestBody customerUpdateDTO: CustomerUpdateDTO
    ): ResponseEntity<CustomerView> {
        val customer = this.customerService.findById(id)
        val customerToUpdate = customerUpdateDTO.toEntity(customer)
        val customerUpdated = this.customerService.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK)
            .body(CustomerView(customerUpdated))
    }
}