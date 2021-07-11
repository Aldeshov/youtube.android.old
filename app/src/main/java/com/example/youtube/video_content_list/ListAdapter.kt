package com.example.youtube.video_content_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.R
import com.example.youtube.configs.BASE_URL
import com.example.youtube.configs.DATE_PATTERN
import com.example.youtube.models.VideoContent
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(var video_contents: ArrayList<VideoContent>,
                  var context: Context?): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_content_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: VideoContent = video_contents[position]
        holder.code = item.code
        Picasso.get().load(BASE_URL + item.preview)
            .placeholder(R.drawable.video_content_background)
            .error(R.drawable.video_content_load_error)
            .into(holder.preview)
        Picasso.get().load(BASE_URL + item.on_channel.avatar)
            .placeholder(R.drawable.ic_channel)
            .error(R.drawable.ic_channel_error)
            .into(holder.channelAvatar)
        holder.title.text =
            if (item.title.length <= 32) { item.title }
            else { item.title.take(32) + "..." }
        holder.channel.text =
            if (item.on_channel.name.length <= 24) { item.on_channel.name }
            else { item.on_channel.name.take(24) + "..." }
        holder.counter.text = item.views.toString()
        val createdDate = SimpleDateFormat(DATE_PATTERN, Locale.US)
            .parse(item.created_on + "+0000")
        if (createdDate != null) {
            holder.time.text = getDifference(createdDate, Date())
        }
    }

    override fun getItemCount(): Int {
        return video_contents.size
    }

    private fun getDifference(before: Date, after: Date): String {
        val timeDifference = after.time - before.time
        if (timeDifference < 1000) {
            return context!!.getString(R.string.just_now)
        }
        val seconds = timeDifference / 1000
        if (seconds < 60) {
            return if (seconds == 1L) {
                context!!.getString(R.string.one_second_ago)
            } else {
                context!!.getString(R.string.x_seconds_ago, seconds)
            }
        }
        val minutes = seconds / 60
        if (minutes < 60) {
            return if (minutes == 1L) {
                context!!.getString(R.string.one_minute_ago)
            } else {
                context!!.getString(R.string.x_minutes_ago, minutes)
            }
        }
        val hours = minutes / 60
        if (hours < 24) {
            return if (hours == 1L) {
                context!!.getString(R.string.one_hour_ago)
            } else {
                context!!.getString(R.string.x_hours_ago, hours)
            }
        }
        val days = hours / 24
        if (days < 31) {
            return if (days == 1L) {
                context!!.getString(R.string.one_day_ago)
            } else {
                context!!.getString(R.string.x_days_ago, days)
            }
        }
        val months = days / 30
        if (months < 12) {
            return if (months == 1L) {
                context!!.getString(R.string.one_month_ago)
            } else {
                context!!.getString(R.string.x_months_ago, months)
            }
        }
        val years = months / 12
        return if (years == 1L) {
            context!!.getString(R.string.one_year_ago)
        } else {
            context!!.getString(R.string.x_years_ago, years)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        lateinit var code: String
        private var videoContent: ConstraintLayout = view.findViewById(R.id.video_content)
        var preview: ImageView = view.findViewById(R.id.preview)
        var channelAvatar: ImageView = view.findViewById(R.id.channel_avatar)
        var title: TextView = view.findViewById(R.id.title)
        var channel: TextView = view.findViewById(R.id.channel)
        var counter: TextView = view.findViewById(R.id.counter)
        var time: TextView = view.findViewById(R.id.time)

        init {
            videoContent.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val activity = v.context
//            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main, MailItemFragment(item))
//                .addToBackStack("secondary")
//                .commit()
        }
    }
}