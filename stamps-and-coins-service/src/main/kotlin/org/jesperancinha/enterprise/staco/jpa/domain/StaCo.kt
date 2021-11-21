package org.jesperancinha.enterprise.staco.jpa.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyEnum
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
data class StaCo(
    @Id
    override val id: Long? = null,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    override var currency: CurrencyEnum?,
    override val diameterMM: String?,
    override val internalDiameterMM: String?,
    override val heightMM: String?,
    override val widthMM: String?,
) : IStaCo


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
        description = description,
        year = year,
        value = value,
        currency = currency,
        diameterMM = diameterMM,
        internalDiameterMM = internalDiameterMM,
        heightMM = heightMM,
        widthMM = widthMM
    )
