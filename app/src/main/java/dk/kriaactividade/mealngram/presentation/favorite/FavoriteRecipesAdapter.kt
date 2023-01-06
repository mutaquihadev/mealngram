package dk.kriaactividade.mealngram.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.kriaactividade.mealngram.BR
import dk.kriaactividade.mealngram.database.room.RecipeEntity
import dk.kriaactividade.mealngram.databinding.ItemRecyclerFavoriteRecipesBinding

class FavoriteRecipesAdapter : ListAdapter<RecipeEntity, FavoriteRecipesAdapter.SelectItemViewHolder>(FavoriteRecipesAdapter) {

    inner class SelectItemViewHolder(private val itemRecycler: ItemRecyclerFavoriteRecipesBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(recipesSelected: RecipeEntity) {
            itemRecycler.setVariable(BR.recipeRoom, recipesSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemRecyclerFavoriteRecipesBinding.inflate(layoutInflater)
        return SelectItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectItemViewHolder, position: Int) {
        val recipesSelected = getItem(position)
        holder.binding(recipesSelected)
    }

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<RecipeEntity>() {
        override fun areItemsTheSame(
            oldItem: RecipeEntity,
            newItem: RecipeEntity
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeEntity,
            newItem: RecipeEntity
        ): Boolean {
            return newItem.equals(oldItem)
        }
    }
}