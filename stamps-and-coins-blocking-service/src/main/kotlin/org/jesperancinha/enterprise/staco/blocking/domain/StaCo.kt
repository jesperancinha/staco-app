package org.jesperancinha.enterprise.staco.blocking.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Table
@Entity
data class StaCo(
    @field: Id
    @field: GeneratedValue(strategy = GenerationType.UUID)
    @field: Column(name = "id")
    override val stacoId: UUID? = null,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    @field:Enumerated(EnumType.STRING)
    override val currency: CurrencyType,
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
