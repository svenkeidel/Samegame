package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import de.tu_darmstadt.gdi1.samegame.model.Level;

import de.tu_darmstadt.gdi1.samegame.exceptions.InternalFailureException;

/**
 * A thread to instantiate and start an animation of falling stones
 */
class AnimationThread extends Thread{

	private Byte[][] field;
	private MainPanel mainPanel;

	private long animationSpeed;

	/**
	 * A constructor to instantiate and start an animation of
	 * falling stones.
	 *
	 * @param field the field to animate
	 * @param animationSpeed the sleep time between each frame in nanoseconds
	 * @param mainPanel the main panel in which the animation is performed
	 */
	AnimationThread(Byte[][] field, long animationSpeed, MainPanel mainPanel){

		this.field = field;
		this.mainPanel = mainPanel;

		this.animationSpeed = animationSpeed;
	}

	@Override
	public void run(){
		boolean fieldstateHasChanged;

		do{
			fieldstateHasChanged = false;
			fieldstateHasChanged = Level.fallDownOneField(field);
			try{
				mainPanel.redraw();
			}catch(InternalFailureException e){
				e.printStackTrace();
			}

			if(fieldstateHasChanged){
				try{
					sleep(animationSpeed);
				}
				catch(InterruptedException ignored){}
			}
		}while(fieldstateHasChanged);

		mainPanel.endAnimation();
	}
}
