
#Game States Testing
Feature: Game States

  #Test1
  Scenario: Transition from ReadyState to GhostsCagedState
    Given the game is in ReadyState
    When the player makes first move
    Then the game state should become GhostsCagedState

  #Test2
  Scenario: Transition from ChaseState to PausedState
    Given the game is in ChaseState
    When the player pauses the game
    Then the game state should become PausedState

  #Test3
  Scenario: Trying to pause the game during ReadyState
    Given the game is in ReadyState
    When the player pauses the game
    Then the game state should remain as ReadyState

  #Test4
  Scenario: Transition to FrightenedState after Pacman eats a powered pellet
    Given the game is in ChaseState
    And there is a powered pellet near Pacman
    When Pacman eats the powered pellet
    Then the game state should become FrightenedState

  #Test5
  Scenario: Transition from FrightenedState to ChaseState after eating all ghosts
    Given the game is in FrightenedState
    And Pacman has eaten a powered pellet
    When Pacman eats all ghosts
    Then the game state should become ChaseState

  #Test6
  Scenario: Transition from ChaseState to GameEndedState after finishing the last level
    Given the game is in ChaseState
    And the desired level is set to 20
    When Pacman eats all dots and powered pellets
    Then the game state should become GameEndedState

  #Test7
  Scenario: Change from FrightenedState to ChaseState after the duration ends
    Given the game is in FrightenedState
    When the duration of FrightenedState has elapsed
    Then the game state should become ChaseState

  #Test8
  Scenario: Verify changing of levels is working
    Given the game is in ReadyState
    And there are no more dots and powered pellets in the environment
    When the game evolves
    Then the game state should remain as ReadyState


#Game Elements Testing
Feature: Game Elements

  #Test1:
  Scenario: Ghost eats Pacman
    Given the environment is set up
    And Pacman is next to a ghost
    When Pacman moves
    Then the symbol at the ghost's position should be the ghost's symbol

  #Test2:
  Scenario: Pacman eats powered pellet
    Given the Game is in ChaseState
    And Pacman is next to a powered pellet
    When Pacman moves towards the powered pellet
    Then the symbol at the powered pellet's position should be Pacman's symbol

  #Test3:
  Scenario: the Game is in ChaseState
    Given the environment is set up
    And Pacman is next to a dot
    When Pacman moves towards the dot
    Then the symbol at the dot's position should be Pacman's symbol

  #Test4:
  Scenario: Pacman eats the fruit
    Given the environment is set up
    And Pacman is next to a fruit
    When Pacman moves towards the fruit
    Then the symbol at the fruit's position should be Pacman's symbol

  #Test5:
  Scenario: After 20 pellets eaten, the fruit appears
    Given the environment is set up
    When Pacman has eaten 20 dots
    Then the fruit spawns

  #Test6:
  Scenario: Pacman doesn't trespass walls
    Given the environment is set up
    And Pacman is next to a wall
    When Pacman moves multiple times towards the wall
    Then the symbol at the wall's position should be the wall's symbol

  #Test7:
  Scenario: Ghost doesn't trespass walls
    Given the environment is set up
    And a ghost is next to a wall
    When the ghost moves towards the wall
    Then the symbol at the wall's position should be the wall's symbol

  #Test8:
    Scenario: Pacman eats ghost
      Given the Game is in FrightenedState
      And Pacman is next to a ghost
      When Pacman eats the ghost
      Then the symbol at the ghost's position should be Pacman's symbol

  #Test9:
  Scenario: Pacman loses a life when eaten by a ghost
    Given the environment is set up
    And Pacman is next to a ghost
    When Pacman moves towards the ghost
    Then the number of lives should decrease by 1

# Game Score Testing
Feature: Score Calculation

  #Test1
  Scenario: Score changes correctly when a dot is eaten
    Given the environment is set up
    And Pacman is next to a dot
    When Pacman moves towards the dot
    Then the score should increase by 1

  #Test2
  Scenario: Score changes correctly when PoweredPellet is eaten
    Given the environment is set up
    And Pacman is next to a PoweredPellet
    When Pacman moves towards the PoweredPellet
    Then the score should increase by 10

  #Test3
  Scenario: Score changes correctly when a Fruit is eaten
    Given the environment is set up
    And Pacman is next to a Fruit
    When Pacman moves towards the fruit
    Then the score should increase by 26

  #Test4
  Scenario: Score increments correctly after eating a ghost
    Given the game is in FrightenedState
    And there is a ghost nearby
    When Pacman moves towards the ghost
    Then the score should increase by 50

  #Test5
  Scenario: Incrementation of score after eating multiple ghosts
    Given the game is in FrightenedState
    And there are all ghosts nearby
    When Pacman moves multiple times towards the ghosts
    Then the score should increase by 300

#Game Interface Testing
Feature: Game Interface

  #Test1
  Scenario: Click "Start Game" Button
    Given the user is on the Initial Menu
    When the user clicks the "Start Game" button
    Then the interface appears matching exactly like the provided mock-up pacman_game_on.png

  #Test2
  Scenario: Click "Top 5 Score" Button
    Given the user is on the Initial Menu
    When the user clicks the "Top 5 Score" button
    Then the design matches exactly the provided mock-up top_5_score.png

  #Test3
  Scenario: Click "Exit" Button
    Given the user is on the Initial Menu
    When the user clicks the "Exit" button
    Then a pop-up window should appear matching exactly like the provided mock-up confirm_exit.png

  #Test4
  Scenario: Exit the application
    Given the user is on the confirm exist pop up (confirm_exit.ong)
    When the user confirms the exit
    Then the application should close

  #Test5
  Scenario: Click "Resume Game" Button
    Given the user is playing the Pacman game
    When the user pauses the game by pressing the 'P' key
    And clicks the "Resume Game" button on the pause interface
    Then the Pacman game interface should resume showing the design matching exactly like the provided mock-up pacman_game_on.png

  #Test6
  Scenario: Click "Save Game" Button
    Given the user is playing the Pacman game
    When the user pauses the game by pressing the 'P' key
    And clicks the "Save Game" button on the pause interface
    Then a pop-up window should appear, confirming that the game has been saved matching the design matching exactly like the provided mock-up saved_with_sucess.png

  #Test7
  Scenario: Click "Back to Menu" Button and Confirm
    Given the user is playing the Pacman game
    When the user pauses the game by pressing the 'P' key
    And clicks the "Back to Menu" button on the pause interface
    Then a pop-up window should appear, asking "Do you really want to go back to the initial menu?"
    When the user confirms going back to the menu
    Then the application should navigate back to the Initial Menu matching exactly like the provided mock-up initial_menu.pngsdxsssssssdd

  #Test8
  Scenario: Click "Back to Menu" Button and Cancel
    Given the user is playing the Pacman game
    When the user pauses the game by pressing the 'P' key
    And clicks the "Back to Menu" button on the pause interface
    Then a pop-up window should appear, asking "Do you really want to go back to the initial menu?"
    When the user cancels going back to the menu
    Then the application should remain in the paused state, waiting for further input
