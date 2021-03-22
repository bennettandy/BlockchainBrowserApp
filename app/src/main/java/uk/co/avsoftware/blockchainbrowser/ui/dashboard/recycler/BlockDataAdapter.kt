package uk.co.avsoftware.fragvm.ui.home.ui.gallery.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import uk.co.avsoftware.blockchainbrowser.R
import uk.co.avsoftware.blockchainbrowser.databinding.TransactionListItemBinding
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction

class BlockDataAdapter(var transactions: List<Transaction> = emptyList()) :
    RecyclerView.Adapter<BlockDataAdapter.BlockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        val binding: TransactionListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.transaction_list_item,
            parent,
            false
        )
        return BlockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        // bind position
        holder.binding.transaction = transactions[position]
    }

    override fun getItemCount(): Int = transactions.size

    class BlockViewHolder(val binding: TransactionListItemBinding) : RecyclerView.ViewHolder(binding.root)
}