package org.jesperancinha.enterprise.engine.dto

data class ResponseDto(
    val staCoDtos: List<StaCoDto>,
    val currentPage: Int,
    val totalRecords: Int,
    val totalPages: Int,
)