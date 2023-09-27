package com.muratalarcin.yemektarifi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.model.Specification
import com.muratalarcin.yemektarifi.service.FavoriteDao
import com.muratalarcin.yemektarifi.util.downloadFromUrl
import com.muratalarcin.yemektarifi.util.placeholderProgressBar
import com.muratalarcin.yemektarifi.view.ListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpecificationAdapter(
    private val specificationList: MutableList<Specification>,
    private val favoriteDao: FavoriteDao
) : RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder>() {

    inner class SpecificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var isFavorite: Boolean = false

        val nameTextView: TextView = itemView.findViewById(R.id.rowName)
        val tagTextView: TextView = itemView.findViewById(R.id.rowTag)
        val imageView: ImageView = itemView.findViewById(R.id.rowImageView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favIcon)

        fun bind(specification: Specification) {
            isFavorite = specification.isFavorite
            updateFavoriteIcon()

            itemView.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(specification.uuid)
                Navigation.findNavController(it).navigate(action)
            }

            favoriteImageView.setOnClickListener {
                isFavorite = !isFavorite
                updateFavoriteIcon()

                CoroutineScope(Dispatchers.IO).launch {
                    if (isFavorite) {
                        favoriteDao.insertFavoriteItem(specification)
                    } else {
                        favoriteDao.deleteFavoriteItem(specification)
                    }
                }
            }
        }

        private fun updateFavoriteIcon() {
            if (isFavorite) {
                favoriteImageView.setImageResource(R.drawable.ic_favorite)
            } else {
                favoriteImageView.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row, parent, false)
        return SpecificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        val specification = specificationList[position]
        holder.bind(specification)
        holder.nameTextView.text = specification.specificationName
        holder.tagTextView.text = specification.specificationTag
        holder.imageView.downloadFromUrl(
            specification.specificationImage,
            placeholderProgressBar(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return specificationList.size
    }

    fun getFavoriteItems(): List<Specification> {
        return specificationList.filter { it.isFavorite }
    }
}