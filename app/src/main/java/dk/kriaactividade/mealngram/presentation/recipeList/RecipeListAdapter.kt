package dk.kriaactividade.mealngram.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.Chip
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.data.domain.WEEK
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding
import dk.kriaactividade.mealngram.presentation.utils.Util
import dk.kriaactividade.mealngram.presentation.utils.Util.getCurrentDate
import dk.kriaactividade.mealngram.presentation.utils.Util.getDay

class RecipeListAdapter(
  private val context: Context,
  private val onRecipeClicked: (Recipe) -> Unit,
  private val onChipClicked: (recipeId: Int, weekDay:WEEK, selectedState: Boolean) -> Unit
) : ListAdapter< Recipe, RecipeListAdapter.RecipeItemViewHolder>(RecipeListAdapter) {

    private var count = 0

    inner class RecipeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipe: Recipe) {
            item.titleRecipe.text = recipe.name
            item.descriptionRecipe.text = recipe.description
            item.foodImage.load(recipe.image)
            item.chipGroup.isVisible = recipe.isSelectionMode

            if(recipe.isSelectionMode){
               item.daysOfTheWeek.removeAllViews()
                recipe.dayOfWeekSelectedPair.forEach { chipState ->
                    val chip = Chip(context)
                    if (getDay(chipState.dayOfWeek.id) >= getCurrentDate()){
                        item.daysOfTheWeek.addView(chip)
                    }
                    chip.isChecked = chipState.isActive
                    chip.isVisible = chipState.isVisible
                    chip.text = chipState.dayOfWeek.label


                    chip.setOnClickListener { onChipClicked(chipState.id, chipState.dayOfWeek, chipState.isActive) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val myView = ItemRecyclerHomeBinding.inflate(layoutInflater)
        return RecipeItemViewHolder(myView)
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.binding(recipe)
        if (!recipe.isSelectionMode){
            holder.itemView.setOnClickListener { onRecipeClicked(recipe) }
        }
    }

   private companion object DiffUtilCallBack : DiffUtil.ItemCallback<Recipe>(){
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: Recipe,
            newItem: Recipe
        ): Boolean {
            return newItem == oldItem
        }
    }
}