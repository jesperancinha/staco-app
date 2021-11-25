package org.jesperancinha.enterprise.staco.common.dto

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType

data class StaCoDto(
    val description: String? = null,
    val year: String? = null,
    val value: String? = null,
    val currency: CurrencyType? = null,
    val diameterMM: String? = null,
    val internalDiameterMM: String? = null,
    val heightMM: String? = null,
    val widthMM: String? = null,
) {
    companion object {
        fun createCoin(
            description: String?,
            year: String?,
            value: String?,
            currency: CurrencyType,
            diameterMM: String?,
            internalDiameterMM: String?,
        ): StaCoDto {
            return StaCoDto(
                description = description,
                year = year,
                value = value,
                currency = currency,
                diameterMM = diameterMM,
                internalDiameterMM = internalDiameterMM,
                heightMM = null,
                widthMM = null
            )
        }

        fun createStamp(
            description: String?,
            year: String?,
            value: String?,
            currency: CurrencyType,
            heightMM: String?,
            widthMM: String?
        ): StaCoDto {
            return StaCoDto(
                description = description,
                year = year,
                value = value,
                currency = currency,
                diameterMM = null,
                internalDiameterMM = null,
                heightMM = heightMM,
                widthMM = widthMM
            )
        }
    }
}
