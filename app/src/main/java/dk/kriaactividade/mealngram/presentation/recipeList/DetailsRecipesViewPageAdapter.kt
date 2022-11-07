package dk.kriaactividade.mealngram.presentation.recipeList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import dk.kriaactividade.mealngram.databinding.LayoutViewPageBinding

class DetailsRecipesViewPageAdapter(
    private val context: Context,
    private val listImageRecipes: List<String>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val cardImage = LayoutViewPageBinding.inflate(layoutInflater)
        val item = listImageRecipes[position]
        setupImage(cardImage, item)
        container.addView(cardImage.root)
        return cardImage.root
    }

    private fun setupImage(images: LayoutViewPageBinding, item: String){
        images.imageVp.load(item)

    }
    override fun getCount(): Int {
        return listImageRecipes.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}