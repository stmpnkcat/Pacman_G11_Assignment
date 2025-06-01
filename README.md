/*
 *
 * Name: Daniel Cheng
 * 
 * Date: 2024/12/13
 * 
 * Course: ICS3U1-05 Mr.Fernandes
 * 
 * Title: PacMan - Daniel Cheng
 * 
 * Description:
 * 
 * - Welcome to Daniel's PacMan Game, a retro video game based off Pac-Man
 * - Navigate the title page options with Tab and press Space to select option
 * - Use Arrow Keys to guide PacMan through a maze, avoiding Ghosts and collecting points
 * - Gaze at the high scores of others in the leader board page
 * - Reach the top of the leader board, where you can enter your name (less than 9 letters long)
 * 
 * Major Skills:
 * 
 * - Swing
 * 		- Basic components (JLabel, JButton, JPanel, etc)
 * 		- Layouts (GridLayout, GridBagLayout, BoxLayout, FlowLayout, BorderLayout, null layout)
 * 		- GridBagConstraints (for GridBagLayout)
 * 		- SwingConstants (for JLabel centering)
 * 
 * - "AI" Pathfinding (A* Pathfinding)
 * 		- Manhattan Distance
 * 		- ArrayList
 * 		- Maze Traversal
 * 		- Customizable penalty for going on a path with another ghost (prevents conga lines)
 * 		- Comparing using comparator
 * 		- Stack
 * 
 * - Data Structures
 * 		- Array
 * 		- Map
 * 		- List
 * 		- LinkedHashMap
 * 
 * - Files
 *		- File Reading
 *		- File Writing (appending)
 *
 * - Custom fonts
 * 		- GraphicsEnvironment
 * 
 * - Object Oriented Programming
 * 		- Constructors
 *		- Fields
 *		- Getters/Setters
 *		- Utility Methods
 *		- Extending another object
 *
 * - KeyListener
 * 		- Getting the arrows keys
 * 
 * - Playing sound
 * 		- AudioInputStream
 * 		- AudioSystem
 * 
 * - ActionListener
 * - FocusListener
 * - ImageIcon
 * - Math.random
 * - Timers
 * - Constants
 * - Try/Catch
 * - Collections custom comparator
 * - Array Traversal
 * 
 * Basic Features:
 * 
 * - Score
 * - PacMan cannot enter the ghosts' house
 * - Ghosts get out of the house easier (Escalator)
 * - Title Screen
 * - Sounds/Music
 * - Lives
 * - Power pellets
 * - Cherry bonus item
 * 
 * Advanced Features:
 * 
 * - Ghost pathfinding "AI"
 * - Leaderboard scores with names
 * 
 * Areas of Concern: 
 * 
 * - none
 * 
 * References:
 * 
 * - A* Algorithm: https://www.youtube.com/watch?v=-L-WgKMFuhE
 * - Playing Sound: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
 * - Custom Fonts: https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
 * - Appending to an existing file: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
 * - Sorting HashMap: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
 * 
 * - arcade_classic: https://www.dafont.com/arcade-classic-2.font
 * - logo: https://www.cleanpng.com/png-ms-pac-man-pac-man-vs-pac-man-plus-arcade-game-1667037/9.html
 * - background: https://www.mobygames.com/game/138/pac-man/screenshots/android/470252/
 * 
 * - Base Game: Nicholas Fernandes
 * 
 */
