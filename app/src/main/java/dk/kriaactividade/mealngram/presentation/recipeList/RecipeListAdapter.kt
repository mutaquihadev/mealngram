package dk.kriaactividade.mealngram.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dk.kriaactividade.mealngram.R
import dk.kriaactividade.mealngram.data.domain.WEEK
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding

class RecipeListAdapter(
    private val context: Context,
    private val onChipClicked: (recipeId: Int, weekDay: WEEK, selectedState: Boolean) -> Unit
) : ListAdapter<RecipeItem, RecipeListAdapter.RecipeItemViewHolder>(RecipeListAdapter) {

    inner class RecipeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipeItem: RecipeItem) {
            item.setVariable(BR.recipe, recipeItem)
            //binding.setVariable(BR.capsule, capsule)
//            item.titleRecipe.text = recipeItem.name
//            item.descriptionRecipe.text = recipeItem.description
//            item.foodImage.load(recipeItem.image)
//            item.chipGroup.isVisible = recipeItem.isSelectionMode
//
//            item.daysOfTheWeek.removeAllViews()
//            item.daysOfTheWeek.setChipSpacing(2)
//            recipeItem.selectedDays.forEachIndexed {  index, chipState ->
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
//                chip.setOnCheckedChangeListener { compoundButton, b ->
//
//                }
//                //chip.setOnClickListener { onChipClicked(chipState.id, chipState.week, chipState.isActive) }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val myView = ItemRecyclerHomeBinding.inflate(layoutInflater)
        return RecipeItemViewHolder(myView)
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val recipe: RecipeItem = getItem(position)
        holder.binding(recipe)
    }

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<RecipeItem>() {
        override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeItem,
            newItem: RecipeItem
        ): Boolean {
            return newItem == oldItem
        }
    }
}