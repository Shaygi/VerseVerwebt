# WovenVerses

Woven verses is a unique puzzle game that interacts with your phone's control center and sensors. The game is styled as a book and includes seven chapters, each featuring its own puzzle, as well as an introductory puzzle. Players can log in or register, and by solving puzzles, they can generate a high score that is displayed on a leaderboard. The total time taken to solve each chapter is added up to form the final score.

## Features

Log in/Register: Players can create an account or log in to track their progress.

Chapter Puzzles: Each of the seven chapters presents a unique puzzle that requires interaction with the phone's control center and sensors.

Leaderboard: The time taken to solve each puzzle is recorded, and the total time is displayed on a leaderboard.

Book-Style Design: The game is designed to resemble a book, enhancing the immersive experience.

## Getting started

To test the game using the Android Studio emulator, follow these steps:

- Use a Pixel 3a API 34 emulator for testing.
- In the emulator's advanced settings, set both the front and back camera emulation to "Emulated" to enable access to the flashlight in the control center.
- Add the dark theme button to the control center for quicker access during gameplay.
- In addition, the microphone must be activated in the microphone settings depending on the selected input.
- If the light (lux) value is below 200 in the visual sensor settings, this should be raised for the best gaming experience.
- If you do not see the button for the DarkTheme when opening the control center, you must add it via the Edit Tool to facilitate access during the game.

No additional installations are required.

## Installation

1. Clone the repository:
~~~git
git clone https://github.com/Shaygi/VerseVerwebt.git
~~~
2. Open the project in Android Studio.

3. Build and run the project on the Pixel 3a API 34 emulator.

## Gameplay

1. Introductory Puzzle: Solve the initial puzzle by turning on the flashlight to get started.

2. Chapter Puzzles: Progress through the seven chapters, each with a unique puzzle:
   
   Chapter 1: Increase the system volume to solve the riddle.

   Chapter 2: Detect the phone's orientation.
   
   Chapter 3: Reduce the room light. Light/Lux value in the extended emulator settings. On the physical cell phone, you can darken the room or cover the light sensor (near the front camera) with your hand.
   
   Chapter 4: Activate the dark mode.
   
   Chapter 5: Check the phone's battery status.
   
   Chapter 6: Use the microphone to input the correct Code. To do this, press the microphone button and say the code word "5768" in the Google pop-up.
   
   Chapter 7: Take a screenshot to solve the riddle. To do this, use the Screenshot button in the Task Manager / Recently used apps or the key combination on the physical cell phone. The screenshot option via the emulator does not work due to a different storage location. 

4. Leaderboard: Your total time to solve the puzzles will be recorded and displayed on the leaderboard.

## PICTURES AS HELP
![ExtendedSettings](https://github.com/Shaygi/VerseVerwebt/assets/104787845/ffe02999-e0b6-4276-b4f0-361e07013e13)


## Notes

Ensure that the emulator's control center is properly configured for optimal gameplay.
The game is designed to work seamlessly on a Pixel 3a API 34 emulator, and some features may not work on other devices or configurations.

## Licence

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License.

## Acknowledgements

Special thanks to the developers and contributors who made this project possible.
