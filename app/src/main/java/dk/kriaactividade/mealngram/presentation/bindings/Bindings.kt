package dk.kriaactividade.mealngram.presentation.bindings

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.entities.domain.chip.SelectedChipState
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListViewModelItemActions
import dk.kriaactividade.mealngram.entities.ui.selectfavoriterecipe.SelectFavoriteRecipesViewActions

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    load(url)
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter(value = ["bind:getId", "bind:onGetId"])
fun ImageView.favorite(selectId:Int,actions: SelectFavoriteRecipesViewActions){
    this.setOnClickListener {
        actions.onFavoriteItem(selectId)
    }
}

@BindingAdapter(value = ["bind:selectableDays", "bind:onDaySelected"])
fun ChipGroup.setSelectableDays(
    selectableDays: List<SelectedChipState>,
    actions: RecipeListViewModelItemActions
) {
    this.removeAllViews()
    this.setChipSpacing(2)
    selectableDays.forEach { chipState ->
        val chip = Chip(context, null, R.attr.CustomChipChoiceStyle)
        this.addView(chip)

        chip.text = chipState.date.toString()
        chip.isCheckable = chipState.isSelectable
        chip.isChecked = chipState.isChecked
        chip.isEnabled = chipState.isSelectable

        chip.setOnClickListener { actions.onSelectableDaySelected(selectableDay = chipState) }
    }
}