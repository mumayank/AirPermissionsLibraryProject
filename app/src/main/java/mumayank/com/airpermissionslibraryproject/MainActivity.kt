package mumayank.com.airpermissionslibraryproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mumayank.com.airpermissions.AirPermissions

class MainActivity : AppCompatActivity() {

    private var airPermission: AirPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        airPermission = AirPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            object: AirPermissions.Callbacks {
                override fun onSuccess() {
                    Toast.makeText(this@MainActivity, "Permission granted", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure() {
                    Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
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
