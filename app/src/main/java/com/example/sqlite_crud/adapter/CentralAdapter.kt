package com.example.sqlite_crud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_crud.R
import com.example.sqlite_crud.model.CentralModel

class CentralAdapter : RecyclerView.Adapter<CentralAdapter.CentralViewHolder>() {

    private var centralList: ArrayList<CentralModel> = ArrayList()
    private var onClickItem: ((CentralModel) -> Unit)? = null
    private var onClickDeleteItem: ((CentralModel) -> Unit)? = null

    fun addItems(items: ArrayList<CentralModel>) {
        this.centralList = items
    }


    fun setOnClickItem(callback: (CentralModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (CentralModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    class CentralViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        //        val id: ImageView = view.findViewById(R.id.image_view)
        val id: TextView = view.findViewById(R.id.tvId)
        val name: TextView = view.findViewById(R.id.tvName)
        val model: TextView = view.findViewById(R.id.tvModel)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)

        fun bindView(central: CentralModel) {
            id.text = central.id.toString()
            name.text = central.name
            model.text = central.model
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentralViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_central, parent, false)
        return CentralViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CentralViewHolder, position: Int) {
        val central = centralList[position]
//        holder.imageView.setImageResource(item.imageResource)
        holder.bindView(central)
        holder.itemView.setOnClickListener { onClickItem?.invoke(central) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(central) }
    }

    override fun getItemCount() = centralList.size
}