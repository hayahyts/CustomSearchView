package com.hayahyts.library

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hayahyts.library.util.KeyboardUtils
import kotlinx.android.synthetic.main.custom_search_view.view.*

class CustomSearchView : FrameLayout, View.OnClickListener {
    private var adapter: SuggestionsAdapter =
            SuggestionsAdapter(ArrayList(), false, object : SuggestionsAdapter.SuggestionListener {
                override fun removeSuggestion(suggestion: String) {
                    removeSuggestionFromHistory(suggestion)
                    if (getSuggestionHistory().size < 1) {
                        noHistory.visibility = View.VISIBLE
                        suggestions.visibility = View.GONE
                    }
                }

                override fun onSuggestionClicked(suggestion: String) {
                    inputField.setText(suggestion)
                    submitQuery()
                }
            })
    private var queryTextListener: QueryTextListener? = null
    private lateinit var suggestionsList: List<String>

    // Constants
    companion object {
        const val SUGGESTIONS_KEY = "suggestions"
        const val TAG = "CustomSearchView"
    }

    // Constructors
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs) {
        setupViews()
        applyXmlAttributes(attrs, defStyleAttr)
    }

    // Setup
    private fun setupViews() {
        LayoutInflater.from(context).inflate(R.layout.custom_search_view, this, true)
        // Set listeners
        scrim.setOnClickListener(this)
        searchIcon.setOnClickListener(this)
        inputField.setOnClickListener(this)
        inputField.setOnEditorActionListener { _, _, _ ->
            submitQuery()
            true
        }
        inputField.addTextChangedListener(object : TextChangedAdapter {
            override fun onTextWatcherTextChanged(newText: CharSequence) {
                onTextChanged()
            }
        })
        inputField.setText("")
        inputField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                KeyboardUtils.showKeyboard(inputField)
                showSuggestions()
            }
        }
        //Setup recycler view
        suggestions.layoutManager = LinearLayoutManager(context)
        suggestions.adapter = adapter
    }

    private fun onTextChanged() {
        // If no text has been entered
        if (inputField.text.toString().isEmpty()) {
            // If history exist, show it
            if (getSuggestionHistory().size > 0) {
                suggestions.visibility = View.VISIBLE
                noHistory.visibility = View.GONE
                adapter.replaceData(getSuggestionHistory().toList(), true)
            } else { // Else show no history
                noHistory.visibility = View.VISIBLE
                suggestions.visibility = View.GONE
            }
        } else { // Else show suggestions
            adapter.replaceData(suggestionsList, false)
            suggestions.visibility = View.VISIBLE
            noHistory.visibility = View.GONE
        }

        if (queryTextListener != null)
            queryTextListener?.onQueryTextChange(inputField.text.toString())

        adapter.filter(inputField.text.toString())
    }

    private fun applyXmlAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView, defStyleAttr, 0)
        if (a != null) {
            if (a.hasValue(R.styleable.CustomSearchView_toolbarBackground)) {
                setToolbarBackgroundColor(a.getColor(R.styleable.CustomSearchView_toolbarBackground, 0))
            }
            if (a.hasValue(R.styleable.CustomSearchView_android_textColorHint)) {
                setHintTextColor(a.getColor(R.styleable.CustomSearchView_android_textColorHint, 0))
            }
            if (a.hasValue(R.styleable.CustomSearchView_android_textColor)) {
                setTextColor(a.getColor(R.styleable.CustomSearchView_android_textColor, 0))
            }
            if (a.hasValue(R.styleable.CustomSearchView_android_hint)) {
                setHint(a.getString(R.styleable.CustomSearchView_android_hint))
            }
        }
        a.recycle()
    }

    // Event handling
    private fun submitQuery() {
        val query = inputField.text
        if (TextUtils.getTrimmedLength(query) > 0) {
            if (queryTextListener != null && queryTextListener!!.onQueryTextSubmit(query.toString())) {
                inputField.text = null
            }
            addSuggestionToHistory(query.toString())
            hideSuggestions()
        }
    }

    private fun showSuggestions() {
        bottomPanel.visibility = View.VISIBLE
        scrim.visibility = View.VISIBLE
        //searchBar.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        searchIcon.setImageResource(R.drawable.ic_arrow_back_70)
    }

    private fun hideSuggestions() {
        bottomPanel.visibility = View.GONE
        scrim.visibility = View.GONE
        KeyboardUtils.hideKeyboard(inputField)
        //searchBar.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        searchIcon.setImageResource(R.drawable.ic_search)
    }

    override fun onClick(v: View?) {
        when (v) {
            inputField -> showSuggestions()
            scrim -> hideSuggestions()
        }
    }

    // Suggestions history //
    private fun addSuggestionToHistory(suggestion: String) {
        val suggestions = getSuggestionHistory()
        suggestions.add(suggestion)
        Log.d(TAG, suggestions.toString())
        Log.d(TAG, context.packageName)
        val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putStringSet(SUGGESTIONS_KEY, suggestions)
        editor.apply()
    }

    private fun removeSuggestionFromHistory(suggestion: String) {
        val suggestions = getSuggestionHistory()
        suggestions.remove(suggestion)
        val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putStringSet(SUGGESTIONS_KEY, suggestions)
        editor.apply()
    }

    private fun getSuggestionHistory(): MutableSet<String> {
        val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return sp.getStringSet(SUGGESTIONS_KEY, HashSet())
    }

    // Interfaces
    interface QueryTextListener {

        fun onQueryTextSubmit(query: String): Boolean

        fun onQueryTextChange(newText: String)
    }

    // Public methods
    fun setSuggestions(suggestionsList: List<String>) {
        this.suggestionsList = suggestionsList
    }

    fun setQueryTextListener(listener: QueryTextListener) {
        this.queryTextListener = listener
    }

    fun setToolbarBackgroundColor(@ColorInt color: Int) {
        toolbar.setBackgroundColor(color)
    }

    fun setHintTextColor(@ColorInt color: Int) {
        inputField.setHintTextColor(color)
    }

    fun setTextColor(@ColorInt color: Int) {
        inputField.setTextColor(color)
    }

    fun setHint(hint: String) {
        inputField.hint = hint
    }
}