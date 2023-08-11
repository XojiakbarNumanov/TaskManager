package com.xojiakbar.taskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity


class RecyclerAdapter<T>(private val layoutId: Int, private val listener: AdapterListener<T>?) :
    RecyclerView.Adapter<RecyclerAdapter<T>.mViewHolder>() {
    private var list: MutableList<T>? = null



    fun setList(list: MutableList<T>) {
        if (this.list != null) notifyItemRangeRemoved(0, this.list!!.size)
        this.list = list
        if (list != null) notifyItemRangeInserted(0, list.size)
    }

    fun getList(): List<T>? {
        return list
    }

    fun addItems(list: List<T>?) {
        if (this.list != null && list != null) {
            val position = this.list!!.size
            this.list!!.addAll(list)
            notifyItemRangeInserted(position, list.size)
        }
    }

    fun addItem(item: T) {
        if (list != null) {
            val position = list!!.size
            list!!.add(item)
            notifyItemInserted(position)
        }
    }

    fun removeItems() {
        if (list != null) {
            val position = list!!.size
            list!!.clear()
            notifyItemRangeRemoved(0, position)
        }
    }

    fun removeItem(item: T?) {
        if (list != null && item != null) {
            val position = list!!.indexOf(item)
            list!!.remove(item)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil
            .inflate<ViewDataBinding>(inflater, layoutId, parent, false)
        return mViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    inner class mViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
        dataBinding.root
    ) {
        init {
            listener?.setController(dataBinding)
        }

        fun bind(item: T) {
            listener?.bindItem(dataBinding, item, adapterPosition)
            animateView(dataBinding.root)
        }

        private fun animateView(v: View) {
//            if (v.animation == null) {
//                val animation = AnimationUtils.loadAnimation(v.context, R.anim.scale_xy)
//                v.animation = animation
//            }
        }
    }

    interface AdapterListener<T> {
        fun setController(dataBinding: ViewDataBinding?)
        fun bindItem(dataBinding: ViewDataBinding?, item: T, position: Int?)
    }

}