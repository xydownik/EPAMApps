package com.example.browserapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewConfiguration
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var etWebLink: EditText
    private lateinit var btnSearch: Button

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        webView = findViewById(R.id.webView)
        etWebLink = findViewById(R.id.etWebLink)
        btnSearch = findViewById(R.id.btnSearch)

        // Настраиваем WebView
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                supportActionBar?.title = title ?: "Browser App"
            }
        }
        webView.settings.javaScriptEnabled = true

        // Обработка нажатия кнопки поиска
        btnSearch.setOnClickListener {
            val url = etWebLink.text.toString()
            if (url.isNotEmpty()) {
                webView.loadUrl(url)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_blue -> btnSearch.setBackgroundColor(Color.rgb(33, 150, 243))
            R.id.menu_green -> btnSearch.setBackgroundColor(Color.GREEN)
            R.id.menu_violet -> btnSearch.setBackgroundColor(Color.MAGENTA)
            R.id.menu_brown -> btnSearch.setBackgroundColor(Color.rgb(165, 42, 42))
            R.id.menu_yellow -> btnSearch.setBackgroundColor(Color.YELLOW)
            R.id.menu_orange -> btnSearch.setBackgroundColor(Color.rgb(255, 165, 0))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }



}
