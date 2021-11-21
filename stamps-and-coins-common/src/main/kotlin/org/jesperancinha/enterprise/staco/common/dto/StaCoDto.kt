package org.jesperancinha.enterprise.staco.common.dto

import org.jesperancinha.enterprise.staco.common.domain.CurrencyEnum
import org.jesperancinha.enterprise.staco.common.domain.StaCo

data class StaCoDto(
    val description: String?,
    val year: String?,
    val value: String?,
    val currency: String,
    val diameterMM: String?,
    val internalDiameterMM: String?,
    val heightMM: String?,
    val widthMM: String?,
) {

    constructor(staCo: StaCo) : this(
        staCo.description,
        staCo.year,
        staCo.value,
        staCo.currency.toString(),
        staCo.diameterMM,
        staCo.internalDiameterMM,
        staCo.heightMM,
        staCo.widthMM
    )

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
                currency = currency.toString(),
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
                currency = currency.toString(),
                diameterMM = null,
                internalDiameterMM = null,
                heightMM = heightMM,
                widthMM = widthMM
            )
        }
    }
}
