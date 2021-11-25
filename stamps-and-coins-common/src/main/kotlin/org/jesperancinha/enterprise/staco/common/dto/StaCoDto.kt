package org.jesperancinha.enterprise.staco.common.dto

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType

/**
 * Either the description is null or the description must be at least 10 characters long.
 */
class Description(val value: String?) {
    init {
        value?.let {
            require(value.isNotBlank())
            require(value.length > 10)
        }
    }
}

data class StaCoDto(
    val description: Description? = null,
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
                description = Description(description),
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
                description = Description(description),
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
