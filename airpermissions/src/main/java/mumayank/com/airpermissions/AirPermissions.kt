package mumayank.com.airpermissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


private const val PERMISSION_REQUEST = 1243

class AirPermissions(
    private val activity: Activity,
    private val permissionsList: Array<String>,
    private val callbacks: Callbacks
) {
    interface Callbacks {
        fun onSuccess()
        fun onFailure()
        fun onAnyPermissionPermanentlyDenied()
    }

    init {
        if (areAllPermissionsGranted()) callbacks.onSuccess() else ActivityCompat.requestPermissions(activity, permissionsList, PERMISSION_REQUEST)
    }

    private fun areAllPermissionsGranted(): Boolean {
        var allPermissionsAvailable = true
        for (permission in permissionsList) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsAvailable = false
                break
            }
        }
        return allPermissionsAvailable
    }

    private fun isAnyPermissionPermanentlyDisabled(): Boolean {
        var isAnyPermissionPermanentlyDisabled = false
        for (permission in permissionsList) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) == false) {
                    isAnyPermissionPermanentlyDisabled = true
                    break
                }
            }
        }
        return isAnyPermissionPermanentlyDisabled
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (areAllPermissionsGranted()) {
                    callbacks.onSuccess()
                } else {
                    if (isAnyPermissionPermanentlyDisabled()) {
                        callbacks.onAnyPermissionPermanentlyDenied()
                    } else {
                        callbacks.onFailure()
                    }
                }
                return
            }
        }
    }

    companion object {
        fun openAppPermissionSettings(activity: Activity) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }
    }

}