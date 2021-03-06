package org.jesperancinha.enterprise.staco.jpa.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
data class StaCo(
    @field: Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    override val stacoId: String? = null,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    @Enumerated(EnumType.STRING)
    override var currency: CurrencyType?,
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
