package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.kriaactividade.mealngram.BR
import dk.kriaactividade.mealngram.databinding.ItemRecyclerRecipesSelectedBinding

class RecipesSelectedAdapter :
    ListAdapter<RecipesSelectedItem, RecipesSelectedAdapter.ItemViewHolder>(RecipesSelectedAdapter) {
    inner class ItemViewHolder(private val itemRecycler: ItemRecyclerRecipesSelectedBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(recipesSelected: RecipesSelectedItem) {
            itemRecycler.setVariable(BR.recipeDetails, recipesSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemRecyclerRecipesSelectedBinding.inflate(layoutInflater)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipesSelected = getItem(position)
        holder.binding(recipesSelected)
    }

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<RecipesSelectedItem>() {
        override fun areItemsTheSame(
            oldItem: RecipesSelectedItem,
            newItem: RecipesSelectedItem
        ): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(
            oldItem: RecipesSelectedItem,
            newItem: RecipesSelectedItem
        ): Boolean {
            return newItem.equals(oldItem)
        }
    }
}