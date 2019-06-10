package mumayank.com.airpermissionslibraryproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mumayank.com.airpermissions.AirPermissions

class PermissionsActivity : AppCompatActivity() {

    private var airPermission: AirPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
    }

    override fun onResume() {
        super.onResume()

        airPermission = AirPermissions(
            this,
            arrayListOf(
                AirPermissions.PermissionItem(android.Manifest.permission.ACCESS_COARSE_LOCATION, "Please allow location services"),
                AirPermissions.PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "Please allow write external storage services")
            ),
            object: AirPermissions.Callbacks {

                override fun onSuccess() {
                    Toast.makeText(this@PermissionsActivity, "Permission granted", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure() {
                    Toast.makeText(this@PermissionsActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onAnyPermissionPermanentlyDenied() {
                    finish()
                }

            }
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airPermission?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
