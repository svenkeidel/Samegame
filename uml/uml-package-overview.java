/**
 * @hidden
 */
class UMLOptions{}


/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame
 * @opt shape package
 * @opt commentname
 * @opt !constructors
 * @navassoc - - - package_model
 * @navassoc - - - package_view
 * @navassoc - - - package_controller
 */
class package_samegame{}
/*===================================================================*/


/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame.controller
 * @opt shape package
 * @opt commentname
 * @assoc - - - AbstractController
 * @assoc - - - GameController
 */
class package_controller{}

/** 
 * + AbstractController
 * @opt commentname
 */
class AbstractController{}

/** 
 * + GameController
 * @opt commentname
 */
class GameController extends AbstractController{}
/*===================================================================*/


/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame.model
 * @opt shape package
 * @opt commentname
 * @assoc - - - Level
 * @assoc - - - GameState
 * @navassoc - - - package_highscore
 */
class package_model{}

/** 
 * + Level
 * @opt commentname
 */
class Level {}

/** 
 * + GameState
 * @opt commentname
 */
class GameState{}
/*===================================================================*/

/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame.model.highscore
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
/*===================================================================*/


/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame.view
 * @opt shape package
 * @opt commentname
 * @assoc - - - SameGameViewer
 * @navassoc - - - package_gameframes
 */
class package_view{}

/** 
 * + SameGameViewer
 * @opt commentname
 */
class SameGameViewer{}
/*===================================================================*/


/*===================================================================*/
/**
 * de.tu_darmstadt.gdi1.samegame.view.gameframes
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
 * + MainFrame
 * @opt commentname
 */
class MainFrame{}

/**
 * + MainFrame
 * @opt commentnam
 */
class MainPanel{}

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
/*===================================================================*/
