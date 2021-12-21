# Simple MVVM App
This is a simple app showcasing the MVVM architecture using Android Jetpack Components such as: DataBinding, Lifecycles, LiveData, Room, ViewModel, DataStore and dependency injection with Hilt.

## TODO::
Before you run your application, you need a Google Maps API key.
    
To get one, you first need to get the **SHA-1 certificate fingerprint** by entering the following command in the Android Studio's Terminal:
    
    keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
    
Then replace the **[SHA_1_CERTIFICATE_FINGERPRINT]** in the following link with the one in your Terminal and then follow this link:

    https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=[SHA_1_CERTIFICATE_FINGERPRINT]%3Bcom.simpleapp.challenge

Follow the directions and press "Create" at the end.

Alternatively, your can follow the directions here:
    https://developers.google.com/maps/documentation/android/start#get-key

Once you have your key (it starts with "AIza"), add the following line in your **local.properties** file:

    MAPS_API_KEY=the_google_maps_key_you_created


## Registration steps:
 - Open the app.
 - Insert your username and a password.
 - Click on the action button.

## Login steps:
 - Open the app.
 - If you're already logged in you should first logout from the user list screen options menu.
 - Insert your previous username and password.
 - Click on the action button.

## App Interface:
![Login Screen](../master/images/image1.png) 
![Country Picker](../master/images/image2.png)
![User List](../master/images/image3.png)
![User Details](../master/images/image4.png)
![User Location](../master/images/image5.png)

## Developer:
  Darush
