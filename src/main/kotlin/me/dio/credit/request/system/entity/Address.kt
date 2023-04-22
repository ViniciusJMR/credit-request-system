package me.dio.credit.request.system.entity

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    var zipCode: String = "",
    var street: String = ""
)
