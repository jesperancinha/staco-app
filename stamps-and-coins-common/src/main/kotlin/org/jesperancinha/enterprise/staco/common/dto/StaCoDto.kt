package org.jesperancinha.enterprise.staco.common.dto

import org.jesperancinha.enterprise.staco.common.domain.CurrencyEnum

data class StaCoDto(
    val description: String?,
    val year: String?,
    val value: String?,
    val currency: CurrencyEnum?,
    val diameterMM: String?,
    val internalDiameterMM: String?,
    val heightMM: String?,
    val widthMM: String?,
) {

    companion object {
        fun createCoin(
            description: String?,
            year: String?,
            value: String?,
            currency: CurrencyEnum,
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
            currency: CurrencyEnum,
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
