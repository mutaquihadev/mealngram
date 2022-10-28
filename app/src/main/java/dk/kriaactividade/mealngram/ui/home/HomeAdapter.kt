package dk.kriaactividade.mealngram.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.utils.Extension.gone
import com.example.projectx.utils.Extension.visible
import dk.kriaactividade.mealngram.ui.FoodModel
import dk.kriaactividade.mealngram.utils.Observable
import com.google.android.material.chip.Chip
import dk.kriaactividade.mealngram.databinding.ItemRecyclerHomeBinding

class HomeAdapter(
    private val context: Context,
    private val listFood: MutableList<FoodModel>
) : ListAdapter<FoodModel, HomeAdapter.HomeItemViewHolder>(DiffUtilHome()) {
    private var isOpen = false
    var newValue = 0
    inner class HomeItemViewHolder(private val item: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun binding(foodModel: FoodModel) {
            item.textFood.text = foodModel.text
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
            clickToUpdateProgress(item.monday)
            clickToUpdateProgress(item.tuesday)
            clickToUpdateProgress(item.wednesday)
            clickToUpdateProgress(item.thursday)
            clickToUpdateProgress(item.friday)
            clickToUpdateProgress(item.saturday)
            clickToUpdateProgress(item.sunday)
        }
    }

    private fun clickToUpdateProgress(chip: Chip) {
        chip.apply {
            setOnClickListener {
                isChecked(isChecked,15)
            }
        }
    }

    private fun isChecked(check: Boolean,value:Int) {
        if (check) {
            newValue += value
            Observable.getValueProgress(newValue)
        } else {
            newValue -= value
            Observable.getValueProgress(newValue)
        }
    }

    fun openMark(openOrClose: Boolean) {
        isOpen = openOrClose
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val myView = ItemRecyclerHomeBinding.inflate(layoutInflater)
        return HomeItemViewHolder(myView)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val food = listFood[position]
        holder.binding(food)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }
}

class DiffUtilHome : DiffUtil.ItemCallback<FoodModel>() {
    override fun areItemsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
        return newItem == oldItem
    }

}
