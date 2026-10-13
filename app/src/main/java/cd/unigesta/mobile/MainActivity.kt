package cd.unigesta.mobile

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceError
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.view.Gravity
import android.graphics.Color
import android.view.inputmethod.EditorInfo
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private lateinit var status: TextView
    private lateinit var addressInput: EditText
    private val prefs by lazy { getSharedPreferences("unigesta", Context.MODE_PRIVATE) }
    private val defaultUrl = "https://teeth-locked-cooperation-experience.trycloudflare.com/Unigesta/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedUrl = prefs.getString("server_url", defaultUrl) ?: defaultUrl
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
        }
        status = TextView(this).apply {
            text = "Unigesta • prêt"
            setPadding(16, 8, 16, 8)
            setTextColor(Color.DKGRAY)
        }
        val settings = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(8, 4, 8, 4)
        }
        addressInput = EditText(this).apply {
            setSingleLine(true)
            textSize = 12f
            hint = "Adresse du serveur, ex. https://votre-domaine/Unigesta/"
            setText(savedUrl)
            imeOptions = EditorInfo.IME_ACTION_GO
        }
        settings.addView(addressInput, LinearLayout.LayoutParams(0, -2, 1f))
        val go = Button(this).apply {
            text = "Ouvrir"
            setOnClickListener { openAddress() }
        }
        settings.addView(go)
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowFileAccess = false
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                status.text = if (hasNetwork()) "Connexion au serveur…" else "Hors ligne • vérification de la connexion…"
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                status.text = "Unigesta • " + (url ?: "")
                if (url != null) addressInput.setText(url.substringBefore("#"))
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                if (request?.isForMainFrame == true) {
                    status.text = "Serveur inaccessible. Vérifie Internet et l'adresse du serveur."
                }
            }
        }
        webView.webChromeClient = WebChromeClient()
        root.addView(status)
        root.addView(settings)
        root.addView(webView, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
        if (savedInstanceState == null) openAddress()
    }

    private fun openAddress() {
        var url = addressInput.text.toString().trim()
        if (url.isBlank()) {
            Toast.makeText(this, "Saisis l'adresse du serveur Unigesta", Toast.LENGTH_LONG).show()
            return
        }
        if (!url.startsWith("https://") && !url.startsWith("http://")) url = "https://$url"
        if (!url.endsWith("/")) url += "/"
        prefs.edit().putString("server_url", url).apply()
        if (!hasNetwork()) status.text = "Hors ligne • le serveur ne peut pas être contacté"
        webView.loadUrl(url)
    }

    private fun hasNetwork(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
