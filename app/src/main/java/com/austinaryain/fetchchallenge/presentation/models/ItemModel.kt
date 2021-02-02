package com.austinaryain.fetchchallenge.presentation.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.austinaryain.fetchchallenge.R
import com.austinaryain.fetchchallenge.databinding.LayoutItemBinding

@EpoxyModelClass(layout = R.layout.layout_item)
abstract class ItemModel : EpoxyModelWithHolder<ItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var idText: String

    @EpoxyAttribute
    lateinit var nameText: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.binding.apply {
            idField.text = idText
            name.text = nameText
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var binding: LayoutItemBinding
        override fun bindView(itemView: View) {
            binding = LayoutItemBinding.bind(itemView)
        }
    }
}
