package me.dio.credit.request.system.controller

import jakarta.validation.Valid
import me.dio.credit.request.system.dto.CreditDTO
import me.dio.credit.request.system.dto.CreditView
import me.dio.credit.request.system.dto.CreditViewList
import me.dio.credit.request.system.entity.Credit
import me.dio.credit.request.system.service.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/credit")
class CreditController(
    private val creditService: CreditService
){

    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDTO: CreditDTO): ResponseEntity<String>{
        val creditSaved = this.creditService.save(creditDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${creditSaved.creditCode} - Customer ${creditSaved.customer?.firstName} saved!")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewList>>{
        val creditViewList = this.creditService.findAllByCustomer(customerId).map{
            credit: Credit -> CreditViewList(credit)
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long,
                         @PathVariable creditCode: UUID
    ): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))

    }
}