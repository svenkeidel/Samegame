/**
 * @hidden
 */
class UMLOptions{}

/**
 * de.tu_darmstadt.gdi1.samegame
 * @opt shape package
 * @opt commentname
 * @opt !constructors
 * @assoc - - - GameController
 * @assoc - - - Level
 * @assoc - - - SameGameViewer
 * @assoc - - - GameState
 * @navassoc - - - package_highscore
 * @navassoc - - - package_ui
 * @navassoc - - - package_gameframes
 */
class package_samegame{}

/** 
 * + GameController
 * @opt commentname
 */
class GameController{}

/** 
 * + Level
 * @opt commentname
 */
class Level {}

/** 
 * + SameGameViewer
 * @opt commentname
 */
class SameGameViewer{}

/** 
 * + GameState
 * @opt commentname
 */
class GameState{}

/**
 * de.tu_darmstadt.gdi1.samegame.highscore
 * @opt shape package
 * @opt commentname
 * @assoc - - - Highscore
 * @assoc - - - HighscoreEntry
 */
class package_highscore{}

/** 
 * + Highscore
 * @opt commentname
 */
class Highscore{}

/** 
 * ~ HighscoreEntry
 * @opt commentname
 */
class HighscoreEntry{}


/**
 * de.tu_darmstadt.gdi1.samegame.gameframes
 * @opt shape package
 * @opt commentname
 * @assoc - - - MainFrame
 * @assoc - - - MainPanel
 * @assoc - - - HighscoreFrame
 * @assoc - - - AskForSaveFrame
 * @assoc - - - AboutFrame
 * @assoc - - - SaveGameFrame
 * @assoc - - - OptionsFrame
 * @assoc - - - LoadGameFrame
 * @assoc - - - AddHighscoreFrame
 */
class package_gameframes{}


/**
 * de.tu_darmstadt.gdi1.samegame.ui
 * @opt shape package
 * @opt commentname
 * @assoc - - - GameWindow
 * @assoc - - - GamePanel
 */
class package_ui{}

/**
 * + GameWindow
 * @opt commentname
 */
class GameWindow{}

/**
 * + GamePanel
 * @opt commentname
 */
class GamePanel{}

/**
 * + MainFrame
 * @opt commentname
 */
class MainFrame extends GameWindow{}

/**
 * + MainFrame
 * @opt commentnam
 */
class MainPanel extends GamePanel{}

/**
 * + HighscoreFrame
 * @opt commentname
 */
class HighscoreFrame{}

/**
 * + AskForSaveFrame
 * @opt commentname
 */
class AskForSaveFrame{}

/**
 * + AboutFrame
 * @opt commentname
 */
class AboutFrame{} 

/**
 * + SaveGameFrame
 * @opt commentname
 */
class SaveGameFrame{}

/**
 * + OptionsFrame
 * @opt commentname
 */
class OptionsFrame{} 

/**
 * + LoadGameFrame
 * @opt commentname
 */
class LoadGameFrame{}

/**
 * + AddHighscoreFrame
 * @opt commentname
 */
class AddHighscoreFrame{}
