package com.agile.engine.challenge.core.repository.contract.response.images

import com.fasterxml.jackson.annotation.JsonProperty

data class PictureResponse(
    val id: String,
    @JsonProperty("cropped_picture")
    val croppedPicture: String
)