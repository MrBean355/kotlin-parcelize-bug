# Kotlin Parcelize Bug
**NOTE: This was fixed at some point in the Kotlin Android Extensions plugin.**

A runtime exception is thrown when parcelizing a class that has a property of type `Array<T>?` (where `T` is any type except `String`) and the value of the property
is `null`.

This seems to have been introduced in Kotlin `1.5.0`, as there were no issues on `1.4.32`.

## Steps to Replicate
1. Use Kotlin version: `1.5.0`.
2. Apply Gradle plugin: `kotlin-android-extensions`.
3. Define a `Parcelable` data class with a nullable array property:
  ```kotlin
  @Parcelize
  data class MyParcelableModel(
      val items: Array<Int>?
  ) : Parcelable
  ```
4. Write an instance of this class with a `null` array property to a `Parcel`:
  ```kotlin
  MyParcelableModel(null)
      .writeToParcel(Parcel.obtain(), 0)
  ```
5. An exception is thrown at run time:
  ```
  java.lang.NullPointerException: Attempt to get length of null array
        at com.github.mrbean355.parcelizebug.MyParcelableModel.writeToParcel(Unknown Source:7)
        at com.github.mrbean355.parcelizebug.MainActivity.onCreate(MainActivity.kt:16)
        at android.app.Activity.performCreate(Activity.java:8000)
        at android.app.Activity.performCreate(Activity.java:7984)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1309)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3422)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3601)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2066)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loop(Looper.java:223)
        at android.app.ActivityThread.main(ActivityThread.java:7656)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:592)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:947)
  ```
