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
// IN YOUR ACTIVITY ->
class MainActivity : AppCompatActivity() {

    // (STEP 1/3) DECLARE AIR PERMISSIONS OBJECT AT TOP-LEVEL
    private var airPermission: AirPermissions? = null

    override fun onResume() {
        // (STEP 2/3) INIT AIR PERMISSION OBJECT WHEN YOU WANT TO ASK FOR DANGEROUS PERMISSIONS ( PREFERABLY IN onResume() )
        airPermissions = AirPermissions(
            this,
            arrayListOf(
                AirPermissions.PermissionItem(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, // JUST EXAMPLE
                    "Please grant location permission" // JUST EXAMPLE
                ),
                AirPermissions.PermissionItem(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, // JUST EXAMPLE
                    "Please grant write external storage permission" // JUST EXAMPLE
                )
            )
        )
    }

    // OVERRIDE onActivityResult METHOD OF YOUR ACTIVITY
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airPermissions?.onActivityResult(requestCode, resultCode, data)
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
