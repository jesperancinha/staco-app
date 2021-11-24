package org.jesperancinha.enterprise.staco.service.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table
data class StaCo(
    @field: Id
    override val id: Long?,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    override var currency: CurrencyType?,
    override val diameterMM: String?,
    override val internalDiameterMM: String?,
    override val heightMM: String?,
    override val widthMM: String?,
    @field: Version
    val version: Long? = null,
) : IStaCo, Persistable<String> {
    override fun getId(): String = id.toString()

    override fun isNew(): Boolean = (version ?: 0) <= 0
}


internal val StaCo.toDto: StaCoDto
    get() = StaCoDto(
        description,
        year,
        value,
        currency,
        diameterMM,
        internalDiameterMM,
        heightMM,
        widthMM
    )

internal val StaCoDto.toData: StaCo
    get() = StaCo(
        id = null,
        description = description,
        year = year,
        value = value,
        currency = currency,
        diameterMM = diameterMM,
        internalDiameterMM = internalDiameterMM,
        heightMM = heightMM,
        widthMM = widthMM
    )
