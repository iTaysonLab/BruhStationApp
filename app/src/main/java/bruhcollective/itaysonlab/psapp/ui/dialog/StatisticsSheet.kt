package bruhcollective.itaysonlab.psapp.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bruhcollective.itaysonlab.psapp.R
import bruhcollective.itaysonlab.psapp.core.models.dashboard.DashboardGameItem
import bruhcollective.itaysonlab.psapp.databinding.SheetStatsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StatisticsSheet: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = SheetStatsBinding.bind(view)
        val data = requireArguments().getParcelable<DashboardGameItem>("gameItem")!!

        b.gameTitle.text = data.name
        b.timeSpent.text = data.playDuration
        b.dates.text = "First started: ${data.firstPlayedDateTime}\nLast started: ${data.lastPlayedDateTime}\n\nLaunch count: ${data.playCount}"
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): StatisticsSheet {
            val fragment = StatisticsSheet()
            fragment.arguments = bundle
            return fragment
        }
    }
}