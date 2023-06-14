package bruhcollective.itaysonlab.psapp.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bruhcollective.itaysonlab.psapp.MainActivity
import bruhcollective.itaysonlab.psapp.R
import bruhcollective.itaysonlab.psapp.core.api.SonyApiFactory
import bruhcollective.itaysonlab.psapp.core.api.controllers.AuthController
import bruhcollective.itaysonlab.psapp.core.webview.CookieDelegate
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import bruhcollective.itaysonlab.psapp.database.models.DbAuth
import bruhcollective.itaysonlab.psapp.database.models.DbUser
import bruhcollective.itaysonlab.psapp.databinding.FragmentWebAuthBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.*
import kotlin.math.max

class WebAuthFragment: Fragment(R.layout.fragment_web_auth), CoroutineScope by MainScope() {
    private val binding: FragmentWebAuthBinding by viewBinding(FragmentWebAuthBinding::bind)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.web.apply {
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            
            webChromeClient = object: WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    val max = max(5, newProgress)
                    if (max < 100) {
                        binding.progressbar.visibility = View.VISIBLE
                        binding.progressbar.progress = max
                    } else {
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }

            webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    if (request.url.toString().startsWith("com.scee.psxandroid.scecompcall")) {
                        launch(Dispatchers.IO) {
                            val npsso = CookieDelegate.requestNpsso()

                            val l = request.url.toString().removePrefix("com.scee.psxandroid.scecompcall://redirect/?code=").split("&")
                            val code = l[0]
                            val cid = l[1].removePrefix("cid=")

                            val token = withContext(Dispatchers.IO) {
                                AuthController.requestToken(npsso, code, cid)
                            }

                            DatabaseHolder.database.auth().insert(
                                DbAuth(
                                    id = 1,
                                    oauthCode = code,
                                    oauthCid = cid,
                                    accountToken = token.access_token,
                                    accountTokenExpiresIn = token.expires_in,
                                    refreshToken = token.refresh_token,
                                    refreshTokenExpiresIn = token.refresh_token_expires_in,
                                    idToken = token.id_token,
                                    npsso = npsso,
                                    tokenAcqTime = System.currentTimeMillis(),
                                    refreshTokenAcqTime = System.currentTimeMillis(),
                                )
                            )

                            val dms = withContext(Dispatchers.IO) {
                                SonyApiFactory.dms.getMeAndMyDevices()
                            }

                            val profile = withContext(Dispatchers.IO) {
                                SonyApiFactory.main.getProfile(dms.accountId)
                            }

                            DatabaseHolder.database.user().insert(DbUser(
                                accountId = dms.accountId,
                                profile = profile,
                                registeredDevices = dms.accountDevices
                            ))

                            withContext(Dispatchers.Main) {
                                (requireActivity() as MainActivity).refreshSidebar()
                                findNavController().popBackStack()
                                findNavController().navigate(R.id.action_navigation_landing_to_navigation_dashboard)
                            }
                        }

                        return true
                    }

                    if (request.url.toString().startsWith("sneiprls://error")) {
                        findNavController().popBackStack()
                        return true
                    }

                    return super.shouldOverrideUrlLoading(view, request)
                }
            }

            loadUrl("https://ca.account.sony.com/api/authz/v3/oauth/authorize?${AuthController.buildForm(requireActivity()).map { 
                "${it.key}=${it.value}"
            }.joinToString("&")}")
        }
    }
}