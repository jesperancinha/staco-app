package org.jesperancinha.demo.engine.dto

class ResponseDto(
    val staCoDtos: List<StaCoDto>,
    val currentPage: Int,
    val totalRecords: Int,
    val totalPages: Int,
) {
}