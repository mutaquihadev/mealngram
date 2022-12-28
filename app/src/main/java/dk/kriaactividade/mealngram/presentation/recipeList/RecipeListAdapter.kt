package dk.kriaactividade.mealngram.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding
import dk.kriaactividade.mealngram.entities.domain.recipe.RecipeItem
import dk.kriaactividade.mealngram.entities.ui.recipeList.RecipeListViewModelItemActions

class RecipeListAdapter(
    private val context: Context,
    private val recipeItemActions: RecipeListViewModelItemActions,
) : ListAdapter<RecipeItem, RecipeListAdapter.RecipeItemViewHolder>(RecipeListAdapter) {

    inner class RecipeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipeItem: RecipeItem) {
            item.setVariable(BR.recipe, recipeItem)
            item.setVariable(BR.actions, recipeItemActions)
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