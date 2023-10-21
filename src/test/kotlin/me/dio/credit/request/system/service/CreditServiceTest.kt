package me.dio.credit.request.system.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.credit.request.system.entity.Credit
import me.dio.credit.request.system.entity.Customer
import me.dio.credit.request.system.enummeration.Status
import me.dio.credit.request.system.repository.CreditRepository
import me.dio.credit.request.system.repository.CustomerRepository
import me.dio.credit.request.system.service.impl.CreditService
import me.dio.credit.request.system.service.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository
    @MockK
    private lateinit var creditRepository: CreditRepository
//    @InjectMockKs
    private lateinit var customerService: CustomerService
//    @InjectMockKs
    private lateinit var creditService: CreditService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
//        customerRepository = mock(CustomerRepository::class.java)
        customerService = CustomerService(customerRepository)
//        creditRepository = mock(CreditRepository::class.java)
        creditService = CreditService(creditRepository, customerService)
    }

//    @AfterEach
//    fun tearDown(){
//        customerRepository.deleteAll()
//        creditRepository.deleteAll()
//    }

    @Test
    fun `should create credit`(){
        //given
        val fakeCustomer = CustomerServiceTest.buildCustomer()
        every { customerRepository.save(any()) } returns fakeCustomer
        val actualCustomer = customerRepository.save(fakeCustomer)
        every { customerRepository.findById(any()) } returns Optional.of(fakeCustomer)

        val fakeCredit = buildCredit(customer = actualCustomer)
        every { creditRepository.save(any())} returns fakeCredit

        //when
        val actualCredit = creditService.save(fakeCredit)

        //then
        Assertions.assertThat(actualCredit).isNotNull
        Assertions.assertThat(actualCredit).isSameAs(fakeCredit)
        Assertions.assertThat(actualCredit.customer).isSameAs(actualCustomer)
        verify(exactly = 1){customerRepository.save(fakeCustomer)}
    }

    private fun buildCredit(
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal.valueOf(500L),
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 3,
        status: Status = Status.IN_PROGRESS,
        customer: Customer? = CustomerServiceTest.buildCustomer()
    ) = Credit(
        creditCode = creditCode,
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        status = status,
        customer = customer
    )
}