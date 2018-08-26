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

