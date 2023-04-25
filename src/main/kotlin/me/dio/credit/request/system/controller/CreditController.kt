package me.dio.credit.request.system.controller

import me.dio.credit.request.system.dto.CreditDTO
import me.dio.credit.request.system.service.impl.CreditService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}