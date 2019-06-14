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
import java.io.Serializable

class AirPermissions(
    private val activity: Activity?,
    private val permissionItems: ArrayList<PermissionItem>?,
    private val onAllPermissionsGranted: OnAllPermissionsGranted?
){
    private var index = -1

    init {
        index = -1
        askNextPermission()
    }

    private fun askNextPermission() {
        if (activity == null || permissionItems == null || permissionItems.size == 0) {
            return
        }

        index++
        if (index == permissionItems.size) {
            onAllPermissionsGranted?.callback()
        } else {
            if (isPermissionAlreadyGranted(activity, permissionItems[index].permission)) {
                askNextPermission()
            } else {
                AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setTitle("Permission required")
                    .setMessage(permissionItems[index].explanationForThePermission)
                    .setPositiveButton("PROCEED") { _, _ ->
                        ActivityCompat.requestPermissions(activity, arrayOf(permissionItems[index].permission), PERMISSION_REQUEST)
                    }
                    .setNegativeButton("CANCEL") { _, _ ->
                        activity.finish()
                    }
                    .show()
            }
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (activity == null || permissionItems == null || permissionItems.size == 0) {
                    return
                }

                if (isPermissionAlreadyGranted(activity, permissionItems[index].permission)) {
                    askNextPermission()
                } else {
                    if (isPermissionPermanentlyDisabled(activity, permissionItems[index].permission)) {
                        openAppPermissionSettings(activity)
                    }
                    activity.finish()
                }
            }
        }
    }

    class PermissionItem(
        val permission: String,
        val explanationForThePermission: String
    ): Serializable

    interface OnAllPermissionsGranted {
        fun callback()
    }

    companion object {

        private const val PERMISSION_REQUEST = 1243

        private fun areAllPermissionsGranted(activity: Activity?, permissionItems: ArrayList<PermissionItem>?): Boolean {
            if (permissionItems == null) {
                return false
            }

            var permissionsAreGranted = true
            for (permissionItem in permissionItems) {
                if (isPermissionAlreadyGranted(activity, permissionItem.permission).not()) {
                    permissionsAreGranted = false
                }
            }
            return permissionsAreGranted
        }

        private fun isPermissionAlreadyGranted(activity: Activity?, permission: String?): Boolean {
            if (activity == null || permission == null) {
                return false
            }

            return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }

        private fun isPermissionPermanentlyDisabled(activity: Activity?, permission: String?): Boolean {
            if (activity == null || permission == null) {
                return true
            }

            val isPermissionGranted = ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
            val isPermissionPermanentlyDisabled = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission).not()
            return isPermissionGranted && isPermissionPermanentlyDisabled
        }

        private fun openAppPermissionSettings(activity: Activity?) {
            if (activity == null) {
                return
            }

            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
            Toast.makeText(activity, "Please enable permissions from settings to proceed", Toast.LENGTH_LONG).show()
        }

    }

}