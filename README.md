# QuizApp
A quiz app which uses JSON payload to load all the questions, and an interactive user interface(UI) for the user to answer all the questions
It has a clean architecture, where a retrofit api calls for all the questions in the json file. It also includes a repositoy to collect all the data from api and a 
view model which provides data to UI elements, from the repository

UI
Includes a score tracker, where with each correct answer, the score meter progresses.
Then comes a divider, followed by the question.
The choices are presented in the form of a radio group button. Selecting a wrong or right answer is also highlighted.
Followed by a next button to go to the next question.


https://user-images.githubusercontent.com/82358330/166686601-d1efcfd3-ab26-4252-9928-486fe4c37de2.mp4

