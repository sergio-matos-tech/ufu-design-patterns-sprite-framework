# Evolving a Java Game Framework with Design Patterns

This project is an academic work developed for the **"Princípios e Padrões de Projeto" (Design Principles and Patterns)** discipline at the Faculty of Computing (FACOM) of the Federal University of Uberlândia (UFU).

The primary objective was to **refactor and evolve a simple Java-based arcade game framework** that was originally tightly coupled to a single game, *Space Invaders*. By applying **SOLID principles** and **core design patterns**, the architecture was transformed into a flexible, extensible, and maintainable framework capable of supporting multiple, distinct games.

The success of this evolution is demonstrated by the implementation of two fully functional games on the same framework: the original *Space Invaders* and the new *Freeze Monster*.

---

## 🎮 Games Implemented

### 1. Freeze Monster

A new game developed on the evolved framework where the player must freeze all monsters on the screen while dodging their projectiles.

**Game Rules:**

* **Player Movement:** The player (Woody) can move in all 8 directions using the arrow keys.
* **Player Attack:** The player fires a freezing ray with the SPACE bar, which travels in a straight line based on the player's last direction of movement.
* **Enemies:** Monsters move randomly around the screen and fire goop projectiles in straight lines.
* **Win/Loss Condition:** The game ends when all monsters are frozen (**win**) or when the player is hit by a monster or a goop projectile (**lose**).

### 2. Space Invaders (Legacy)

The classic arcade game that served as the base for the original framework, which remains fully functional after the architectural refactoring.

**Game Rules:**

* **Player Movement:** The player's ship moves horizontally at the bottom of the screen.
* **Enemy Movement:** Aliens move as a monolithic block, descending each time they reach the screen's edge.
* **Enemy Attack:** Aliens drop bombs randomly.
* **Win/Loss Condition:** The game ends when all aliens are destroyed (**win**), the player is hit, or the aliens reach the bottom of the screen (**lose**).

---

## 🖼️ Game Previews
### Space Invaders:

<img width="498" height="504" alt="image" src="https://github.com/user-attachments/assets/09f4da78-eb02-4d0f-ae13-ce3eee7e4b3c" /> <br>
### Freeze Monsters:
<img width="498" height="504" alt="image" src="https://github.com/user-attachments/assets/683c28d5-de80-433d-9378-058cd7eadb83" />


---

## ⚙️ How to Run the Project

### Prerequisites

* Java Development Kit (**JDK 8** or higher)

### Compilation & Execution

The project can be compiled and run from the command line in any Linux-based environment.

```bash
# Clone the repository
git clone <your-repository-url>
cd <repository-directory>

# Compile all Java source files
javac -d out $(find src -name "*.java")

# Run Freeze Monster
java -cp out freezemonster.FreezeMonsterGame

# Run Space Invaders
java -cp out spaceinvaders.SpaceInvadersGame
```

---

## 🏗️ Architectural Analysis & Design Patterns

The core of this project was the **practical application of design patterns** to decouple components and promote extensibility.

### 1. Template Method Pattern

* **Abstract Class:** `spriteframework.AbstractBoard`
* **Template Methods:** `doGameCycle()` and `doDrawing()` define the invariant sequence of the game's logic (update, repaint) and rendering steps.
* **Hotspots (Abstract Operations):** Concrete boards (`FreezeMonsterBoard`, `SpaceInvadersBoard`) override methods like `update()`, `createBadSprites()`, and `drawOtherSprites()` to implement specific rules.

### 2. Factory Method Pattern

* **Factory Method:** `createPlayer(String image)` defined in `AbstractBoard`.
* **Concrete Creators:** `FreezeMonsterBoard` and `SpaceInvadersBoard` override this method to return their specific player types (`PlayerBilateral` or `PlayerUnilateral`).

### 3. Strategy Pattern

The most critical pattern used in the refactor, responsible for **decoupling sprites from movement behaviors**.

* **Strategy Interface:** `IMovementStrategy` defines `move(Sprite sprite)`.
* **Concrete Strategies:**

  * `PlayerUnilateralStrategy`: Horizontal-only movement (Space Invaders ship)
  * `PlayerBilateralStrategy`: 8-way movement (Freeze Monster player)
  * `Random8WayMovementStrategy`: Random roaming (monsters)
  * `StraightLine8WayStrategy`: Straight-line trajectory (Goop projectiles)
* **Context:** `Sprite` holds a reference to an `IMovementStrategy`. The `performMove()` method delegates to the strategy. Strategies are injected at runtime.

---

## ✅ SOLID Principles Applied

* **Single Responsibility Principle (SRP):**

  * Original boards acted as "God Classes".
  * Refactor delegated movement logic to **sprites** and **strategy objects**.

* **Liskov Substitution Principle (LSP):**

  * Fixed a violation in `BadSprite.getBadnesses()` (now returns an empty `LinkedList` instead of `null`).
  * This ensured client code could handle base and subtype objects uniformly.

---

## 🙌 Credits

* The original *Space Invaders* source code is based on the tutorial by **Jan Bodnar** at [zetcode.com](https://zetcode.com).
* This project was developed as academic work for the **Design Principles and Patterns discipline** at FACOM, UFU.
