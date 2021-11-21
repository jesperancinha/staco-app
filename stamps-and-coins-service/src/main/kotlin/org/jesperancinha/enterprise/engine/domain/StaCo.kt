package org.jesperancinha.enterprise.engine.domain

import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
data class StaCo(
    @Id
    override val id: Long?,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    override var currency: String?,
    override val diameterMM: String?,
    override val internalDiameterMM: String?,
    override val heightMM: String?,
    override val widthMM: String?,
) : IStaCo
