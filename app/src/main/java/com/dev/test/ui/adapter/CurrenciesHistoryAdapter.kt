package com.dev.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.test.model.CurrenciesHistory
import com.dev.test.ui.details.viewmodel.CurrenciesHistoryItemViewModel
import com.dev.test.databinding.CurrenciesHistoryRowBinding
import com.dev.test.ui.base.BaseViewHolder

class CurrenciesHistoryAdapter  constructor(var mList: ArrayList<CurrenciesHistory>?) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var mListener: CurrencyAdapterListener? = null
    override fun getItemCount(): Int = mList?.size!!

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    interface CurrencyAdapterListener {
        fun click(optionData: CurrenciesHistory, posation: Int)
    }

    fun setListener(listener: CurrencyAdapterListener) {
        this.mListener = listener
    }

    fun clear() {
        mList!!.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val blogViewBinding = CurrenciesHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return BlogViewHolder(blogViewBinding)
    }

    fun addItems(
        list: List<CurrenciesHistory>
    ) {
        mList!!.addAll(list)
        notifyDataSetChanged()
    }

    inner class BlogViewHolder(  var mBinding: CurrenciesHistoryRowBinding) :
        BaseViewHolder(mBinding.root),
        CurrenciesHistoryItemViewModel.CurrencyClick {

        override fun onItemClick(currency: CurrenciesHistory, posation: Int) {
            mListener!!.click(currency, posation)
        }

        var mCurrencyViewModel: CurrenciesHistoryItemViewModel? = null
        override fun onBind(position: Int) {
            mBinding.position = position
            mCurrencyViewModel = CurrenciesHistoryItemViewModel(itemView.context, mList!![position], this)
            mBinding.viewModel = mCurrencyViewModel
            mBinding.executePendingBindings()
        }

    }


}