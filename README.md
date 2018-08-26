# CustomSearchView
A custom search view for Android 

# Usage
**Add jitpack in your root build.gradle at the end of repositories:**
```javascript
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Add dependency in your app's build.gradle file**
```javascript
	dependencies {
	        implementation 'com.github.hayahyts:CustomSearchView:1.0.0'
	}
```

**Add CustomSearchView to your layout file**
```xml
    <?xml version="1.0" encoding="utf-8"?>
     <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.hayahyts.library.CustomSearchView
        android:id="@+id/searchBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
    </LinearLayout>
```

**Get CustomSearchView in your Activity or Fragment**
```java
      class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val suggestions = ArrayList<String>()
            suggestions.add("SpaceWek")
            suggestions.add("Google")
            suggestions.add("Tesla")
            suggestions.add("AirBnb")

            searchBar.setSuggestions(suggestions)
       }
    }
```

# Style it
```xml
	<style name="CustomSearchView">
		<item name="toolbarBackground">@color/colorPrimary</item>
		<item name="android:hint">Search</item>
		<item name="android:textColor">@color/colorBlack87</item>
		<item name="android:textColorHint">@color/colorBlack54</item>
   	 </style>
```

# License
	Copyright 2018 Aryeetey Solomon Aryeetey

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
