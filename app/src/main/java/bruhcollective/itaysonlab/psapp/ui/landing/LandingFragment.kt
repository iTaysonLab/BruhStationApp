package bruhcollective.itaysonlab.psapp.ui.landing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bruhcollective.itaysonlab.psapp.R
import bruhcollective.itaysonlab.psapp.core.api.controllers.AuthController
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import bruhcollective.itaysonlab.psapp.databinding.FragmentLandingBinding
import bruhcollective.itaysonlab.psapp.log.LoggingFacade
import bruhcollective.itaysonlab.psapp.prefs.BSPreferences
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class LandingFragment: Fragment(R.layout.fragment_landing), CoroutineScope by MainScope() {
    private val binding: FragmentLandingBinding by viewBinding(FragmentLandingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.auth.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_landing_to_navigation_auth)
        }

        launch(Dispatchers.IO) {
            val user = DatabaseHolder.database.user().get()
            val data = DatabaseHolder.database.auth().get()

            if (user != null && data != null) {
                LoggingFacade.logDebug("Startup", "[data:token] refreshTokenAcqTime = ${data.refreshTokenAcqTime}, refreshTokenExpiresIn = ${data.refreshTokenExpiresIn}, elapsed = ${(System.currentTimeMillis() - data.refreshTokenAcqTime) / 1000}, shouldLogout = ${(System.currentTimeMillis() - data.refreshTokenAcqTime) / 1000 > data.refreshTokenExpiresIn}")
                LoggingFacade.logDebug("Startup", "[data:refresh] tokenAcqTime = ${data.tokenAcqTime}, accountTokenExpiresIn = ${data.accountTokenExpiresIn}, elapsed = ${(System.currentTimeMillis() - data.tokenAcqTime) / 1000}, shouldRefresh = ${(System.currentTimeMillis() - data.tokenAcqTime) / 1000 > data.accountTokenExpiresIn}")
                LoggingFacade.logDebug("Startup", "[data:refresh] ${data.accountToken}")

                if ((System.currentTimeMillis() - data.refreshTokenAcqTime) / 1000 > data.refreshTokenExpiresIn) {
                    DatabaseHolder.database.auth().delete(DatabaseHolder.database.auth().get()!!)
                    withContext(Dispatchers.Main) {
                        binding.bsLandingProgress.visibility = View.GONE
                        binding.bsLandingRoot.visibility = View.VISIBLE
                        binding.auth.visibility = View.VISIBLE
                    }
                    return@launch
                } else if ((System.currentTimeMillis() - data.tokenAcqTime) / 1000 > data.accountTokenExpiresIn)  {
                    val token = withContext(Dispatchers.IO) {
                        AuthController.requestRefreshedToken(
                            data.npsso,
                            data.refreshToken
                        )
                    }

                    DatabaseHolder.database.auth().update {
                        accountToken = token.access_token
                        accountTokenExpiresIn = token.expires_in
                        refreshToken = token.refresh_token
                        refreshTokenExpiresIn = token.refresh_token_expires_in
                        idToken = token.id_token
                        tokenAcqTime = System.currentTimeMillis()
                        refreshTokenAcqTime = System.currentTimeMillis()
                    }
                }

                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_navigation_landing_to_navigation_dashboard)
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.bsLandingProgress.visibility = View.GONE
                    binding.bsLandingRoot.visibility = View.VISIBLE
                    binding.auth.visibility = View.VISIBLE
                }
            }
        }
    }
}