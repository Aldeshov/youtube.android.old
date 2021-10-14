package com.example.youtube.service.models.api.additional

data class SongCopyright(
    var key: Int,
    var is_allowable: Boolean,
    var accept_monetization: Boolean,
    var tags: ArrayList<String> = ArrayList(),
    var song: String,
    var artist: String,
    var album: String,
    var licensed_to: String
)