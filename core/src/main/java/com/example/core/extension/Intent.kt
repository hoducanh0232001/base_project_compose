package com.example.core.extension

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openBrowser(url: String) {
    if (url.isEmpty()) return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}
