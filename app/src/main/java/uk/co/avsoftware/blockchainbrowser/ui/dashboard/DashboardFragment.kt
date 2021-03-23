package uk.co.avsoftware.blockchainbrowser.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import uk.co.avsoftware.blockchainbrowser.R
import uk.co.avsoftware.blockchainbrowser.databinding.FragmentDashboardBinding
import uk.co.avsoftware.blockchainbrowser.ui.dashboard.recycler.BlockDataAdapter

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var viewBinding: FragmentDashboardBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewModel = dashboardViewModel

        setUpRecyclerView()

        return viewBinding.root
    }

    private fun setUpRecyclerView() {
        // bind RecyclerView
        val recyclerView: RecyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Set Adapter
        val adapter = BlockDataAdapter(emptyList())
        viewBinding.recyclerView.adapter = adapter
        dashboardViewModel.latestBlock.observe(viewLifecycleOwner, {
            adapter.transactions = it.tx
            adapter.notifyDataSetChanged()
        })
    }
}