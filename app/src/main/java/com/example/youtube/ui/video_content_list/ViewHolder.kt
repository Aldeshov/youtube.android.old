package com.example.youtube.ui.video_content_list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.BR
import com.example.youtube.R
import com.example.youtube.configs.BASE_URL
import com.example.youtube.configs.DATE_PATTERN
import com.example.youtube.databinding.VideoContentItemBinding
import com.example.youtube.service.models.VideoContent
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder constructor(
    private val binding: VideoContentItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun setup(item: VideoContent) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()

        Picasso.get().load(BASE_URL + item.preview)
            .placeholder(R.drawable.video_content_background)
            .error(R.drawable.video_content_load_error)
            .into(binding.preview)

        Picasso.get().load(BASE_URL + item.on_channel.avatar)
            .placeholder(R.drawable.ic_channel)
            .error(R.drawable.ic_channel_error)
            .into(binding.channelAvatar)

        val createdDate = SimpleDateFormat(DATE_PATTERN, Locale.US)
            .parse(item.created_on + "+0000")
        if (createdDate != null) {
            binding.time.text = getTimeDifference(createdDate, Date())
        }
    }

    private fun getTimeDifference(before: Date, after: Date): String {
        val timeDifference = after.time - before.time
        if (timeDifference < 1000) {
            return context.getString(R.string.just_now)
        }
        val seconds = timeDifference / 1000
        if (seconds < 60) {
            return if (seconds == 1L) {
                context.getString(R.string.one_second_ago)
            } else {
                context.getString(R.string.x_seconds_ago, seconds)
            }
        }
        val minutes = seconds / 60
        if (minutes < 60) {
            return if (minutes == 1L) {
                context.getString(R.string.one_minute_ago)
            } else {
                context.getString(R.string.x_minutes_ago, minutes)
            }
        }
        val hours = minutes / 60
        if (hours < 24) {
            return if (hours == 1L) {
                context.getString(R.string.one_hour_ago)
            } else {
                context.getString(R.string.x_hours_ago, hours)
            }
        }
        val days = hours / 24
        if (days < 31) {
            return if (days == 1L) {
                context.getString(R.string.one_day_ago)
            } else {
                context.getString(R.string.x_days_ago, days)
            }
        }
        val months = days / 30
        if (months < 12) {
            return if (months == 1L) {
                context.getString(R.string.one_month_ago)
            } else {
                context.getString(R.string.x_months_ago, months)
            }
        }
        val years = months / 12
        return if (years == 1L) {
            context.getString(R.string.one_year_ago)
        } else {
            context.getString(R.string.x_years_ago, years)
        }
    }
}