package fr.isen.meketyn.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.meketyn.androiderestaurant.databinding.FragmentPageBinding

class PageFragment: Fragment() {

    private lateinit var binding: FragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("URL")
        if(url?.isNotEmpty() == true) {
            Picasso.get().load(url).placeholder(R.drawable.ic_launcher_foreground).into(binding.imageFragment)
        }
    }

    companion object {
        fun newInstance(url: String): PageFragment {
            return PageFragment().apply { arguments = Bundle().apply { putString("URL", url) } }
        }
    }
}