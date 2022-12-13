package dk.kriaactividade.mealngram.presentation.bindings

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.presentation.favorite.selectFavorite.SelectFavoriteItem
import dk.kriaactividade.mealngram.presentation.favorite.selectFavorite.SelectFavoriteRecipesViewActions
import dk.kriaactividade.mealngram.presentation.recipeList.RecipeListViewModelItemActions
import dk.kriaactividade.mealngram.presentation.recipeList.SelectedChipState
import dk.kriaactividade.mealngram.presentation.utils.visible

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

        chip.text = chipState.week.label
        chip.isCheckable = chipState.isSelectable
        chip.isChecked = chipState.isChecked
        chip.isEnabled = chipState.isSelectable

        chip.setOnClickListener {
            actions.onDaySelected(
                date = chipState.date,
                recipeId = chipState.id,
                weekDay = chipState.week
            )
        }
    }
}