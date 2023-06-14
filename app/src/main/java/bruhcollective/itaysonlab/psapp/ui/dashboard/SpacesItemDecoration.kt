package bruhcollective.itaysonlab.psapp.ui.dashboard

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildLayoutPosition(view)
        if (pos % 2 == 0) {
            outRect.left = space
            outRect.right = space / 2
        } else {
            outRect.left = space / 2
            outRect.right = space
        }
        outRect.bottom = space
        outRect.top = if (pos == 0 || pos == 1) space / 2 else 0
    }
}