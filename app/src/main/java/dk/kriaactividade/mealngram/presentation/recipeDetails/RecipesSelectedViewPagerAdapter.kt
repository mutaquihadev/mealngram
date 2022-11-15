package dk.kriaactividade.mealngram.presentation.recipeDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import com.google.android.material.chip.Chip
import dk.kriaactividade.mealngram.data.domain.DetailsRecipes
import dk.kriaactividade.mealngram.databinding.ItemViewPagerRecipesSelectedBinding
import dk.kriaactividade.mealngram.repository.remote.RecipeDTO

class RecipesSelectedViewPagerAdapter(
    private val context: Context,
    private val listRecipesSelected: List<DetailsRecipes>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = ItemViewPagerRecipesSelectedBinding.inflate(layoutInflater)
        val item = listRecipesSelected[position]
        setupCard(itemView,item)
        container.addView(itemView.root)
        return itemView.root
    }

    private fun setupCard(layout: ItemViewPagerRecipesSelectedBinding, item:DetailsRecipes){
            layout.imageRecipes.load(item.image)
        layout.titleRecipe.text = item.name
        layout.descriptionDetails.text = item.description
    }

    override fun getCount(): Int {
       return listRecipesSelected.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}