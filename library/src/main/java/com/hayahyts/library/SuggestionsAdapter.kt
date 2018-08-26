package com.hayahyts.library

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*

internal class SuggestionsAdapter(private var originalSuggestions: List<String>?, private var showDelete: Boolean, private val listener: SuggestionListener?) : RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder>() {
    private val filteredSuggestions: MutableList<String>

    init {
        filteredSuggestions = ArrayList()
        this.filteredSuggestions.addAll(originalSuggestions!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(filteredSuggestions[position])
    }

    override fun getItemCount(): Int {
        return if (filteredSuggestions.size < 7) {
            filteredSuggestions.size
        } else 7
    }

    fun filter(s: String) {
        filteredSuggestions.clear()
        if (s.isEmpty()) {
            filteredSuggestions.addAll(originalSuggestions!!)
        } else {
            for (suggestion in originalSuggestions!!) {
                if (suggestion.toLowerCase().contains(s.toLowerCase())) {
                    filteredSuggestions.add(suggestion)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun replaceData(suggestions: List<String>, showDelete: Boolean) {
        this.originalSuggestions = suggestions
        this.filteredSuggestions.addAll(originalSuggestions!!)
        this.showDelete = showDelete
        notifyDataSetChanged()
    }

    interface SuggestionListener {

        fun onSuggestionClicked(suggestion: String)

        fun removeSuggestion(suggestion: String)
    }

    internal inner class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val suggestionTV: TextView = itemView.findViewById(R.id.suggestion)
        private val removeImg: ImageView = itemView.findViewById(R.id.remove)

        init {
            if (listener != null) {
                itemView.setOnClickListener { listener.onSuggestionClicked(filteredSuggestions[adapterPosition]) }
                removeImg.setOnClickListener {
                    listener.removeSuggestion(filteredSuggestions[adapterPosition])
                    filteredSuggestions.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(suggestion: String) {
            suggestionTV.text = suggestion
            if (showDelete) {
                removeImg.visibility = View.VISIBLE
            } else {
                removeImg.visibility = View.GONE
            }
        }
    }
}
