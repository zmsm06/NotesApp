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
import com.zeinabmallaki.adapter.databinding.ListItemRecycleBinBinding
import com.zeinabmallaki.adapter.ui.AddNotesActivity


class RecycleBinAdapter(
    private val context: Context,
    private val dao: NotesDao

) : RecyclerView.Adapter<RecycleBinAdapter.RecycleViewHolder>() {


    private val allData = dao.getNotesForRecycler(DBHelper.TRUE_STATE)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder =

        RecycleViewHolder(
            ListItemRecycleBinBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = allData.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.setData(allData[position])
    }


    inner class RecycleViewHolder(
        private val binding: ListItemRecycleBinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: RecyclerNotesModel) {

            binding.txtTitleNotes.text = data.title

            binding.imgDeleteNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle("حذف یادداشت")
                    .setMessage("آیا میخواهید یادداشت برای همیشه حذف شود؟")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("بله") { dialog, _ ->
                        val result = dao.deleteNotes(data.id)
                        if (result) {
                            showText("یادداشت حذف شد")
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                        } else showText("عملیات با مشکل مواجه شد")

                    }
                    .setPositiveButton("خیر") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()

            }

            binding.imgRestoreNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle("حذف یادداشت")
                    .setMessage("آیا میخواهید یادداشت بازگردانی شود؟")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("بله") { dialog, _ ->
                        val result = dao.editNotes(data.id, DBHelper.FALSE_STATE)
                        if (result) {
                            showText("یادداشت بازگردانی شد")
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                        } else showText("عملیات با مشکل مواجه شد")

                    }
                    .setPositiveButton("خیر") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()

            }


        }
    }


    private fun showText(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}