package mumayank.com.airpermissions

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class AirPermissionsActivity : AppCompatActivity() {

    companion object {
        val INTENT_EXTRA_PERMISSION_ITEMS = "INTENT_EXTRA_PERMISSION_ITEMS"
        private const val PERMISSION_REQUEST = 1243
    }

    var permissionItems = arrayListOf<AirPermissions.PermissionItem>()
    var currentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.air_permission_activity)
        supportActionBar?.hide()
        permissionItems.addAll(intent.getSerializableExtra(INTENT_EXTRA_PERMISSION_ITEMS) as ArrayList<AirPermissions.PermissionItem>)
        askNextPermission()
    }

    private fun askNextPermission() {
        currentIndex++
        if (currentIndex == permissionItems.size) {
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            if (AirPermissions.isPermissionAlreadyGranted(this, permissionItems[currentIndex].permission)) {
                askNextPermission()
            } else {
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Permission required")
                    .setMessage(permissionItems[currentIndex].permissionRationalText)
                    .setPositiveButton("PROCEED") { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(permissionItems[currentIndex].permission), PERMISSION_REQUEST)
                    }
                    .setNegativeButton("CANCEL") { dialog, which ->
                        finish()
                    }
                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (AirPermissions.isPermissionAlreadyGranted(this, permissionItems[currentIndex].permission)) {
                    askNextPermission()
                } else {
                    if (AirPermissions.isPermissionPermanentlyDisabled(this, permissionItems[currentIndex].permission)) {
                        AirPermissions.openAppPermissionSettings(this)
                    }
                    finish()
                }
            }
        }
    }

}
