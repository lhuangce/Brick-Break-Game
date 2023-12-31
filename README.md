# My Personal Project: Brick Break Game

## Overview

This application will contain a simple game in which the user clears various bricks in the scene by hitting a moving 
ball with a paddle. **Anyone** with access to a computer can play the game, including but not limited to:
- Kids
- Adults
- Seniors

This project is of interest to me because I've wanted to learn to code a game since elementary school. I think this game
is a suitable complexity to attempt since the physics is relatively simple, and it's possible to customize features to 
make the game more interesting over time. The design is inspired by the arcade game *Atari Breakout.*

## User Stories

- As a user, I want to be able to control the paddle
- As a user, I want to be able to pause the game
- As a user, I want to be able to restart the game
- As a user, I want to create a specific sized list of bricks at the start of the game
- As a user, I want to have an option to save my game from the pause menu
- As a user, I want to have an option to load my save file when starting the game

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by entering the starting number of bricks when
starting a new game, creating the list of bricks.
- You can generate the second required action related to adding Xs to a Y by hitting a ball, so it contacts then removes
a brick from the list.
- You can locate my visual component by running the game, either by loading from a save file or starting a new game.
- You can save the state of my application by starting the game, pausing it by pressing the space bar, and then pressing
's'.
- You can reload the state of my application by choosing the 'Load from file' option when starting the game.

## Phase 4: Task 2
Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:33 PDT 2023: Brick added to list.

Mon Aug 07 01:16:40 PDT 2023: Brick removed from list.

Mon Aug 07 01:16:50 PDT 2023: Brick removed from list.

Mon Aug 07 01:16:59 PDT 2023: Brick removed from list.

Mon Aug 07 01:17:08 PDT 2023: Brick removed from list.

Mon Aug 07 01:17:17 PDT 2023: Brick removed from list.

Mon Aug 07 01:17:26 PDT 2023: Brick removed from list.

Mon Aug 07 01:17:35 PDT 2023: Brick removed from list.
- Note: event log does not print if start menu of game is closed

## Phase 4: Task 3
Given more time, I would refactor methods in the BrickBreakGame and BrickBreakFrame classes to improve coupling and 
cohesion in my project. In the BrickBreakGame class, I would first abstract the code for generating the list of bricks
in the setUp() method for better coupling. In addition, I would use the iterator design pattern to improve the
checkCollision() method, which currently uses a for loop that may be error-prone. 

Next, I would combine all in-game key press actions in the BrickBreakFrame class to improve coupling and cohesion. 
Currently, part of this functionality is located in the BrickBreakGame class, with a method call to access that method.
Keeping the code for this purpose in a single class improves cohesion, while eliminating the source of coupling.