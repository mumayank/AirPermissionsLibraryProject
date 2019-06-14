# AirPermissions 

A lightweight and very simple android library which I myself use to simplify Android Runtime Permissions forever.

[![](https://jitpack.io/v/mumayank/AirPermissionsLibraryProject.svg)](https://jitpack.io/#mumayank/AirPermissionsLibraryProject)

## Usage

As usual, declare all the permissions your app needs in the `AndroidManifest` file:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>     <!-- just an example -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>     <!-- just an example -->
<uses-permission android:name="android.permission.READ_CONTACTS"/>              <!-- just an example -->
```

Since normal permissions are automatically granted to your app by Android OS at install time, you only need to ask grant for dangerous permissions.
You can easily do it like this:

### Step 1 - Declare AirPermissions object on top of your activity
```kotlin
class MainActivity: AppCompatActivity() {
    private var airPermissions: AirPermissions? = null
    .
    .
    .
}
```

### Step 2 - In onCreate, define airPermissions
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_permissions)

    airPermissions = AirPermissions(
        this,
        arrayListOf(
            AirPermissions.PermissionItem(android.Manifest.permission.ACCESS_COARSE_LOCATION, "Please grant location permission"),
            AirPermissions.PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"Please grant write external storage permission"),
            AirPermissions.PermissionItem(android.Manifest.permission.READ_CONTACTS,"Please grant read contacts permission")
        ),
        object: AirPermissions.OnAllPermissionsGranted {
            override fun callback() {
                Toast.makeText(this@PermissionsActivity, "All permissions are granted", Toast.LENGTH_SHORT).show()
                // Continue your work here
            }
        }
    )
}
```

### Step 3 - Override onRequestPermissionsResult and call airPermissions's method by the same name
```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    airPermissions?.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
