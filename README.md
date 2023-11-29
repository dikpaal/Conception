# Personal Project: Conception
*Conception: design (in French)*

The application would essentially be a tool that would allow a user to design rooms of a house from top 
view. The user will be able to start with a square room with the dimension of their choice. Then they would be able to 
place furniture of various sizes in the rooms in the position and alignment of their choice. At initial stage, the user 
would only be able to design a single square room. At later stages of the project, however,  I would add an option that
would allow the users to add multiple rooms in the house.

Because of its simple and efficient working mechanism, this application could be used by anybody that wants to design 
their own house or wants to learn architectural drawing and design but it would be of special help to new architects 
and architecture students.

This project is of interest to me because in the past, I have tried learning architectural drawing and designing 
but failed to do so because of the complexity of the tools. I, therefore, wanted to design a simple yet efficient 
tool that would help new architects and architecture students or just anyone who wants to explore new fields like I 
tried doing.

## User Stories

- The user can *choose the dimension of the room*.
- The user can *add* furniture in the room *in the position of their choice*. 
given that there is space.
- The user will also be able to *view all the furniture placed in the room so far*.
- The user can also *remove furniture* from the room.
- The user can *save* their progress.
- The user can *load* their previous progress at the start.

## Instructions for Grader

- When there's no room to load, at the start, the user is prompted with just one option to create a new room.
  - When the user selects this option, they are then prompted to enter their "username" and "dimension" of their intended room.
- When there is a room to load, at the start, the user is prompted with two options -- to create a new room or to load the room.
  - When the user selects either option, they are then prompted to enter their "username" and "dimension" of their intended room.
- If the user selected to create a new room, then a room of "dimension" of the choice of the user is created.
- If the user selected to load room, then the saved room is loaded.
- Now, if the user wants to place a chair in the canvas, then the user has to first select the chair button in the button panel.
  - After selecting the chair button from the button panel, all the available spots in the canvas will turn green. On clicking
    a green square, a chair will be placed at that spot.
- If the suer wants to place a sofa in the canvas, then the user has to select the sofa button in the button panel.
  - After selecting the sofa button from the button panel, all the available spots in the canvas will turn green. On clicking 
    a green square, the second available spots for the sofa will now turn red. 
  - On selecting one of the red squares, a sofa will be placed with according alignment (vertical or horizontal).
- If the user wants to place a centre table in the canvas, then the user has to select the centre table button in the button panel.
  - After selecting the centre table button from the button panel, a green square represents the top left spot of the centre table. 
    On selecting the green square, a centre table will be placed all the required spots.
- Now, if the user wants to remove a furniture from the canvas, then the user needs to click on the furniture that they wish to remove.
- Finally, to save the progress, the user can press the save button in the left-most side of the button panel.

## Phase 4: Task 2

Sample logs:
```
Tue Nov 28 11:59:50 PST 2023
Room displayed.


Tue Nov 28 11:59:55 PST 2023
Room displayed.


Tue Nov 28 11:59:58 PST 2023
CENTRETABLE added to the furniture list.


Tue Nov 28 12:00:00 PST 2023
SOFA added to the furniture list.


Tue Nov 28 12:00:01 PST 2023
CHAIR added to the furniture list.


Tue Nov 28 12:00:03 PST 2023
CHAIR added to the furniture list.


Tue Nov 28 12:00:05 PST 2023
CHAIR added to the furniture list.


Tue Nov 28 12:00:07 PST 2023
Chair removed.


```

## Phase 4: Task 3
If I had more time, I would have applied more efficient and better abstraction to the Furniture, Chair, Sofa, and CenterTable classes. I would have renamed all of my methods in the Room and ConsoleUI class. I would have used SuppressWarnings rather than making helper methods everywhere I had to cut down method length. This would make my code more readable. I would also remove the PanelGUI class as it is of no use for now.
