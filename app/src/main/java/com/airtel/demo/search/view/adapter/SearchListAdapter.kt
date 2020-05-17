package com.airtel.demo.search.view.adapter

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airtel.demo.R
import com.airtel.demo.search.data.model.AddressListItem

class SearchListAdapter(private val list: MutableList<AddressListItem>) :
    RecyclerView.Adapter<SearchListAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_list, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = list[position]
        val res = holder.itemView.resources

        holder.city.text = getSpannedText("City : ${item.city}", 4, res)

        holder.address.text = getSpannedText("Address : ${item.addressString}", 7, res)

        holder.location.text =
            getSpannedText("Location : ( ${item.latitude} , ${item.longitude} )", 8, res)
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val city: TextView = view.findViewById(R.id.text_city)
        val address: TextView = view.findViewById(R.id.text_address)
        val location: TextView = view.findViewById(R.id.text_lat_long)
    }

    private fun getSpannedText(text: String, len: Int, resources: Resources): SpannableString {
        val spannableString =
            SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.pink)),
            0, len,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }
}