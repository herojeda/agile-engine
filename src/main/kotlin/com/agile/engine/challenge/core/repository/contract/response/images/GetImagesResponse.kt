package com.agile.engine.challenge.core.repository.contract.response.images

data class GetImagesResponse(
    val pictures: List<PictureResponse>,
    val page: Int,
    val pageCount: Int,
    val hasMOre: Boolean
)