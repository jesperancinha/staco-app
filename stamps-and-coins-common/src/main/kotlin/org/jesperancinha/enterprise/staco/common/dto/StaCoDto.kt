package org.jesperancinha.enterprise.staco.common.dto

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.ObjectType

/**
 * Either the description is null or the description must be at least 10 characters long.
 */
data class Description(val value: String?) {
    init {
        value?.let {
            require(value.isNotBlank())
            require(value.length > 10)
        }
    }

    override fun toString(): String = value ?: ""
}

data class StaCoDto(
    val id: String? = null,
    val description: Description? = null,
    val year: String? = null,
    val value: String? = null,
    val currency: CurrencyType? = null,
    val type: ObjectType,
    val diameterMM: String? = null,
    val internalDiameterMM: String? = null,
    val heightMM: String? = null,
    val widthMM: String? = null,
) {
}
