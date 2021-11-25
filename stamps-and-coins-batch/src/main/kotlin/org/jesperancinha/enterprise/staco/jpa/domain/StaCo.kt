package org.jesperancinha.enterprise.staco.jpa.domain

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.IStaCo
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table
data class StaCo(
    @field: Id
    @Column("id")
    override val stacoId: String?,
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

    override fun getId(): String = stacoId.toString()

    override fun isNew(): Boolean = (version ?: 0) <= 0
}
