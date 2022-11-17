package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.databinding.ItemRecyclerRecipesSelectedBinding

class RecipesSelectedAdapter(private val context: Context) :
    ListAdapter<RecipesSelected, RecipesSelectedAdapter.ItemViewHolder>(RecipesSelectedAdapter) {
    inner class ItemViewHolder(private val itemRecycler: ItemRecyclerRecipesSelectedBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(detailsRecipes: RecipesSelected){
            itemRecycler.textRecipeSelected.text = detailsRecipes.name
            itemRecycler.imageRecipeSelected.load(detailsRecipes.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = ItemRecyclerRecipesSelectedBinding.inflate(layoutInflater)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipesSelected = getItem(position)
        holder.binding(recipesSelected)
    }

    private companion object DiffUtilCallBack : DiffUtil.ItemCallback<RecipesSelected>() {
        override fun areItemsTheSame(oldItem: RecipesSelected, newItem: RecipesSelected): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(
            oldItem: RecipesSelected,
            newItem: RecipesSelected
        ): Boolean {
            return newItem.equals(oldItem)
        }
    }
}