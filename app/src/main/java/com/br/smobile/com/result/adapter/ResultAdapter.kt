package com.br.smobile.com.result.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.smobile.com.R
import com.br.smobile.com.result.model.ResultModel

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    private var list: List<ResultModel> = emptyList()
    var clickListener: ((url: String) -> Unit)? = null
    var deleteClick: ((model: ResultModel) -> Unit)? = null

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val data: TextView = itemView.findViewById(R.id.text_view_data)
        private val textUrl: TextView = itemView.findViewById(R.id.text_link_data)
        private val deleteButton: ImageView = itemView.findViewById(R.id.delete_img_view)

        fun bind(resultModel: ResultModel) {
            data.text = resultModel.data
            textUrl.text = resultModel.text

            deleteButton.setOnClickListener {
                deleteClick?.invoke(resultModel)
            }

            textUrl.setOnClickListener {
                clickListener?.invoke(resultModel.text)
            }

        }
    }

    fun updateItems(newList: List<ResultModel>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(list[position])
    }
}