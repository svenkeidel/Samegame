/**
 * @note 
 * Controller: procceses on events 
 * sended by the View and call the 
 * specific function in the Model
 *
 * @depend - "updates state" - Level
 */
class GameController{}

/**
 * @note 
 * Model: The Level-data in the game. 
 * When the Model changes its state, 
 * it causes an ChangeEvent to the 
 * View-ChangeListener
 *
 * @navassoc - "causes state-changed-events" - SameGameViewer
 */
class Level {}

/**
 * @note 
 * View: The Presentation Layer. Is
 * updated by changing events caused
 * by the Model
 * @depend - "listen for state-changes" - Level
 * @navassoc 1 "sends events" 1 GameController
 */
class SameGameViewer{}
