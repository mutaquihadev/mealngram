package dk.kriaactividade.mealngram.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
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
    private var isMark = ""
    private var isActive = true
    var chipPosition = 0
    var newValue = 0
    val listChip = mutableListOf<Chip>()

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
