package org.jesperancinha.enterprise.staco.jpa.dto

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto

data class ResponseDto(
    val staCoDtos: List<StaCoDto>,
    val currentPage: Int,
    val totalRecords: Int,
    val totalPages: Int,
)