package bruhcollective.itaysonlab.psapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import bruhcollective.itaysonlab.psapp.core.api.SonyApiFactory
import bruhcollective.itaysonlab.psapp.core.api.impl.MainPsnApi
import bruhcollective.itaysonlab.psapp.core.models.UnifiedGameItem
import bruhcollective.itaysonlab.psapp.core.models.dashboard.DashboardGameItem
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DashboardPagingSource (
    private val service: MainPsnApi,
    private val ownedMode: Boolean
): PagingSource<Int, UnifiedGameItem>() {
    private var _uid: String? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnifiedGameItem> {
        try {
            if (_uid == null) _uid = withContext(Dispatchers.IO) { DatabaseHolder.database.user().get()!!.accountId }

            val offset = params.key ?: 0
            val titles: List<UnifiedGameItem>
            val nextOffset: Int?

            if (ownedMode) {
                val r = service.getEntitlements(
                    packageTypes = "PSGD,PS4GD",
                    fields = "titleMeta,gameMeta,conceptMeta",
                    active = true,
                    sortBy = "title_name",
                    sortDirection = "asc",
                    limit = params.loadSize,
                    offset = offset
                )

                titles = r.entitlements
                nextOffset = if ((r.totalResults ?: 0) < offset + params.loadSize) null else offset + params.loadSize
            } else {
                val r = service.getDashboardGameList(_uid!!, "ps4_game,ps5_native_game", "media", params.loadSize, offset)

                titles = r.titles
                nextOffset = r.nextOffset
            }

            return LoadResult.Page(
                data = titles,
                prevKey = null,
                nextKey = nextOffset
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnifiedGameItem>): Int? {
        return state.anchorPosition?.let { anchorPosition -> state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) }
    }
}