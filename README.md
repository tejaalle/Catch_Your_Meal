# Catchyourmeal

Our application CatchYourMeal helps people in finding their favorite meals and have their food delivered at their doorstep.
Not only this, the application lets you choose your favorite meal, also customers can even customize their meal according to their dietary needs. 
Even though our application does not involve the business model behind delivering food as of now, we believe that this application is a platform that can serve the intermediate purpose for many businesses that strive to work in this area.

## Target Audience:
The target audience for our application covers a wide range of people, targeting people age between 14-60 years old of any gender. 
They should be an English speaking Haligonians, who like to order meals and other items. They need to be using Android phones for less than 3 years old.
There is a need for a network connection for using this application for the first time. Later this application will be used without a network connection.

## Libraries:
Room database library: Library used to store the data temporarily in the local 
Firebase database and auth library: This library is used to store the items available in the restaurants. Data from the firebase is retrieved and displayed to the user.
Google play services maps: This library is used to detect the user's device location.
Bumptech library: This library is used to show the image of the meal item.
Dexter library: This library is used to ask permission from the user to access the device location.

## Installation Notes:
1.Install Android Studio from https://developer.android.com/studio/install
2.Download or clone(https://git.cs.dal.ca/mayank/catchyourmeal.git) our repository from the Gitlab.
4.Select open existing project option in android studio
3.Select our directory to open the project
4. Provide Kotlin SDK(version>=1.3.50) to run the application
5. Set the compileSdkVersion 29, minSdkVersion 24, targetSdkVersion 29
6. While running on the emulator run on a device with API level 26 or higher.

## Features:
Minimum Functionalities 
Our application boasts some basic features such as:
1. Ordering food from various selections like individual items, combo, and weekly plans. It allows people to choose meals based on the requirement. They can choose an ala carte if they want to have a single dish or combo if they want to choose a combo offer or else they can choose meal plans to have the same item delivered for a week or two weeks.
2. Having weekly meal plans, so that a person does not have to go through the tedious process of thinking and ordering the meals every day. The main view of providing weekly plans is to allow users not to worry about ordering the food every day if they are busy in their schedule. 
3. Allow users to operate the application as a guest. The user is allowed to operate and use the application as a guest. 
4.FAQs are present to help guide the clients. Frequently asked questions allows user to get answers quickly for the queries rather than reaching to the customer care every time. It will allow people to get more ideas on the application.
5. About is also provided to give a brief about the application and its use. We are also allowing the user to reach us by mail to let us know the queries.

## Expected functionalities
1. Favorites: This functionality includes a set of mostly picked items and eventually helps a customer to pick food easily. The main use of favorites is to allow the customers to have a shortcut for the favorite item. By this, we are allowing the customer to build a shortcut without going through all the items again
2. Customization of meals which helps clients to customize their meal according to their dietary needs.
3. Enabled users to create an account by signing up. It will allow users to easily deliver the food to the place based on the details provided. We are also providing a profile page for the signed users as well as guest users.
4. Enabled users to order food by fetching their location. It would allow a user to quickly fetch the items available in the restaurant near to the user.

## References:
"Udacity", Classroom.udacity.com, 2020. [Online]. Available: https://classroom.udacity.com/courses/ud9012. [Accessed: 28- Mar- 2020].
"SearchView  |  Android Developers", Android Developers, 2020. [Online]. Available: https://developer.android.com/reference/android/widget/SearchView. [Accessed: 21- Apr- 2020].
"10 Heuristics for User Interface Design: Article by Jakob Nielsen", Nielsen Norman Group, 2020. [Online]. Available: https://www.nngroup.com/articles/ten-usability-heuristics/. [Accessed: 02- Apr- 2020].
"Karumi/Dexter", GitHub, 2020. [Online]. Available: https://github.com/Karumi/Dexter. [Accessed: 03- Apr- 2020].
J. Joseph, "How to pass values from RecycleAdapter to MainActivity or Other Activities", Stack Overflow, 2020. [Online]. Available: https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities. [Accessed: 03- Apr- 2020].
"Coroutines With Room Persistence Library", raywenderlich.com, 2020. [Online]. Available: https://www.raywenderlich.com/7414647-coroutines-with-room-persistence-library. [Accessed: 03- Apr- 2020].
A. Pervaiz, "Add a Back Button to Action Bar Android Studio (Kotlin)", Devofandroid.blogspot.com, 2020. [Online]. Available: https://devofandroid.blogspot.com/2018/03/add-back-button-to-action-bar-android.html. [Accessed: 03- Apr- 2020].
"Bottom Navigation - Material Components for Android", Material Components for Android, 2020. [Online]. Available: https://material.io/develop/android/components/bottom-navigation-view/. [Accessed: 03- Apr- 2020].




