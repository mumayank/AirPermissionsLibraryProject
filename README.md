# AirPermissions 

A lightweight and very simple android library which I myself use to simplify Android Runtime Permissions forever.

[![](https://jitpack.io/v/mumayank/AirPermissionsLibraryProject.svg)](https://jitpack.io/#mumayank/AirPermissionsLibraryProject)

## Usage

As usual, declare all the permissions your app needs in the `AndroidManifest` file:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> <!-- Just example -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- Just example -->
```

Since normal permissions are automatically granted to your app by Android OS at install time, you only need to ask grant for dangerous permissions.
You can easily do it like this:

```kotlin
//  GO TO YOUR ACTIVITY CLASS
class MainActivity : AppCompatActivity() {

    // DECLARE AIR PERMISSIONS OBJECT AT TOP-LEVEL
    private var airPermission: AirPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // INIT AIR PERMISSION OBJECT WHEN YOU WANT TO ASK FOR DANGEROUS PERMISSIONS
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

    // ADD THIS ALSO (OVERRIDE ON REQUEST PERMISSION RESULT OF YOUR ACTIVITY TO CALL AIR PERMISSION'S METHOD BY THE SAME NAME
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airPermission?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
```

## Setup
Add this line in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' } // this line
  }
}
  ```
Add this line in your app build.gradle:
```gradle
dependencies {
  implementation 'com.github.mumayank:AirPermissionsLibraryProject:LATEST_VERSION' // this line
}
```
where LATEST_VERSION is [![](https://jitpack.io/v/mumayank/AirPermissionsLibraryProject.svg)](https://jitpack.io/#mumayank/AirPermissionsLibraryProject)

That's all!
