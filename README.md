# AirPermissions 

A lightweight and very simple android library which I myself use to simplify Android Runtime Permissions forever.

[![](https://jitpack.io/v/mumayank/AirPermissionsLibraryProject.svg)](https://jitpack.io/#mumayank/AirPermissionsLibraryProject)

## Working
- Check if all requested permissions are available
- If not, request for them
- When results arrive, check if all permissions are now available
- If not, and if any permission is permanently denied, open app settings and request user to provide the permission
- When user returns to the activity, check if all permissions are now available
- If still not, inform the dev that required permission(s) were not provided

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

## Usage

As usual, declare all the permissions your app needs in the `AndroidManifest` file:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>     <!-- just an example -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>     <!-- just an example -->
<uses-permission android:name="android.permission.READ_CONTACTS"/>              <!-- just an example -->
```

Since normal permissions are automatically granted to your app by Android OS at install time, you only need to ask grant for dangerous permissions. The library takes care of this for you:

### Step 1 - Create AirPermissions object
```kotlin
class MainActivity : AppCompatActivity() {

    private val airPermissions = AirPermissions(
        this,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
        ),
        fun() {
            // todo on success
        }, fun() {
            // todo on failure
        }
    )
    
}
```

### Step 2 - call request() when you want to ask for permissions
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_permissions)

    airPermissions.request() // THIS LINE
}
```

### Step 3 - override onRequestPermissionsResult and onActivityResult and call airPermissions's corresponnding methods
```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    airPermissions.onRequestPermissionsResult(requestCode)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    airPermissions.onActivityResult(requestCode)
}
```

That's all!
