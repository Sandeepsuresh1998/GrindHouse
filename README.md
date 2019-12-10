ReadME.md


    Application Setup:
        GitHub Repository:
            https://github.com/Sandeepsuresh1998/GrindHouse.git
        Map Setup:
            Because we are using the Google Maps API and it is only registered with one API key, you will have to replace the debug.keystore file on your machine with ours. This file has been uploaded to the main directory of our GitHub repo. To find your debug.keystore on a mac, go to finder; press command, shift, g; and type in Users/[your username here]/.android. By doing this, you should be able to see your debug.keystore file. On a Windows machine, this path is located at the same path Users/[username]/.android
            In order to use the Map API, we have to download the correct SDK tools in android studio. In order to do so we must go through a series of steps:
                Go to Tools > SDK Manager
                From there click on ‘SDK Tools’
                Check the following SDK Developer Tools
                    Google Play services
                    Android SDK Tools
                    Android SDK Platform-Tools
                    Google Play APK Expansion library
                    Google Web Driver
                    Android SDK Build-Tools
                    Android Emulator
        SQLite:
            No extra setup is required for the SQLite database deployment.
            In order to access the database file, it must be downloaded to your desktop. To do so, search for “Device File Explorer” using Android Studio’s search button in the top right. Then, navigate to data/data/com.example.beanandleaf/databases. Once the first user account is created, the database file will be stored in this folder as “BeanAndLeaf.db”. Right click and save this file to the desktop. To query a SQLite database, use terminal or consider installing a SQLite plugin such as SQLite Manager for Firefox or Database Navigator for Android Studio (both of which require a manual upload of your .db database file to access).
        GPS:
            The emulator does not allow you to see your current location so you have to set it manually. When you open the Android Emulator, there is a sidebar next to the phone. At the bottom of this sidebar, there is  an icon with three little dots, click that. Once you do, a pop up should occur with a list of options for you to click on the side. Find the one that says location and click that. A map should appear and you can search for the location you would like to set as your current location. Once you find it and click it on the map, there should be a button on the bottom right that says “Set Location.” Clicking that button will set your current location to the point you selected.

    Walk Through of Universal Functionalities:
        Landing Screen
            Basic Functionalities
                This is the opening screen of the application. You can see a brief description of the application and can choose to either login or register for a new account.
            Login
                In order to login to your existing account, click the “Login” button
            Create a New Account
                In order to create a new account, click the “No Account Yet? Create One” button
            Admin Access
                Administrators can login through the landing screen. Administrator access is used to verify merchant’s stores. If stores are not verified, their markers will not display on the map and customers cannot order from them. Please see the admin functionalities section of this document for the administrator password.
        Login Screen
            Basic Functionalities
                Login to your existing account.
            Login
                Type in your email and password in the given spaces by clicking on “Email” and “Password”. Then select if you are logging into a customer or merchant account. Click the “Login” button when finished.
            Create a New Account
                If you accidentally clicked login but don’t have an account, you can create a new account by clicking the “No Account Yet? Create One” button
        Register Screen
            Basic Functionalities
                Register for a new account
            Register a New Account
                Type in your full name, email and password in the given spaces by clicking on “Full Name”, “Email” and “Password”. Then select your gender and decide which type of account you would like to create (Customer or Merchant). Click the “Create Account” button when finished.
        Profile Screen
            Basic Functionalities
                View user’s profile information and make edits to it.
            Edit Information
                Type your changes for the various fields on the lines that follow “Full Name” and “Email”. You can also change your gender by clicking the desired radio button. Once you have made all of your changes, click “Update Profile” to confirm the changes.
            Change Password
                If you click on the “Change Password” button, you are prompted to enter your old password and then allowed to change it to a new one. Click “Change Password” at the bottom of the screen to set your new password.
        Logout Button
            If you press the button to the right of the Bean & Leaf Logo on the top right corner, it will log you out of your account and you will be redirected to the landing page.

    Walk Through of Customer Specific Functionality:
        Home/Map Screen
            Basic Functionalities
                If store locations have been populated by merchant accounts and those stores have been authenticated by the admin, then markers will appear on the Map Screen for the customer. You’re able to zoom in and zoom out of the map using the buttons on the bottom right of the map. You can also scroll the map to view nearby locations. You can also find your current location by clicking the button in the top right corner of the map.
            Check a Store’s Menu
                By clicking on any store populated within the map, an Info Window with that store’s name will appear above the marker in a white box. If that window is then clicked, you will be redirected to a page with that store’s menu.
            Getting Directions
                After clicking on a marker, you will see a square on the bottom right of the map screen with a blue arrow on it. If you click on this button, it will redirect you to a browser window with Google Maps and provide you with directions using various modes of transportation to get to that specific store.
            Logging An Order
                When you are looking at a store’s menu after clicking the respective marker and info window, you will see a button on the top right that allows you to record an order. You can click on this button and record an order you may have placed at that store by selecting the item, size, and quantity. Once finished, you may either complete the order which returns you to the map screen, or you may submit this item to the order and add another item which will refresh the add order form. A customer would choose to submit another item to the order if they purchased, for example, a latte and hot chocolate in the same visit. Since they are different items, the customer would log their latte order, click “Submit another item”, then log their hot chocolate purchase.
        History Screen
            Basic Functionalities
                If you’ve recorded at least one order, the history screen will provide you with statistics on your caffeine intake, calorie consumption, the money you’ve spent on drinks, types of drinks you have ordered, and stores you have visited. These charts will display different data depending on whether you choose to see your history for today, the past week, or all time, depending on what option you choose in the drop down bar located on the top of the history screen.
                Three charts are displayed for the customer. The first is a bar chart which shows the customer’s orders separated by store. The second is a pie chart which displays the customer’s orders separated by drink. The third is another bar chart which shows the money spent on drink orders by day of the week.
                If the customer has purchased no items, the charts and data will be blank.
        Recommendations Screen
            Basic Functionalities
                Once you’ve recorded at least 4 orders within the system, the recommendations page will populate a map with a store that we believe you would enjoy visiting based on your order history. Our recommendation for you will appear as a marker on the map and a Toast will pop-up and state what we believe you should order from that store. If there is only one store in the database, it is possible that you still may not get a recommendation.
                If you have not recorded at least 4 orders, you will get a Toast po-up that says “Sorry, unable to provide recommendations until you've made at least 4 trips!”
        Caffeine
            Basic Functionalities
                If you click on the caffeine button (found on the top left corner of the app) at any time, it will give you data on your caffeine consumption and how it relates to the recommended intake amount of 400mg.

    Walk Through of Merchant Specific Functionality:
        Home/Map Screen
            Basic Functionalities
                If store locations have been populated by merchant accounts and those stores have been authenticated by the admin, then markers will appear on the Map Screen for the customer. You’re able to zoom in and zoom out of the map using the buttons on the bottom right of the map. You can also scroll the map to view nearby locations. You can also find your current location by clicking the button in the top right corner of the map.
            Check a Store’s Menu
                By clicking on any store populated within the map, an Info Window with that store’s name will appear above the marker in a white box. If that window is then clicked, you will be redirected to a page with that store’s menu.
            Getting Directions
                After clicking on a marker, you will see a square on the bottom right of the map screen with a blue arrow on it. If you click on this button, it will redirect you to a browser window with Google Maps and provide you with directions using various modes of transportation to get to that specific store.
        Store Screen
            Basic Functionality
                You’re able to add, delete and update any store you select from the drop down menu. You must enter the latitude and longitude of the store you wish to enter.
            Add Store
                Click on the button “Add Store” to add a new store. Fill out the form and then click “Add New Store.” You will be prompted to upload a picture to provide proof you are a verified store (See Verification for more detail). Once you submit the photo, the new store will be added to the merchant’s account and will be displayed in the drop down menu of stores for the merchant.
            Verification
                The store will not show up on the map until it has been verified by an administrator. When you add a new store, you will be directed to upload proof of ownership. (See Admin section to know how verification happens.) If a merchant’s store has not been verified, it will be displayed on the bottom of the screen saying “Not Verified.” Once it has been , the status will be changed to “Verified.”
        Store Selection
            The store you choose in the drop down bar will be the store that is referenced in both the menu and history screens. In order to see data for a different store, you will have to select a different store on the store screen and the other two screens will populate accordingly.
        Menu Screen
            Basic Functionality
                View all the menu items in your store and edit, add or delete items.
            Add Menu Item
                By clicking the “Add Item” button in the top right corner of the menu, you will be directed to a form to add a new item to your menu. Here, you can fill in the item name, as well as the calories, caffeine (in mg), and price for small, medium, and large sizes. Merchants may add drink information for any or all of the sizes, but all three fields are required for each size (you can’t add price and calories for the small size without the caffeine, or vice versa).
            Edit Menu
                By clicking the side buttons you’re able to edit items and delete items from your menu. Sizes may be removed from an item. If all sizes are removed, the item will be deleted from your menu.
        History Screen
            Basic Functionality
                If a customer has recorded at least one order at your store, the history screen will provide you with statistics on your money earned and orders placed at this store as well as several charts. These charts will display different data depending on whether you choose to see your history for today, the past week, or all time, depending on what option you choose in the drop down bar located on the top of the history screen.
                Three charts are displayed for the merchant. The first is a pie chart which displays customers’ orders at your store separated by drink. The second is a bar chart which shows the money earned on drink orders by day of the week. The third is another pie chart which displays how many times one customer visited your store.

    Walk Through of Admin Specific Functionality:
        Log In
            Basic Functionalities
                This profile view is designed so that administrators are able to verify merchant ownership of stores.
            Password
                To log in, enter the password “Admin123!”
        Merchant Verification
            KBasic Functionalities
                Using the drop-down menu, enter the store you’d like to verify. The details of that store should populate (including the verification image they uploaded when created) and you should be able to verify it by clicking the blue button. Afterwards, both merchants and customers will be able to view this store’s marker on the map.
        Logout Button
            If you press the button on the top right of the admin homepage, it will log you out of the administrator account.

Improvements of the app since Project 2.4

    1. The user is alerted when with a notification when their caffeine nears 400 mg/day.
    2. The text font in the app displays upper and lower case letters. 
    3. Emails are stored in lowercase in the database.
    4. Passwords are encrypted in the database using SHA-1.



# GrindHouse
