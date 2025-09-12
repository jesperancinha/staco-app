package org.jesperancinha.enterprise.staco.cloud.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import java.util.UUID

data class StaCo(
    val dynId: String? = null,

    override val stacoId: UUID? = null,

    override val description: String?,

    override var year: String?,

    override var value: String?,

    override var currency: CurrencyType,

    override var type: ObjectType,

    override val diameterMM: String?,

    override val internalDiameterMM: String?,

    override val heightMM: String?,

    override val widthMM: String?,
) : IStaCo


internal val StaCo.toDto: StaCoDto
    get() = StaCoDto(
        null,
        Description(description),
        year,
        value,
        currency,
        type,
        diameterMM,
        internalDiameterMM,
        heightMM,
        widthMM
    )

internal val StaCoDto.toData: StaCo
    get() = StaCo(
        description = description.toString(),
        year = year,
        value = value,
        currency = currency,
        type = type,
        diameterMM = diameterMM,
        internalDiameterMM = internalDiameterMM,
        heightMM = heightMM,
        widthMM = widthMM
    )
