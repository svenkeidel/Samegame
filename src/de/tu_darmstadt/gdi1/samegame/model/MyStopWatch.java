package de.tu_darmstadt.gdi1.samegame.model;

import org.apache.commons.lang.time.StopWatch;

class MyStopWatch{

	private StopWatch watch;
	
	private boolean paused;
	private long timeOffset;
	
	public MyStopWatch(){
		watch = new StopWatch();
		this.paused = true;
		this.timeOffset = 0;
	}

	public MyStopWatch(long initialTimeOffset){
		super();
		this.paused = true;
		this.timeOffset = initialTimeOffset;
	}

	public long getTime(){
		return timeOffset + watch.getTime();
	}
	
	public void suspend(){
		if(!paused){
			watch.suspend();
			paused = true;
		}
	}

	public void resume(){
		if(paused){
			watch.resume();
			paused = false;
		}
	}

	public void reset(){
		watch.reset();
		paused = true;
		timeOffset = 0;
	}
	
	public void start(){
		if(paused){
			watch.start();
			paused = false;
		}
	}

	public void stop(){
		if(!paused){
			watch.stop();
			paused = true;
			timeOffset = 0;
		}
	}
}
