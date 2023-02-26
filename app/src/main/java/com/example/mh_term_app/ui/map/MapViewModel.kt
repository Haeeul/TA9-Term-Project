package com.example.mh_term_app.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.utils.extension.toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private fun setUserInfo(){

    }

    suspend fun checkPermission(){
        viewModelScope.launch {
            TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
        }

    }

    val permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            MHApplication.getApplicationContext().toast("권한 확인")
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            MHApplication.getApplicationContext().toast("권한 거절")
        }
    }
}