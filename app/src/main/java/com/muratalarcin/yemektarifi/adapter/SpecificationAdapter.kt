package com.muratalarcin.yemektarifi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.databinding.RowBinding
import com.muratalarcin.yemektarifi.model.Specification
import com.muratalarcin.yemektarifi.util.downloadFromUrl
import com.muratalarcin.yemektarifi.util.placeholderProgressBar
import com.muratalarcin.yemektarifi.view.ListFragmentDirections

class SpecificationAdapter(private val specificationList: ArrayList<Specification>) :
    RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder>(), SpecificationClickListener {

    //private var onItemClickListener: AdapterView.OnItemClickListener? = null
    // ViewHolder sınıfını içeride tanımlayabilirsiniz.
    class SpecificationViewHolder(var view: RowBinding) : RecyclerView.ViewHolder(view.root) {
        /*// ViewHolder içindeki view'lara burada erişebilirsiniz.
        val nameTextView: TextView = itemView.findViewById(R.id.rowName)
        val tagTextView: TextView = itemView.findViewById(R.id.rowTag)
        val imageView: ImageView = itemView.findViewById(R.id.rowImageView)
        // Diğer view'ları buraya ekleyebilirsiniz.
        // val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        // val imageView: ImageView = itemView.findViewById(R.id.imageView)*/
    }

    // onCreateViewHolder metodu içinde LayoutInflater'ı kullanarak view'ı inflate ediyoruz.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.row, parent, false)
        val view = DataBindingUtil.inflate<RowBinding>(inflater, R.layout.row, parent, false)
        return SpecificationViewHolder(view)
    }

    // getItemCount metodu ile liste elemanlarının sayısını döndürüyoruz.
    override fun getItemCount(): Int {
        return specificationList.size
    }

    // onBindViewHolder metodu ile ViewHolder'ın view'larına verileri yerleştiriyoruz.
    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {

        val specification = specificationList[position]
        holder.view.specification = specification
        holder.view.listener = this


        /*val specification = specificationList[position]
        holder.nameTextView.text = specificationList[position].specificationName
        holder.tagTextView.text = specificationList[position].specificationTag

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(specification.uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.imageView.downloadFromUrl(specificationList[position].specificationImage, placeholderProgressBar(holder.itemView.context))
*/
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSpecificationList(newSpecificationList: List<Specification>){
        specificationList.clear()
        specificationList.addAll(newSpecificationList)
        notifyDataSetChanged()
    }

    val specification: Any
        get() = TODO("Not yet implemented")

    override fun onSpecificationClicked(v: View, specification: Specification) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(specification.uuid)
        Navigation.findNavController(v).navigate(action)
    }
}