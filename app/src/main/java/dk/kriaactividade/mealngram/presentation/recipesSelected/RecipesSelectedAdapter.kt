package dk.kriaactividade.mealngram.presentation.recipesSelected

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dk.kriaactividade.mealngram.data.domain.RecipesSelected
import dk.kriaactividade.mealngram.databinding.ItemRecyclerRecipesSelectedBinding
import dk.kriaactividade.mealngram.databinding.SectionHeaderSelectedRecipesBinding
import dk.kriaactividade.mealngram.presentation.utils.Util.getWeek

class RecipesSelectedAdapter(
    private val onShowList: (Boolean) -> Unit
):
    ListAdapter<RecipesSelected, RecyclerView.ViewHolder>(RecipesSelectedAdapter) {

    val VIEW_TYPE_HEADER = 0
    val VIEW_TYPE_ITEM = 1

    inner class ItemViewHolder(private val itemRecycler: ItemRecyclerRecipesSelectedBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(detailsRecipes: RecipesSelected){
            itemRecycler.textRecipeSelected.text = detailsRecipes.name
            itemRecycler.imageRecipeSelected.load(detailsRecipes.image)
            itemRecycler.root.isVisible = detailsRecipes.isOpen
        }
    }

    inner class HeaderViewHolder(private val itemRecycler: SectionHeaderSelectedRecipesBinding) :
        RecyclerView.ViewHolder(itemRecycler.root) {
        fun binding(detailsRecipes: RecipesSelected){
            itemRecycler.titleSection.text = getWeek()
            itemRecycler.buttonSectionOpen.isVisible = !detailsRecipes.isOpen
            itemRecycler.buttonSectionClose.isVisible = detailsRecipes.isOpen
            itemRecycler.buttonSectionClose.setOnClickListener {
                onShowList(false)
            }
            itemRecycler.buttonSectionOpen.setOnClickListener {
                onShowList(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER){
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = SectionHeaderSelectedRecipesBinding.inflate(layoutInflater)
            HeaderViewHolder(view)
        }else{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemRecyclerRecipesSelectedBinding.inflate(layoutInflater)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recipesSelected = getItem(position)
        if (holder is HeaderViewHolder){
            holder.binding(recipesSelected)
        }
        if (holder is ItemViewHolder){
            holder.binding(recipesSelected)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            return VIEW_TYPE_HEADER
        } else {
            return VIEW_TYPE_ITEM
        }
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