package org.jesperancinha.enterprise.staco.common.dto

data class ResponseDto(
    val staCoDtos: List<StaCoDto>,
    val currentPage: Int,
    val totalRecords: Long,
    val totalPages: Long,
)