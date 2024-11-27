<h1 align="center">Server Time</h1>

<p align="center">
  <a href="http://developer.android.com/index.html"><img alt="Android" src="https://img.shields.io/badge/platform-android-green.svg"/></a>
  <a href="https://jitpack.io/#raheemadamboev/server-time-android"><img alt="Version" src="https://jitpack.io/v/raheemadamboev/server-time-android.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
🕜 <b>ServerTimeAndroid</b> is a light library to get real UNIX time from the Google server in Android. Sometimes phone's local date can be wrong so for the best accuracy it is better to get time from the server. It gets time from the Google server and always gets the correct date.
</p>

# Setup

Add the maven library bucket to the `dependencyResolutionManagement.repositories` block in `settings.gradle.kts` file as follows:
```kotlin
dependencyResolutionManagement {
  ...
  repositories {
    ...
    maven("https://jitpack.io")
  }
}
```

Install the library to the project in desired module's `build.gradle.kts` file. Replace `<current_version>` with the actual version:
```kotlin
implementation("com.github.raheemadamboev:server-time-android:<current_version>")
```

# Implementation

**Get server time (callback API):**
```kotlin
 ServerTime().execute { time ->
  
  when (time) {
    ServerTime.Result.UNKNOWN_HOST -> { 
      // handle internet not working or error in host                      
    }

    ServerTime.Result.IO_EXCEPTION -> { 
      // handle io exception                  
    }

    else -> { 
      // time (milli seconds) was retreived successfully
      // println(Date(time).toString())
    }
  }
}
```

**Get server time (suspend API):**
```kotlin
 viewModelScope.launch {
   val time = ServerTime().execute()
  
   when (time) {
     ServerTime.Result.UNKNOWN_HOST -> { 
       // handle internet not working or error in host                      
     }

     ServerTime.Result.IO_EXCEPTION -> { 
       // handle io exception                  
     }

     else -> { 
       // time (milli seconds) was retreived successfully
       // println(Date(time).toString())
     }
   }
}
```

# Demo

<a href="https://github.com/raheemadamboev/server-time-android/blob/master/extra/app-debug.apk">Download demo</a>

<img src="https://github.com/raheemadamboev/server-time-android/blob/master/extra/banner.gif" width="200" height="400">

# Projects using this library

**GoTest** 250 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.gotest">Google Play Store</a>

**Buxgalteriya schyotlar rejasi** 50 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.uzbekistanaccountingcode">Google Play Store</a>

**Irregular Verbs**  25 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.irregularverbs">Google Play Store</a>

# License

```xml
Designed and developed by raheemadamboev (Raheem) 2022.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
