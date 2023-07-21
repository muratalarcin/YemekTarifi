package com.muratalarcin.yemektarifi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.recyclerview.widget.RecyclerView
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.model.Specification
import com.muratalarcin.yemektarifi.view.DetailFragmentDirections

class SpecificationAdapter(val specificationList: ArrayList<Specification>) :
    RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder>() {

    //private var onItemClickListener: AdapterView.OnItemClickListener? = null
    // ViewHolder sınıfını içeride tanımlayabilirsiniz.
    class SpecificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder içindeki view'lara burada erişebilirsiniz.
        val nameTextView: TextView = itemView.findViewById(R.id.rowName)
        val tagTextView: TextView = itemView.findViewById(R.id.rowTag)
        // Diğer view'ları buraya ekleyebilirsiniz.
        // val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        // val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // onCreateViewHolder metodu içinde LayoutInflater'ı kullanarak view'ı inflate ediyoruz.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row, parent, false)
        return SpecificationViewHolder(view)
    }

    // getItemCount metodu ile liste elemanlarının sayısını döndürüyoruz.
    override fun getItemCount(): Int {
        return specificationList.size
    }

    // onBindViewHolder metodu ile ViewHolder'ın view'larına verileri yerleştiriyoruz.
    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        val specification = specificationList[position]
        holder.nameTextView.text = specificationList[position].specificationName
        holder.tagTextView.text = specificationList[position].specificationTag

        holder.itemView.setOnClickListener {
            val action = DetailFragmentDirections.actionListFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSpecificationList(newSpecificationList: List<Specification>){
        specificationList.clear()
        specificationList.addAll(newSpecificationList)
        notifyDataSetChanged()
    }
}