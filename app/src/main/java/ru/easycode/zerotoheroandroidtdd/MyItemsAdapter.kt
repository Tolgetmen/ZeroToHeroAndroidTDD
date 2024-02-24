package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class MyItemsAdapter : RecyclerView.Adapter<MyItemsViewHolder>() {

    private val itemList = ArrayList<CharSequence>()


    fun add(source: CharSequence) {
        itemList.add(source)
        notifyItemInserted(itemList.size - 1)
    }

    fun save(bundle: Bundle) {
        bundle.putCharSequenceArrayList(KEY, itemList)
    }

    fun restore(bundle: Bundle) {
        itemList.addAll(bundle.getCharSequenceArrayList(KEY) ?: ArrayList())
        notifyItemRangeInserted(0, itemList.size)
    }

    companion object {
        private const val KEY = "MyItemsAdapterKey"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemsViewHolder {
        return MyItemsViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = itemList.size
    override fun onBindViewHolder(holder: MyItemsViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}


class MyItemsViewHolder(private val binding: ItemLayoutBinding) : ViewHolder(binding.root) {
    fun bind(source: CharSequence) {
        binding.elementTextView.text = source
    }
}