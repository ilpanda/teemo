package cn.ilpanda.arch_debug.kotlin.ui.appinfo

import cn.ilpanda.arch_debug.R
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class PhoneInfoAdapter(sectionHeadResId: Int, layoutResId: Int, data: MutableList<PhoneDataInfo>) :
    BaseSectionQuickAdapter<PhoneDataInfo, BaseViewHolder>(sectionHeadResId, layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: PhoneDataInfo) {
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_tip, item.content)
    }

    override fun convertHeader(helper: BaseViewHolder, item: PhoneDataInfo) {
        helper.setText(R.id.tv_title, item.title)
    }
}

class PhoneDataInfo(var title: String, var content: String? = "", override val isHeader: Boolean = false) : SectionEntity {


}