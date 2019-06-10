package mumayank.com.airpermissionslibraryproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mumayank.com.airpermissions.AirPermissions

class PermissionsActivity : AppCompatActivity() {

    private var airPermissions: AirPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
    }

    override fun onResume() {
        super.onResume()

        airPermissions = AirPermissions(
            this,
            arrayListOf(
                AirPermissions.PermissionItem(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    "Please grant location permission"
                ),
                AirPermissions.PermissionItem(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "Please grant write external storage permission"
                ),
                AirPermissions.PermissionItem(
                    android.Manifest.permission.READ_CONTACTS,
                    "Please grant read contacts permission"
                )
            ),
            object: AirPermissions.OnAllPermissionsGranted {
                override fun callback() {
                    Toast.makeText(this@PermissionsActivity, "All permissions are granted", Toast.LENGTH_SHORT).show()
                }
            }
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airPermissions?.onActivityResult(requestCode, resultCode, data)
    }

}
