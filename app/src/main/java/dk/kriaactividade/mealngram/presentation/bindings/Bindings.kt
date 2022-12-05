package dk.kriaactividade.mealngram.presentation.bindings

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.data.domain.ChipState
import dk.kriaactividade.mealngram.presentation.recipeList.SelectedChipState

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    load(url)
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("selectableDays")
fun ChipGroup.setSelectableDays(selectableDays : List<SelectedChipState>) {
//                val chip = Chip(context, null, R.attr.CustomChipChoiceStyle)
//                item.daysOfTheWeek.addView(chip)
//
//                chip.isCheckable = true
//                chip.elevation = if(index == 0) 0f else 10f
//                //chip.chipStrokeWidth = 6f
//                //chip.chipStrokeColor = ContextCompat.getColorStateList(context, R.color.red)
//
//                //chip.isChecked = chipState.isActive
//                chip.text = chipState.week.label


    this.removeAllViews()
    this.setChipSpacing(2)
    selectableDays.forEach {
        val chip = Chip(context, null, R.attr.CustomChipChoiceStyle)
        this.addView(chip)
        chip.text = it.week.label
    }
//    recipeItem.selectedDays.forEachIndexed { index, chipState ->
//        val chip = Chip(context, null, R.attr.CustomChipChoiceStyle)
//        item.daysOfTheWeek.addView(chip)
//
//        chip.isCheckable = true
//        chip.elevation = if (index == 0) 0f else 10f
//        //chip.chipStrokeWidth = 6f
//        //chip.chipStrokeColor = ContextCompat.getColorStateList(context, R.color.red)
//
//        //chip.isChecked = chipState.isActive
//        chip.text = chipState.week.label
//        chip.setOnCheckedChangeListener { compoundButton, b ->
//
//        }
//        //chip.setOnClickListener { onChipClicked(chipState.id, chipState.week, chipState.isActive) }
//    }
}