package com.digitalkarachi.psx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
    private static final int FILE_CHOOSER_REQUEST = 7001;
    private static final String PREFS = "psx_android_prefs";
    private static final String KEY_URL = "psx_url";

    private WebView webView;
    private ProgressBar progressBar;
    private ValueCallback<Uri[]> filePathCallback;
    private String homeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi();
        configureWebView();

        String savedUrl = getSharedPreferences(PREFS, MODE_PRIVATE).getString(KEY_URL, "");
        String packagedUrl = getString(R.string.default_psx_url);
        if (!savedUrl.isEmpty()) {
            homeUrl = normalizeUrl(savedUrl);
            loadHome();
        } else if (!packagedUrl.contains("YOUR-STREAMLIT-APP")) {
            homeUrl = normalizeUrl(packagedUrl);
            loadHome();
        } else {
            showUrlSetupDialog(false);
        }
    }

    private void buildUi() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(7, 17, 29));

        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setPadding(dp(14), dp(7), dp(8), dp(7));
        bar.setBackgroundColor(Color.rgb(7, 17, 29));

        TextView title = new TextView(this);
        title.setText("PSX Intelligence");
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setOnLongClickListener(v -> {
            showUrlSetupDialog(true);
            return true;
        });
        bar.addView(title, new LinearLayout.LayoutParams(0, dp(48), 1f));

        Button home = makeBarButton("⌂");
        home.setContentDescription("Home");
        home.setOnClickListener(v -> loadHome());
        bar.addView(home);

        Button refresh = makeBarButton("↻");
        refresh.setContentDescription("Refresh");
        refresh.setOnClickListener(v -> {
            if (webView.getUrl() == null) loadHome(); else webView.reload();
        });
        bar.addView(refresh);

        Button settingsButton = makeBarButton("⚙");
        settingsButton.setContentDescription("App URL settings");
        settingsButton.setOnClickListener(v -> showUrlSetupDialog(true));
        bar.addView(settingsButton);

        root.addView(bar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(58)));

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        root.addView(progressBar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(3)));

        webView = new WebView(this);
        root.addView(webView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(2), dp(3), dp(2), dp(5));
        nav.setBackgroundColor(Color.rgb(7, 17, 29));
        addNavButton(nav, "Alerts", "alerts");
        addNavButton(nav, "Decision", "decision");
        addNavButton(nav, "Scenario", "scenario");
        addNavButton(nav, "Divergence", "divergence");
        addNavButton(nav, "Portfolio", "portfolio");
        root.addView(nav, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(58)));

        setContentView(root);
    }

    private void addNavButton(LinearLayout nav, String label, String pageKey) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(10);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setPadding(dp(1), 0, dp(1), 0);
        b.setMinWidth(0);
        b.setMinimumWidth(0);
        b.setMinHeight(0);
        b.setMinimumHeight(0);
        b.setOnClickListener(v -> loadPage(pageKey));
        nav.addView(b, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
    }

    private void loadPage(String pageKey) {
        if (homeUrl == null || homeUrl.trim().isEmpty()) {
            showUrlSetupDialog(false);
            return;
        }
        webView.loadUrl(buildStreamlitUrl(pageKey));
    }

    private String buildStreamlitUrl(String pageKey) {
        try {
            Uri base = Uri.parse(homeUrl);
            Uri.Builder builder = base.buildUpon()
                    .clearQuery()
                    .appendQueryParameter("embed", "true");
            if (pageKey != null && !pageKey.trim().isEmpty()) {
                builder.appendQueryParameter("page", pageKey);
            }
            return builder.build().toString();
        } catch (Exception e) {
            String suffix = "?embed=true";
            if (pageKey != null && !pageKey.trim().isEmpty()) {
                suffix += "&page=" + pageKey;
            }
            return homeUrl + suffix;
        }
    }

    private Button makeBarButton(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(20);
        b.setTextColor(Color.WHITE);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setAllCaps(false);
        b.setPadding(dp(4), 0, dp(4), 0);
        b.setMinWidth(0);
        b.setMinimumWidth(0);
        b.setMinHeight(0);
        b.setMinimumHeight(0);
        b.setLayoutParams(new LinearLayout.LayoutParams(dp(52), dp(48)));
        return b;
    }

    private void configureWebView() {
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setUserAgentString(s.getUserAgentString() + " PSXIntelligenceAndroid/1.0");

        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (uri == null) return false;
                String scheme = uri.getScheme();
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    if (isSameHost(uri.toString(), homeUrl)) return false;
                    openExternal(uri);
                    return true;
                }
                openExternal(uri);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
                progressBar.setProgress(newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (filePathCallback != null) filePathCallback.onReceiveValue(null);
                filePathCallback = callback;
                Intent intent;
                try {
                    intent = params.createIntent();
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }
                try {
                    startActivityForResult(intent, FILE_CHOOSER_REQUEST);
                } catch (ActivityNotFoundException e) {
                    filePathCallback = null;
                    Toast.makeText(MainActivity.this, "No file picker is available.", Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("PSX Intelligence")
                        .setMessage(message)
                        .setPositiveButton("OK", (d, w) -> result.confirm())
                        .setCancelable(false)
                        .show();
                return true;
            }
        });

        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            try {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                request.addRequestHeader("User-Agent", userAgent);
                String cookies = CookieManager.getInstance().getCookie(url);
                if (cookies != null) request.addRequestHeader("Cookie", cookies);
                String filename = android.webkit.URLUtil.guessFileName(url, contentDisposition, mimeType);
                request.setTitle(filename);
                request.setDescription("Downloading from PSX Intelligence");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(this, "Downloading to Downloads", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                openExternal(Uri.parse(url));
            }
        });
    }

    private void showUrlSetupDialog(boolean cancelable) {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint("https://your-app.streamlit.app");
        input.setText(homeUrl == null ? "" : homeUrl);
        int pad = dp(18);
        LinearLayout wrap = new LinearLayout(this);
        wrap.setPadding(pad, dp(6), pad, 0);
        wrap.addView(input, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Connect your PSX bot")
                .setMessage("Paste the public Streamlit Cloud URL of your PSX bot.")
                .setView(wrap)
                .setPositiveButton("Save & Open", null)
                .setNegativeButton(cancelable ? "Cancel" : "Exit", (d, w) -> {
                    if (!cancelable) finish();
                })
                .setNeutralButton("Clear", null)
                .create();
        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String url = normalizeUrl(input.getText().toString().trim());
                if (!isValidHttpUrl(url)) {
                    input.setError("Enter a valid https:// Streamlit URL");
                    return;
                }
                homeUrl = url;
                getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString(KEY_URL, url).apply();
                dialog.dismiss();
                loadHome();
            });
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                input.setText("");
                getSharedPreferences(PREFS, MODE_PRIVATE).edit().remove(KEY_URL).apply();
            });
        });
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    private void loadHome() {
        if (homeUrl == null || homeUrl.trim().isEmpty()) {
            showUrlSetupDialog(false);
            return;
        }
        if (!hasInternet()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
        webView.loadUrl(buildStreamlitUrl(null));
    }

    private boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    private void openExternal(Uri uri) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open this link", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isSameHost(String a, String b) {
        if (a == null || b == null) return false;
        try {
            String ha = new URI(a).getHost();
            String hb = new URI(b).getHost();
            return ha != null && hb != null && ha.equalsIgnoreCase(hb);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private boolean isValidHttpUrl(String value) {
        if (value == null || value.isEmpty()) return false;
        try {
            URI uri = new URI(value);
            String scheme = uri.getScheme();
            return ("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme)) && uri.getHost() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return "";
        String trimmed = url.trim();
        if (!trimmed.isEmpty() && !trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            trimmed = "https://" + trimmed;
        }
        while (trimmed.endsWith("/")) trimmed = trimmed.substring(0, trimmed.length() - 1);
        return trimmed;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_REQUEST) {
            if (filePathCallback == null) return;
            Uri[] results = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
            filePathCallback.onReceiveValue(results);
            filePathCallback = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
