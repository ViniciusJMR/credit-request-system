package me.dio.credit.request.system.controller

import me.dio.credit.request.system.dto.CreditDTO
import me.dio.credit.request.system.dto.CreditViewList
import me.dio.credit.request.system.entity.Credit
import me.dio.credit.request.system.service.impl.CreditService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/credit")
class CreditController(
    private val creditService: CreditService
){

    @PostMapping
    fun saveCredit(@RequestBody creditDTO: CreditDTO): String{
        val creditSaved = this.creditService.save(creditDTO.toEntity())
        return "Credit ${creditSaved.creditCode} - Customer ${creditSaved.customer?.firstName} saved!"
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): List<CreditViewList>{
        return this.creditService.findAllByCustomer(customerId).map{
            credit: Credit -> CreditViewList(credit)
        }
    }
}