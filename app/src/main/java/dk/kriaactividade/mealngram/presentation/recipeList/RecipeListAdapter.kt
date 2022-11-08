package dk.kriaactividade.mealngram.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dk.kriaactividade.mealngram.data.domain.ChipState
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding

class RecipeListAdapter(
  private val context: Context,
  private val onRecipeClicked: (Recipe) -> Unit,
  private val onChipClicked: (Int, ChipState) -> Unit
) : ListAdapter< Recipe, RecipeListAdapter.RecipeItemViewHolder>(RecipeListAdapter) {

    inner class RecipeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipe: Recipe) {
            item.titleRecipe.text = recipe.name
            item.descriptionRecipe.text = recipe.description
            item.foodImage.load(recipe.mainImage)
            item.chipGroup.isVisible = recipe.isSelectionMode

            if(recipe.isSelectionMode){
                item.monday.isVisible = recipe.dayOfWeekSelectedPair[0].isVisible
                item.monday.isChecked = recipe.dayOfWeekSelectedPair[0].isActive
                item.monday.setOnClickListener {
                    onChipClicked(recipe.id, recipe.dayOfWeekSelectedPair[0])
                }

                item.tuesday.isChecked = recipe.dayOfWeekSelectedPair[1].isActive
                item.wednesday.isChecked = recipe.dayOfWeekSelectedPair[2].isActive
                item.thursday.isChecked = recipe.dayOfWeekSelectedPair[3].isActive
                item.friday.isChecked = recipe.dayOfWeekSelectedPair[4].isActive
                item.saturday.isChecked = recipe.dayOfWeekSelectedPair[5].isActive
                item.sunday.isChecked = recipe.dayOfWeekSelectedPair[6].isActive
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