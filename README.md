# server-time-android

[![](https://jitpack.io/v/raheemadamboev/server-time-android.svg)](https://jitpack.io/#raheemadamboev/server-time-android)

Light library to get real UNIX time from Google server in android. Sometimes local phone's date can be wrong so for best accuracy it is better get time from server. It only gets time from server and always gets correct date.

## How To use

Add it in your root **build.gradle** at the end of repositories:
```
allprojects {
  repositories {
	  maven { url 'https://jitpack.io' }
  }
}
```  

Include below dependency in build.gradle of application and sync it:
```
implementation 'com.github.raheemadamboev:server-time-android:1.0'
```

**Get server time:**
```
 ServerTime().execute { time ->
  
  when (time) {

    ServerTime.UNKNOWN_HOST -> { 
      // handle internet not working or error in host                      
    }

    ServerTime.IO_EXCEPTION -> { 
      // handle io exception                  
    }

    else -> { 
      // time (milli seconds) was retreived successfully
      // println(Date(time).toString())
    }
  }
}
```

## Demo application

<a href="https://github.com/raheemadamboev/server-time-android/blob/master/app-debug.apk">Download demo</a>

<img src="https://github.com/raheemadamboev/server-time-android/blob/master/demo-server-time.gif" alt="Italian Trulli" width="200" height="400">
