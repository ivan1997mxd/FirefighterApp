package com.chentong.firefighterapp.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.chentong.firefighterapp.R
import com.chentong.firefighterapp.nav.NavFragment
import com.google.android.material.dialog.MaterialDialogs
import timber.log.Timber

class ScanFragment : NavFragment() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 28

        @JvmStatic
        fun newInstance(): ScanFragment{
            return ScanFragment()
        }
    }


    private val scanResultHandler = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiMan = context!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val scanResults = wifiMan.scanResults
            print("Wi-Fi get SSID:%s" + scanResults.get(0).SSID.toString())
        }
    }

    override fun refresh() {
        print("Refreshing tab: scan")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startScan = view.findViewById<Button>(R.id.networks_scan)
        startScan.setOnClickListener { onStartScan() }

    }

    private fun onStartScan(){
        val wifimanager = requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifimanager.isWifiEnabled) {
            MaterialDialog.Builder(requireContext())
                .title(R.string.dialog_error)
                .content(R.string.networks_start_scan_wifi_disabled)
                .icon(requireContext().getDrawable(R.drawable.baseline_error_outline_24)!!)
                .positiveText(R.string.action_ok)
                .build().show()
            return
        }

        print("Start network scanning")
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            MaterialDialog.Builder(requireContext())
                .title(R.string.networks_start_scan)
                .content(R.string.networks_start_scan_permission_warning)
                .icon(requireContext().getDrawable(R.drawable.baseline_perm_scan_wifi_24)!!)
                .negativeText(R.string.action_cancel)
                .positiveText(R.string.networks_start_scan_permission_warning_action)
                .onPositive { _, _ ->
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        ScanFragment.PERMISSION_REQUEST_CODE
                    )
                }
                .build().show()
        }else{
            doStartScan()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            ScanFragment.PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    doStartScan()
                }
            }
        }
    }

    private fun doStartScan() {
        requireContext().registerReceiver(scanResultHandler, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        val wifiMan = requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val success = wifiMan.startScan()
        print("Waiting for scanning results")
        view!!.findViewById<View>(R.id.networks_scan).visibility = View.GONE
        view!!.findViewById<View>(R.id.status_text).visibility = View.VISIBLE
    }
}