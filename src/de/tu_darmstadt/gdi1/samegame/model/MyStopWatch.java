package de.tu_darmstadt.gdi1.samegame.model;

import org.apache.commons.lang.time.StopWatch;

/**
 * a customized version of commons.lang.tim.Stopwatch.
 * This version don't throw exceptions which must be caught if the watch
 * is in a wrong state. So if the watch is paused and suspend() is 
 * called, the watch does nothing neither to throw an exception.
 */
class MyStopWatch{

	/** 
	 * the apache stop watch.
	 */
	private StopWatch watch;
	
	/**
	 * the state of the watch.
	 */
	private boolean paused;

	/**
	 * the initial time offset.
	 */
	private long timeOffset;
	
	/**
	 * class constructor which sets a initial time offset of 0.
	 */
	public MyStopWatch(){
		watch = new StopWatch();
		this.paused = true;
		this.timeOffset = 0;
	}

	/**
	 * class constructor which sets a initial time offset given with the 
	 * parameter.
	 * @param initialTimeOffset sets the initial time offset of the watch
	 * which is added to the current time.
	 */
	public MyStopWatch(long initialTimeOffset){
		watch = new StopWatch();
		this.paused = true;
		this.timeOffset = initialTimeOffset;
	}

	/**
	 * get the initial time offset + the current time
	 */
	public long getTime(){
		return timeOffset + watch.getTime();
	}
	
	/**
	 * suspend the watch. Don't throw exceptions if the watch is already
	 * suspended
	 */
	public void suspend(){
		if(!paused){
			watch.suspend();
			paused = true;
		}
	}

	/**
	 * resume the watch. Don't throw exceptions if the watch is already
	 * running
	 */
	public void resume(){
		if(paused){
			watch.resume();
			paused = false;
		}
	}

	/**
	 * resets all values of the watch.
	 */
	public void reset(){
		watch.reset();
		paused = true;
		timeOffset = 0;
	}
	
	/**
	 * starts the watch.
	 */
	public void start(){
		if(paused){
			watch.start();
			paused = false;
		}
	}

	/**
	 * stops the watch.
	 */
	public void stop(){
		if(!paused){
			watch.stop();
			paused = true;
			timeOffset = 0;
		}
	}
}
