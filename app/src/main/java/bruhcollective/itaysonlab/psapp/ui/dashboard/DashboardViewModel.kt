package bruhcollective.itaysonlab.psapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import bruhcollective.itaysonlab.psapp.core.api.SonyApiFactory
import bruhcollective.itaysonlab.psapp.data.repository.DashboardPagingSource
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import bruhcollective.itaysonlab.psapp.prefs.BSPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DashboardViewModel: ViewModel() {
    val prsRecent = DashboardPagingSource(SonyApiFactory.main, false)
    val prsOwned = DashboardPagingSource(SonyApiFactory.main, true)

    val pagerRecentFlow = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        prsRecent
    }.flow.cachedIn(viewModelScope)

    val pagerOwnedFlow = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        prsOwned
    }.flow.cachedIn(viewModelScope)
}