package uk.co.avsoftware.blockchainbrowser.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uk.co.avsoftware.blockchainbrowser.R
import uk.co.avsoftware.blockchainbrowser.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var viewBinding: FragmentDashboardBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewmodel = dashboardViewModel

        return viewBinding.root
    }
}