package bruhcollective.itaysonlab.psapp.core.models

interface UnifiedGameItem: MarkableWithId {
    fun getCusa(): String
    fun getIconUrl(): String
    override fun getItemId() = getCusa()
}