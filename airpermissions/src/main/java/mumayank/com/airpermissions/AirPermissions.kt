package mumayank.com.airpermissions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private const val PERMISSION_REQUEST = 1243

class AirPermissions(
    private val activity: Activity,
    private val permissionItems: ArrayList<PermissionItem>,
    private val callbacks: Callbacks
) {
    class PermissionItem(
        val permission: String,
        val permissionRationalText: String
    )

    interface Callbacks {
        fun onSuccess()
        fun onFailure()
        fun onAnyPermissionPermanentlyDenied()
    }

    var currentIndex = -1

    init {
        askNextPermission()
    }

    private fun askNextPermission() {
        currentIndex++
        if (currentIndex == permissionItems.size) {
            callbacks.onSuccess()
        } else {
            if (isPermissionAlreadyGranted(activity, permissionItems[currentIndex].permission)) {
                askNextPermission()
            } else {
                AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setTitle("Permission required")
                    .setMessage(permissionItems[currentIndex].permissionRationalText)
                    .setPositiveButton("PROCEED") { dialog, which ->
                        ActivityCompat.requestPermissions(activity, arrayOf(permissionItems[currentIndex].permission), PERMISSION_REQUEST)
                    }
                    .setNegativeButton("CANCEL") { dialog, which ->
                        callbacks.onFailure()
                    }
                    .show()
            }
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (isPermissionAlreadyGranted(activity, permissionItems[currentIndex].permission)) {
                    askNextPermission()
                } else {
                    if (isPermissionPermanentlyDisabled(activity, permissionItems[currentIndex].permission)) {
                        openAppPermissionSettings(activity)
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
            Toast.makeText(activity, "Please enable permissions from settings to proceed", Toast.LENGTH_LONG).show()
        }

        fun isPermissionAlreadyGranted(activity: Activity, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }

        fun isPermissionPermanentlyDisabled(activity: Activity, permission: String): Boolean {
            val isPermissionGranted = ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
            val isPermissionPermanentlyDisabled = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission).not()
            return isPermissionGranted && isPermissionPermanentlyDisabled
        }

    }
}