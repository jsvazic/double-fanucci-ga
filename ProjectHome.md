# Project Moving! #
I've decided to migrate the code for Yet Another Double Fanucci Calculator (tm) to BitBucket in an attempt to better evaluate their platform for other private ventures that I am undertaking.  See:

http://bitbucket.org/jsvazic/yadfc/overview/

If you want to get involved in the development, just drop me a line and I'll add you to the list of developers.

# Status Update! #
I am leaving LoZ altogether since I do no appreciate how the game is being developed or run.  This does **NOT** mean that this project is dead!  Quite the opposite, I will continue maintenance on this project and provide new releases as appropriate.  I will scan the official forums for updates as appropriate, but feel free to log issues if you feel I may have missed something.  Thanks.

# Overview #
This is a simple application for finding optimized double fanucci hands for the [Legends of Zork](http://legendsofzork.com/) online game, which uses a genetic algorithm rather than recursion to find a solution.  What does this mean exactly?  Well, currently the recursion-based calculators can take anywhere from 1-30 seconds to generate a solution, depending on how far you want them to go.  By using a Genetic Algorithm (GA), YADFC can generate an optimal solution in less than 1 second!

## Status ##
### Version 2.3 now available! ###
  * Fixes a problem where the slot order wasn't being properly followed, i.e. the 'best' hands weren't always going to your first slot.
  * Added "Save As" functionality
  * The name of the deck you have open will now display in the title bar
  * Changes to a deck will add an indicator to the title bar indicating that a save needs to occur.

## Features ##
  * **Fast!**  Can find the best fit solution for a full Fanucci Deck in less than 1 second!
  * **Cross platform!**  Can run on Windows, Linux and Mac (requires [Java SE](http://java.sun.com/))
  * **Intuitive interface!**
    * Nice big buttons to help you select which cards you have in your deck!
  * **Save** and **Load** your Fanucci decks!
  * **Tweakable!** Completely customizable controls for the underlying algorithm!
  * **Coolness factor!**  This isn't your moms recursive calculator!  This one uses _Artificial Intelligence (AI)_!
  * **FREE!**  Also comes with source code for you curious types!
    * Also licensed under a very generous MIT License which lets you re-use whatever you want without any special concerns.

## Specifics ##
  * Written in Java
  * Using the feedback on Double Fanucci from the LoZ [forums](http://forums.jolt.co.uk/showthread.php?t=590090) (thanks all!).
  * Swing application as well as a command-line version
    * Command-line version requires a pre-formatted FanucciDeckXmlFile

## How To Run ##
  * You must have [Java SE](http://java.sun.com/) installed
  * Download the binary release of YADFC

### GUI Version ###
  * After downloading the executable, either double-click on the yadfc-v2.3.jar file or go to a command prompt and execute:
```
java -jar yadfc-v2.3.jar
```

### CLI Version ###
  * After downloading the executable, either double-click on the yadfc-v2.3.jar file or go to a command prompt and execute:
```
java -classpath yadfc-v2.3.jar com.arm.fanucci.CLICalculator <Fanucci deck file>
```
  * The Fanucci cards file is an [XML](FanucciDeckXmlFile.md) file that defines the cards that you have.
    * You need to manually create this file first.