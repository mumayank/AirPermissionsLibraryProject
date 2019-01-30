package mumayank.com.airpermissionslibraryproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                    Toast.makeText(this@PermissionsActivity, "Please enable permissions from settings", Toast.LENGTH_SHORT).show()
                    AirPermissions.openAppPermissionSettings(this@PermissionsActivity)
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
