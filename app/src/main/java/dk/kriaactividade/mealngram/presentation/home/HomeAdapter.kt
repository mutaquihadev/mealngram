package dk.kriaactividade.mealngram.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectx.utils.Extension.gone
import com.example.projectx.utils.Extension.visible
import dk.kriaactividade.mealngram.repository.remote.RecipesResponse
import dk.kriaactividade.mealngram.presentation.utils.Observable
import com.google.android.material.chip.Chip
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding

class HomeAdapter(
    private val context: Context,
    private val listRecipes: MutableList<RecipesResponse>,
    private val listener: ClickToDetailsRecipes
) : ListAdapter<RecipesResponse, HomeAdapter.HomeItemViewHolder>(DiffUtilHome()) {
    private var isOpen = false
    private var isMark = ""
    private var isActive = true
    private var isClick = true
    var newValue = 0
    val listChip = mutableListOf<Chip>()

    inner class HomeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(recipesResponse: RecipesResponse) {
            item.titleRecipe.text = recipesResponse.name
            item.descriptionRecipe.text = recipesResponse.description
            item.foodImage.load(recipesResponse.imagesUrl[0])

            item.noFavorite.setOnClickListener {
                item.noFavorite.gone()
                item.favorite.visible()
            }
            item.favorite.setOnClickListener {
                item.favorite.gone()
                item.noFavorite.visible()
            }
            if (isOpen) {
                item.chipGroup.visible()
            } else {
                item.chipGroup.gone()
            }


            listChip.add(item.monday)
            listChip.add(item.tuesday)
            listChip.add(item.wednesday)
            listChip.add(item.thursday)
            listChip.add(item.friday)
            listChip.add(item.saturday)
            listChip.add(item.sunday)

            for (value in 0 until listChip.size) {
                clickToUpdateProgress(listChip[value])
                if (isMark == listChip[value].text) {
                    markItemChip(listChip[value])
                    if (!isActive){
                        umMarkItemChip(listChip[value])
                    }
                }
            }
        }
    }


    private fun clickToUpdateProgress(chip: Chip) {
        chip.apply {
            setOnClickListener {
                isChecked(isChecked, 15, chip.text.toString())
            }
        }
    }

     fun verifyChip(chipActive:Boolean){
        isActive = chipActive
         notifyDataSetChanged()
    }

    private fun umMarkItemChip(chip: Chip) {
        when (chip.text) {
            "M" -> chip.isChecked = false
            "T" -> chip.isChecked = false
            "W" -> chip.isChecked = false
            "Th" -> chip.isChecked = false
            "F" -> chip.isChecked = false
            "Sa" -> chip.isChecked = false
            "Su" -> chip.isChecked = false
        }
    }

    private fun markItemChip(chip: Chip) {
        when (chip.text) {
            "M" -> chip.isChecked = true
            "T" -> chip.isChecked = true
            "W" -> chip.isChecked = true
            "Th" -> chip.isChecked = true
            "F" -> chip.isChecked = true
            "Sa" -> chip.isChecked = true
            "Su" -> chip.isChecked = true
        }
    }

    private fun isChecked(check: Boolean, value: Int, text: String) {
        if (check) {
            newValue += value
            Observable.getValueProgress(newValue)
            Observable.setVisibilityChip(text)
            Observable.activeChipIsTrue(check)
        } else {
            newValue -= value
            Observable.getValueProgress(newValue)
            Observable.setVisibilityChip(text)
            Observable.activeChipIsTrue(check)
        }
    }

    fun openMark(openOrClose: Boolean) {
        isOpen = openOrClose
        notifyDataSetChanged()
    }

    fun dayCheck(check: String) {
        isMark = check
        notifyDataSetChanged()
    }

    fun isClickable(click:Boolean){
        isClick = click
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val myView = ItemRecyclerHomeBinding.inflate(layoutInflater)
        return HomeItemViewHolder(myView)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val recipes = listRecipes[position]
        holder.binding(recipes)
        if (isClick){
            holder.itemView.isClickable = true
            holder.itemView.setOnClickListener {
                listener.detailsRecipes(recipes)
            }
        }else{
            holder.itemView.isClickable = false
        }
    }

    override fun getItemCount(): Int {
        return listRecipes.size
    }
}

class DiffUtilHome : DiffUtil.ItemCallback<RecipesResponse>() {
    override fun areItemsTheSame(oldItem: RecipesResponse, newItem: RecipesResponse): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: RecipesResponse, newItem: RecipesResponse): Boolean {
        return newItem == oldItem
    }

}

interface ClickToDetailsRecipes{
    fun detailsRecipes(recipesResponse: RecipesResponse)
}
