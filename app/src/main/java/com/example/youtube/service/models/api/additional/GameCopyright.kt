package com.example.youtube.service.models.api.additional

data class GameCopyright(
    var key: Int,
    var is_allowable: Boolean,
    var accept_monetization: Boolean,
    var tags: ArrayList<String> = ArrayList(),
    var name: String,
    var description: String
)