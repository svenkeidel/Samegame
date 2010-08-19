/**
 * @note 
 * Abstract Controller: procceses 
 * on events sended by the View 
 * and call the specific function 
 * in the Model
 *
 * Implements the designpattern 
 * "strategy", so if you want to
 * change the reaction of the program
 * on user events, you just have
 * to extend from this class
 *
 * @depend - "updates state" - Level
 */
class AbstractController{}

/**
 * @note
 * Concrete Controller: A Concrete
 * Strategy how to react on user
 * events
 */
class GameController extends AbstractController{}

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
 *
 * Implements the design pattern "Observer"
 * so if the model changes it's state,
 * the viewer release a cascade wich updates
 * all presantations wich represents the model
 *
 * @depend - "listen for state-changes" - Level
 * @navassoc 1 "sends events" 1 AbstractController
 */
class SameGameViewer{}
