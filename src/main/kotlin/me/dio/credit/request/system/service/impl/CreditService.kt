package me.dio.credit.request.system.service.impl

import me.dio.credit.request.system.entity.Credit
import me.dio.credit.request.system.repository.CreditRepository
import me.dio.credit.request.system.service.ICreditService
import java.util.*

class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService{
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit = this.creditRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("Credit Code")

        return if(credit.customer?.id == customerId) credit else throw RuntimeException("Contact admin")
    }
}