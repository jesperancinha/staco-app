package org.jesperancinha.enterprise.staco.common.domain

import org.apache.commons.csv.CSVRecord
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto

interface IStaCo {
    val stacoId: String?
    val description: String?
    var year: String?
    var value: String?
    var currency: CurrencyType?
    var type: ObjectType
    val diameterMM: String?
    val internalDiameterMM: String?
    val heightMM: String?
    val widthMM: String?
}

val CSVRecord.toDto: StaCoDto
    get() = StaCoDto(
        id = get("id"),
        description = Description(get("description")),
        year = get("year"),
        value = get("value"),
        currency = CurrencyType.valueOf(get("currency")),
        type = ObjectType.valueOf(get("type")),
        diameterMM = get("diameterMM"),
        internalDiameterMM = get("internalDiameterMM"),
        heightMM = get("heightMM"),
        widthMM = get("widthMM")
    )

class StaCoTypeNotSupportedException : RuntimeException()
