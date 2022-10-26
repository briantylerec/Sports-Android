package com.monksoft.sports

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.monksoft.sports.databinding.ItemSportBinding
import com.monksoft.sports.pojos.Sport

class SportListAdapter(private val listener: OnClickListener) :
    ListAdapter<Sport, RecyclerView.ViewHolder>(SportDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_sport, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sport = getItem(position)

        with(holder as ViewHolder){
            setListener(sport)

            binding.tvName.text = sport.name

            val request = ImageRequest.Builder(context)
                .data(sport.imgUrl)
                .crossfade(true) //no vale transformations
//                .transformations((listOf(
//                    BlurTransformation(context, 25f)
//                ))
                .size(1280, 720)
                .target(
                    onStart = {
                        binding.imgPhoto.setImageResource(R.drawable.ic_access_time)
                    },
                    onSuccess = {
                        binding.progressBar.visibility = View.GONE
                        binding.imgPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
                        binding.imgPhoto.setImageDrawable(it)
                    },
                    onError = {
                        binding.progressBar.visibility = View.GONE
                        binding.imgPhoto.setImageResource(R.drawable.ic_error_outline)
                    }
                )
                .build()
            context.imageLoader.enqueue(request)

//            Glide.with(context)
//                .asBitmap()
//                .load(sport.imgUrl)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .centerCrop()
//                .into(object : CustomTarget<Bitmap>(1280, 720){
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//
//                    }
//
//                    override fun onLoadStarted(placeholder: Drawable?) {
//                        super.onLoadStarted(placeholder)
//                    }
//
//                    override fun onLoadFailed(errorDrawable: Drawable?) {
//                        super.onLoadFailed(errorDrawable)
//
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {}
//                })

//            Picasso.get()
//                .load(sport.imgUrl)
//                .resize(1280, 720)
//                .into(object : Target{
//                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                        binding.progressBar.visibility = View.GONE
//                        binding.imgPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
//                        binding.imgPhoto.setImageBitmap(bitmap)
//                    }
//
//                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                        binding.progressBar.visibility = View.GONE
//                        binding.imgPhoto.setImageResource(R.drawable.ic_error_outline)
//                    }
//
//                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                        binding.imgPhoto.setImageResource(R.drawable.ic_access_time)
//                    }
//                })
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemSportBinding.bind(view)

        fun setListener(sport: Sport){
            binding.root.setOnClickListener { listener.onClick(sport) }
        }
    }

    class SportDiffCallback: DiffUtil.ItemCallback<Sport>(){
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem == newItem
    }
}