package mumayank.com.airpermissions

import android.app.Activity
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
    private val permissionItems: ArrayList<PermissionItem>?
){

    init {
        if (activity != null && permissionItems != null) {
            if (areAllPermissionsGranted(activity, permissionItems).not()) {
                activity.startActivityForResult(
                    Intent(activity, AirPermissionsActivity::class.java)
                    .putExtra(AirPermissionsActivity.INTENT_EXTRA_PERMISSION_ITEMS, permissionItems),
                    REQUEST_CODE
                )
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode != Activity.RESULT_OK) {
            activity?.finish()
        }
    }

    class PermissionItem(
        val permission: String,
        val permissionRationalText: String
    ): Serializable

    companion object {

        private val REQUEST_CODE = 127

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

        fun isPermissionAlreadyGranted(activity: Activity?, permission: String?): Boolean {
            if (activity == null || permission == null) {
                return false
            }

            return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }

        fun isPermissionPermanentlyDisabled(activity: Activity?, permission: String?): Boolean {
            if (activity == null || permission == null) {
                return true
            }

            val isPermissionGranted = ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
            val isPermissionPermanentlyDisabled = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission).not()
            return isPermissionGranted && isPermissionPermanentlyDisabled
        }

        fun openAppPermissionSettings(activity: Activity?) {
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