package com.agile.engine.challenge.core.repository.contract.response.images

import com.fasterxml.jackson.annotation.JsonProperty

data class GetImageDetailResponse(
    val id: String,
    val author: String,
    val tags: String,
    @JsonProperty("cropped_picture")
    val croppedPicture: String,
    @JsonProperty("full_picture")
    val fullPicture: String
)