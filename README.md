# Iago: An Othello Player

This project is an Othello player written in Java. The main goals of this project are to follow good software development practices and write clean code. Making a decent Othello AI wouldn't hurt, though.

## Usage

Clone this repository and run `./gradlew build`. You should be able to play a game of Othello on the command line.

Current algorithms include:

* `EASY`: this algorithm focuses purely on how many tiles (interior, edge, and corner) it controls.
* `MEDIUM`: this algorithm implements a negamax algorithm that looks at tiles and number of possible moves for both players.

## The Name

Why the name `iago`, though? I named it after the bird, obviously.

![I can't take it anymore!](https://media.tenor.com/fYui1xvwgV0AAAAC/iago-aladdin.gif)

Just kidding -- I love *Aladdin*, but I actually named this after Iago, the villain in Shakespeare's *Othello*, because, well... the play and the game share a name. That's basically it.
