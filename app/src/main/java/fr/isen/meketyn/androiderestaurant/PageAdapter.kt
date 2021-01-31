package fr.isen.meketyn.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.squareup.picasso.Picasso
import fr.isen.meketyn.androiderestaurant.databinding.ItemSimpleItemviewBinding

class PageAdapter(fa: AppCompatActivity, private val images: List<String>): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return images.count()
    }

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(images[position])
    }
}