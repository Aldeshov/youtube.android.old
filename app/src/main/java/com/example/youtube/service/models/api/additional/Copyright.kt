package com.example.youtube.service.models.api.additional

data class Copyright(
    var is_private: Boolean,
    var is_adult_content: Boolean,
    var is_kids_content: Boolean,
    var song_copyrights: ArrayList<SongCopyright> = ArrayList(),
    var game_copyrights: ArrayList<GameCopyright> = ArrayList()
)