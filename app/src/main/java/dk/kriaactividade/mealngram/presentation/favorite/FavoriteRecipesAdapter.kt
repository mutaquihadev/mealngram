package dk.kriaactividade.mealngram.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.kriaactividade.mealngram.BR
import dk.kriaactividade.mealngram.database.room.RecipeRoomItem
import dk.kriaactividade.mealngram.databinding.ItemRecyclerFavoriteRecipesBinding
import dk.kriaactividade.mealngram.databinding.ItemRecyclerSelectFavoriteRecipesBinding
import dk.kriaactividade.mealngram.presentation.favorite.selectFavorite.SelectFavoriteItem

class FavoriteRecipesAdapter : ListAdapter<RecipeRoomItem, FavoriteRecipesAdapter.SelectItemViewHolder>(FavoriteRecipesAdapter) {

    inner class SelectItemViewHolder(private val itemRecycler: ItemRecyclerFavoriteRecipesBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(recipesSelected: RecipeRoomItem) {
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

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<RecipeRoomItem>() {
        override fun areItemsTheSame(
            oldItem: RecipeRoomItem,
            newItem: RecipeRoomItem
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeRoomItem,
            newItem: RecipeRoomItem
        ): Boolean {
            return newItem.equals(oldItem)
        }
    }
}