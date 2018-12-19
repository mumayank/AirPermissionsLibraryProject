package mumayank.com.airpermissions

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.content.Intent
import android.net.Uri
import android.provider.Settings


private const val PERMISSION_REQUEST = 1243

class AirPermissions(
    private val activity: Activity,
    private val permissionsList: Array<String>,
    private val callbacks: Callbacks
) {
    interface Callbacks {
        fun onSuccess()
        fun onFailure()
    }

    init {

        // First check if all permissions are already available
        var allPermissionsAvailable = true
        for (permission in permissionsList) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsAvailable = false
                break
            }
        }
        if (allPermissionsAvailable) {
            callbacks.onSuccess()
        } else {
            ActivityCompat.requestPermissions(activity, permissionsList, PERMISSION_REQUEST)
        }

    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    callbacks.onSuccess()
                } else {
                    callbacks.onFailure()
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
            activity.startActivityForResult(intent, PERMISSION_REQUEST)
        }
    }

}