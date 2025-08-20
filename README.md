# Weight Tracker App  

## Overview  
This project’s goal was to create a basic Android application that helps users track their weight and reach a target weight goal. The app allows users to:  

- Store entered weights in a database  
- Track progress toward a target weight  
- Log in with a local account or continue as a guest
- <img width="557" height="1248" alt="image" src="https://github.com/user-attachments/assets/6892f3b9-414c-4579-bd15-c63b641cab80" /> <img width="560" height="1247" alt="image" src="https://github.com/user-attachments/assets/bc1878b4-6a08-4c9c-bb8c-7ee4c4b40e3e" /> <img width="553" height="1248" alt="image" src="https://github.com/user-attachments/assets/c7784057-ee35-4b36-bf92-607472263224" />




## User Needs & UI Design  
The app was designed with a user-centered approach, focusing on simplicity and ease of use.  

**Screens and Features:**  
- **Login Screen** – Users can create a local account, log in, or continue as a guest  
- **Main Screen** – Displays target weight, current weight, and a history of previously entered weights  
- **Settings Screen** – Allows users to enable SMS notifications for reaching their target weight and update their target goal  

The UI was intentionally kept straightforward so users could focus on tracking progress without unnecessary distractions.  

## Development Approach  
- Began with paper sketches to plan the UI layout  
- Implemented screens in Android Studio based on those sketches  
- Built functionality screen by screen to keep the code organized and easy to revisit  
- Maintained a simple, modular structure to support future maintainability  

These strategies can be applied in future projects to improve clarity, structure, and adaptability.  

## Testing  
- Used the Android emulator to test functionality across screens  
- Verified login, weight entry, progress tracking, and settings behavior  
- Testing was critical for catching small issues early and confirming that the app worked as intended  

## Challenges and Innovation  
One major challenge was implementing the database. While SQLite was initially considered, the Android documentation recommended using Room, an abstraction layer over SQLite that simplifies database management. By adopting Room, the app gained cleaner code, better maintainability, and more flexibility for future expansion.  

## Successes  
The database implementation with Room was a standout success. It provided an elegant solution that not only met the project’s requirements but also left room for future feature expansion. This component highlighted strong problem-solving skills and adaptability to industry best practices.  

## Installation and Usage  
1. Clone this repository  
2. Open in Android Studio  
3. Build and run on an emulator or physical device  
