Smart Pantry Manager

Description
Smart Pantry Manager is a Java-based Android application developed for the Mobile App Development 700 practical assignment.
The application helps users reduce food waste by keeping track of ingredients they already have in their pantry and suggesting recipes that can be prepared using only those available ingredients.
The application follows a strict recipe-matching rule. A recipe is only suggested when the user has every required ingredient in the required quantity.

Main Features
- Add pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- View current pantry items
- Store and manage recipes
- Suggest recipes based on available pantry ingredients
- View recipe ingredients and preparation steps
- Strict ingredient-matching logic
- Settings/Profile screen
- Input validation
- Persistent data storage

Technologies Used
- Java
- Android Studio
- Android SDK
- SQLite
- RecyclerView
- Intents

Database
SQLite was chosen as the database because it provides local persistent storage within the Android application. 
It allows pantry and recipe data to remain available after the application is closed and reopened.

Setup and Run Instructions
1. Download or clone this repository.
2. Open the project in Android Studio.
3. Allow Android Studio to sync the Gradle files.
4. Make sure an Android emulator or physical Android device is connected.
5. Run the application using Android Studio.
6. The application will create and initialise its local SQLite database when required.
7. Use the Pantry screen to add ingredients and test the recipe-matching functionality.

Project Requirements
This project is developed as part of the Mobile App Development 700 practical assignment.
The application is built entirely in Java and includes multiple screens, database persistence, CRUD functionality, RecyclerView with an adapter, Intents for navigation, and strict recipe-matching logic.
