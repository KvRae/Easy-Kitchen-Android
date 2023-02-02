package devsec.app.easykitchen.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import devsec.app.easykitchen.R


class GuidePageOneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_page_one, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nextBtn = view?.findViewById<TextView>(R.id.nextTV)
        nextBtn?.setOnClickListener {
            val fragment = GuidePageTwoFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction?.commit()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}