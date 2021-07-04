package org.jesperancinha.demo.engine.domain

import javax.persistence.*

@Table
@Entity
data class StaCo(
    @Id
    val id: Long?,
    val description: String?,
    var year: String?,
    var value: String?,
    var currency: String?,
    val diameterMM: String?,
    val internalDiameterMM: String?,
    val heightMM: String?,
    val widthMM: String?,
)
