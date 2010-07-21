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
 * @composed - - - TimeUpdate
 */
class Level {}

/**
 * - TimeUpdate
 * @opt commentname
 */
class TimeUpdate{}

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
 * + MainFrame
 * @opt commentname
 */
class MainFrame{}
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
