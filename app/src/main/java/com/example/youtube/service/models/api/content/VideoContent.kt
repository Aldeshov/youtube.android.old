package com.example.youtube.service.models.api.content

import com.example.youtube.service.models.api.additional.Copyright
import com.example.youtube.service.models.api.applications.Channel

data class VideoContent(
    var code: String,
    var title: String,
    var created_on: String,
    var video: String,
    var views: String,
    var likes: Int,
    var dislikes: Int,
    var preview: String,
    var type: Type,
    var description: String,
    var on_channel: Channel,
    var copyrights: Copyright
)