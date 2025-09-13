UFU Design Patterns Sprite Framework

This project is an academic work developed for the "Princípios e Padrões de Projeto" (Design Principles and Patterns) discipline at the Faculty of Computing (FACOM) of the Federal University of Uberlândia (UFU).
Project Overview

The primary objective of this project is to refactor and evolve a simple Java-based arcade game framework. The original framework was tightly coupled to a single game, Space Invaders. The goal is to apply SOLID principles and key design patterns to create a more flexible, extensible, and maintainable architecture that can support multiple, distinct games.

The refactored framework now supports two games:

    Space Invaders (Legacy): The original game, which must remain functional after the architectural changes.

    Freeze Monster (New): A new game with different mechanics, implemented on top of the evolved framework.

This evolution demonstrates the practical application of the Strategy, Template Method, and Factory Method design patterns to solve common software design problems.
Games Implemented
1. Freeze Monster

A new game where the player must freeze all monsters on the screen while dodging projectiles.

Game Rules:

    The player (Woody) can move in all 8 directions using the arrow keys.

    The player fires a freezing ray with the SPACE bar, which travels in the player's last direction of movement.

    Monsters move randomly and fire goop projectiles in straight lines.

    The game ends when all monsters are frozen (win) or when the player is hit by a monster or goop (lose).

2. Space Invaders

The classic arcade game that served as the base for the original framework.

Game Rules:

    The player ship moves horizontally.

    Aliens move as a block, descending each time they reach the screen's edge.

    Aliens drop bombs randomly.

    The game ends when all aliens are destroyed (win) or the aliens reach the bottom (lose).

How to Run
Prerequisites

    Java Development Kit (JDK) 1.8 or higher.

Compilation & Execution

    Clone the repository:

    git clone <your-repository-url>
    cd ufu-design-patterns-sprite-framework

    Compile the source code from the root directory:

    javac -d out $(find src -name "*.java")

    (Note: This command is for Linux/macOS. For Windows, you may need an alternative to find)

    Run a game:

        To run Freeze Monster:

        java -cp out freezemonster.FreezeMonsterGame

        To run Space Invaders:

        java -cp out spaceinvaders.SpaceInvadersGame

Architectural Analysis & Design Patterns

The core of this project involved applying the following patterns and principles:

    Template Method Pattern: The spriteframework.AbstractBoard class defines the main game loop (doGameCycle) and drawing process (doDrawing) as a template. Concrete game boards like FreezeMonsterBoard and SpaceInvadersBoard override specific steps (update, drawBadSprites, etc.) to implement their unique logic without altering the core algorithm structure.

    Strategy Pattern: The movement logic for game sprites is encapsulated in separate IMovementStrategy classes. This decouples the sprites from their movement behavior, allowing different strategies (e.g., PlayerBilateralStrategy, Random8WayMovementStrategy) to be injected at runtime. This was crucial for supporting the different movement rules of both games.

    Factory Method Pattern: The AbstractBoard uses a createPlayer method, allowing subclasses to decide the concrete type of Player sprite to instantiate, thereby promoting loose coupling.

    SOLID Principles: The refactoring effort focused on addressing violations of the Single Responsibility Principle (by breaking up monolithic "Board" classes) and the Liskov Substitution Principle (by ensuring sprite state models were consistent and extensible).

Credits

The original Space Invaders source code is based on the tutorial by Jan Bodnar at zetcode.com.
