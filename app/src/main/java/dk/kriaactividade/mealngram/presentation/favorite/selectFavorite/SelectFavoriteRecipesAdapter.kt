package dk.kriaactividade.mealngram.presentation.favorite.selectFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.kriaactividade.mealngram.BR
import dk.kriaactividade.mealngram.databinding.ItemRecyclerSelectFavoriteRecipesBinding

class SelectFavoriteRecipesAdapter : ListAdapter<SelectFavoriteItem, SelectFavoriteRecipesAdapter.SelectItemViewHolder>(SelectFavoriteRecipesAdapter) {

    inner class SelectItemViewHolder(private val itemRecycler: ItemRecyclerSelectFavoriteRecipesBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(recipesSelected: SelectFavoriteItem) {
            itemRecycler.setVariable(BR.selectFavoriteRecipe, recipesSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemRecyclerSelectFavoriteRecipesBinding.inflate(layoutInflater)
        return SelectItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectItemViewHolder, position: Int) {
        val recipesSelected = getItem(position)
        holder.binding(recipesSelected)
    }

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<SelectFavoriteItem>() {
        override fun areItemsTheSame(
            oldItem: SelectFavoriteItem,
            newItem: SelectFavoriteItem
        ): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(
            oldItem: SelectFavoriteItem,
            newItem: SelectFavoriteItem
        ): Boolean {
            return newItem.equals(oldItem)
        }
    }
}