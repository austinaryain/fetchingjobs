package com.austinaryain.fetchchallenge.presentation.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.austinaryain.fetchchallenge.R
import com.austinaryain.fetchchallenge.databinding.LayoutHeaderBinding

@EpoxyModelClass(layout = R.layout.layout_header)
abstract class HeaderModel : EpoxyModelWithHolder<HeaderModel.Holder>() {

    @EpoxyAttribute
    lateinit var text: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.binding.title.text = text
    }

    class Holder : EpoxyHolder() {
        lateinit var binding: LayoutHeaderBinding
        override fun bindView(itemView: View) {
            binding = LayoutHeaderBinding.bind(itemView)
        }
    }
}
