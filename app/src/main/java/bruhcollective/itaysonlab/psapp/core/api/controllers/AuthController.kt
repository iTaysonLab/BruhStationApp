package bruhcollective.itaysonlab.psapp.core.api.controllers

import android.content.Context
import bruhcollective.itaysonlab.psapp.BSApplication
import bruhcollective.itaysonlab.psapp.core.ApiConstants
import bruhcollective.itaysonlab.psapp.core.api.SonyApiFactory
import bruhcollective.itaysonlab.psapp.core.models.auth.SsoCookieRequest
import bruhcollective.itaysonlab.psapp.core.models.auth.SsoCookieResponse
import bruhcollective.itaysonlab.psapp.core.models.auth.SsoTokenResponse
import bruhcollective.itaysonlab.psapp.log.LoggingFacade
import bruhcollective.itaysonlab.psapp.core.internal.duid.DuidGeneratorFactory
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object AuthController {
    suspend fun requestToken(npsso: String, code: String, cid: String): SsoTokenResponse {
        return SonyApiFactory.sso.ssoToken("npsso=$npsso", "Basic ${ApiConstants.CA_CLIENT_ID_WEB_READY}", mutableMapOf(
            "token_format" to "jwt",
            "redirect_uri" to "com.scee.psxandroid.scecompcall://redirect",
            "grant_type" to "authorization_code",
            "code" to code,
            "cid" to cid,
        ))
    }

    suspend fun requestRefreshedToken(npsso: String, refreshToken: String): SsoTokenResponse {
        return SonyApiFactory.sso.ssoToken("npsso=$npsso", "Basic ${ApiConstants.CA_CLIENT_ID_WEB_READY}", mutableMapOf(
            Pair("scope", "psn:mobile.v2.core psn:clientapp"),
            Pair("refresh_token", refreshToken),
            Pair("token_format", "jwt"),
            Pair("grant_type", "refresh_token")
        ))
    }

    fun buildForm(ctx: Context): Map<String, String> {
        val params = mutableMapOf<String, String>()

        params["elements_visibility"] = "no_aclink"
        params["darkmode"] = "true"
        params["access_type"] = "offline"
        params["enable_scheme_error_code"] = "true"
        params["PlatformPrivacyWs1"] = "minimal"
        params["client_id"] = ApiConstants.CA_CLIENT_ID_WEB
        params["response_type"] = "code"
        params["scope"] = "psn:mobile.v2.core psn:clientapp"
        params["redirect_uri"] = "com.scee.psxandroid.scecompcall://redirect"
        params["service_entity"] = "urn:service-entity:psn"
        params["duid"] = DuidGeneratorFactory.getDuid(ctx)
        params["ui"] = "pr"
        params["support_scheme"] = "sneiprls"
        params["device_profile"] = "mobile" //         String str = getResources().getConfiguration().smallestScreenWidthDp >= 600 ? "tablet" : "mobile";
        params["device_base_font_size"] = "10"
        params["animation"] = "enabled"
        params["service_logo"] = "ps"

        return params
    }
}