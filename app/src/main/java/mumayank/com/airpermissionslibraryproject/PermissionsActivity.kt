package mumayank.com.airpermissionslibraryproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mumayank.com.airpermissions.AirPermissions

class PermissionsActivity : AppCompatActivity() {

    private var airPermissions: AirPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        airPermissions = AirPermissions(
            this,
            arrayListOf(
                AirPermissions.PermissionItem(android.Manifest.permission.ACCESS_COARSE_LOCATION, "Please grant location permission"),
                AirPermissions.PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"Please grant write external storage permission"),
                AirPermissions.PermissionItem(android.Manifest.permission.READ_CONTACTS)
            ),
            object: AirPermissions.OnAllPermissionsGranted {
                override fun callback() {
                    Toast.makeText(this@PermissionsActivity, "All permissions are granted", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airPermissions?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
