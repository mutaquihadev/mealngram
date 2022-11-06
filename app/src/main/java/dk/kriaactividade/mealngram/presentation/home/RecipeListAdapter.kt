package dk.kriaactividade.mealngram.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dk.kriaactividade.mealngram.data.domain.Recipe
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding

class RecipeListAdapter(
  private val context: Context,
  private val onRecipeClicked: (Recipe) -> Unit
) : ListAdapter< Recipe, RecipeListAdapter.RecipeItemViewHolder>(RecipeListAdapter) {

    inner class RecipeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipe: Recipe) {
            item.titleRecipe.text = recipe.name
            item.descriptionRecipe.text = recipe.description
            item.foodImage.load(recipe.mainImage)
            item.chipGroup.isVisible = recipe.isSelectionMode
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