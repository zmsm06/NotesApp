package com.zeinabmallaki.adapter.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zeinabmallaki.adapter.R
import com.zeinabmallaki.adapter.data.local.db.DBHelper
import com.zeinabmallaki.adapter.data.local.db.dao.NotesDao
import com.zeinabmallaki.adapter.data.model.RecyclerNotesModel
import com.zeinabmallaki.adapter.databinding.ListItemNotesBinding
import com.zeinabmallaki.adapter.ui.AddNotesActivity

class NotesAdapter (
    private val context: Context,
    private val dao : NotesDao

    ): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    private var allData: ArrayList<RecyclerNotesModel>
    init {
        allData = dao.getNotesForRecycler(DBHelper.FALSE_STATE)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =

        NotesViewHolder(
            ListItemNotesBinding.inflate(LayoutInflater.from(context),parent,false))


    override fun getItemCount(): Int = allData.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.setData(allData[position])
    }


    inner class NotesViewHolder(
        private val binding: ListItemNotesBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun setData(data: RecyclerNotesModel){

            binding.txtTitleNotes.text = data.title
            binding.imgDeleteNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context,R.style.CustomAlertDialog))
                    .setTitle("حذف یادداشت")
                    .setMessage("آیا میخواهید یادداشت به سطل زباله منتقل شود؟")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("بله"){dialog,_ ->
                        val result = dao.editNotes(data.id, DBHelper.TRUE_STATE)
                        if (result){
                            showText("یادداشت به سطل زباله منتقل شد")
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                        }else showText("عملیات با مشکل مواجه شد")

                    }
                    .setPositiveButton("خیر"){dialog,_ -> dialog.dismiss() }
                    .create()
                    .show()

            }

            binding.root.setOnClickListener {
                val intent = Intent(context, AddNotesActivity::class.java )
                intent.putExtra("notesId", data.id)


                context.startActivity(intent)
            }



        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: ArrayList<RecyclerNotesModel>){

            allData = data
            notifyDataSetChanged()
    }

    private fun showText(text:String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}