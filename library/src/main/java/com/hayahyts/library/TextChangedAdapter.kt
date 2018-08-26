package com.hayahyts.library

import android.text.Editable
import android.text.TextWatcher

/**
 * @author Solomon on 3/30/2018.
 */

internal interface TextChangedAdapter : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onTextWatcherTextChanged(s)
    }

    override fun afterTextChanged(s: Editable){}

    fun onTextWatcherTextChanged(newText: CharSequence)
}
