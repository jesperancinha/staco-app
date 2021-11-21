package org.jesperancinha.enterprise.staco.common.domain

interface IStaCo {
    val id: Long?
    val description: String?
    var year: String?
    var value: String?
    var currency: CurrencyEnum?
    val diameterMM: String?
    val internalDiameterMM: String?
    val heightMM: String?
    val widthMM: String?
}