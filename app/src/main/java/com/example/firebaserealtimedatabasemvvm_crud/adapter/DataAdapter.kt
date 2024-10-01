package com.example.firebaserealtimedatabasemvvm_crud.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabasemvvm_crud.MainActivity
import com.example.firebaserealtimedatabasemvvm_crud.R
import com.example.firebaserealtimedatabasemvvm_crud.model.Data


class Dataadapter(private var data:List<Data>, private var itemClickListener: MainActivity):RecyclerView.Adapter<Dataadapter.ViewHolder>() {

    interface ItemClickListener{
        fun onEditItemClick(data: Data)
        fun onDeleteItemClick(data: Data)
    }



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val stuid = itemView.findViewById<TextView>(R.id.idTxt)
        val name = itemView.findViewById<TextView>(R.id.nameTxt)
        val email = itemView.findViewById<TextView>(R.id.emailTxt)
        val subject = itemView.findViewById<TextView>(R.id.subjectTxt)
        val birthdate = itemView.findViewById<TextView>(R.id.birthdateTxt)
        var edit = itemView.findViewById<ImageButton>(R.id.editBtn)
        val delete = itemView.findViewById<ImageButton>(R.id.deleteBtn)

    }
    fun updateData(newData:List<Data>){
        this.data=newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]
        holder.stuid.text=item.id
        holder.name.text=item.name
        holder.email.text=item.email
        holder.subject.text=item.subject
        holder.birthdate.text=item.birthdate


        holder.edit.setOnClickListener{
            itemClickListener.onEditItemClick(item)
        }
        holder.delete.setOnClickListener {
            itemClickListener.onDeleteClick(item)
        }

    }


}