package bruhcollective.itaysonlab.psapp.core.api.impl

import bruhcollective.itaysonlab.psapp.core.models.dashboard.DashboardRequestResult
import bruhcollective.itaysonlab.psapp.core.models.entitlement.EntitlementsRequestResult
import bruhcollective.itaysonlab.psapp.core.models.profile.UserProfile
import bruhcollective.itaysonlab.psapp.core.models.trophy.TrophyRequestResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainPsnApi {
    @GET("userProfile/v1/internal/users/{uid}/profiles")
    suspend fun getProfile(@Path("uid") uid: String): UserProfile

    @GET("gamelist/v2/users/{uid}/titles")
    suspend fun getDashboardGameList(
        @Path("uid") uid: String,
        @Query("categories") categories: String, // ps4_game,ps5_native_game
        @Query("fields") fields: String, // media
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): DashboardRequestResult

    @GET("entitlement/v2/users/me/internal/entitlements")
    suspend fun getEntitlements(
        @Query("gameMetaPackageType") packageTypes: String, // PSGD,PS4GD
        @Query("fields") fields: String, // titleMeta,gameMeta,conceptMeta
        @Query("isActive") active: Boolean, // titleMeta,gameMeta,conceptMeta
        @Query("sortBy") sortBy: String, // title_name
        @Query("sortDirection") sortDirection: String, // asc (desc?)
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): EntitlementsRequestResult

    @GET("catalog/v2/titles/{cusa}/concepts")
    suspend fun getCatalog(
        @Path("cusa") cusas: String, // 1,2,3,4
        @Query("age") age: Int, // mostly used for verification
        @Query("country") country: String, // en/ru
        @Query("language") language: String, // en/ru
    ): DashboardRequestResult

    @GET("trophy/v1/users/{uid}/titles/trophyTitles")
    suspend fun getTrophies(
        @Path("uid") uid: String,
        @Query("npTitleIds") cusa_ids: String
    ): TrophyRequestResult
}