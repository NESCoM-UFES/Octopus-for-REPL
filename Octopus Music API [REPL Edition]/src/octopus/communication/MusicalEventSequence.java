
package octopus.communication;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Leandro Costalonga
 * @version 1.0
 */
import octopus.Note;


public class MusicalEventSequence {//implements Playable{
  //protected double bpm = 120.0;
 // protected boolean isLoop = false;


protected Vector<MusicalEvent> musicalEvents;


  public MusicalEventSequence() {
    musicalEvents = new Vector<MusicalEvent>();
  }

  public MusicalEventSequence(MusicalEvent[] musicalEvents) {
  this.musicalEvents = new Vector<MusicalEvent>(Arrays.asList(musicalEvents));
 }

 public void addMusicalEventSequence(MusicalEventSequence musicalSequence) {
   this.musicalEvents.addAll(musicalSequence.musicalEvents);
 }

 public void addMusicalEvent(MusicalEvent musicalEvent){
     musicalEvents.add(musicalEvent);
 }
 
 //Em teste
 public double getDuration() {
	 MusicalEvent lastMusicalEvent = ((MusicalEvent)musicalEvents.lastElement());
	 double duration = lastMusicalEvent.timing + lastMusicalEvent.duration;
	 return duration;
	 /* double duration = 0.0;
	 for (MusicalEvent musicalEvent : musicalEvents) {
	
		 duration+=musicalEvent.duration;
	
	 }
	 return duration;*/
	 
 }

 public void delayEvents(double time){
   for (Enumeration<MusicalEvent> e = musicalEvents.elements() ; e.hasMoreElements() ;) {
     (e.nextElement()).timing+=time;
  }
 }
 
 /*
  * It will insert/replace the notes in the MusicalEvent(s). Used when MusicalEvents only
  * with rhythmic information is used. for example: Bar;
  */
 public void insertNotesToMusicalEvents(Note[] notes){
	 int smallerArray = notes.length<musicalEvents.size()? notes.length:musicalEvents.size();	 

	 for (int i = 0; i < smallerArray; i++) {
		 int timesTwo = i*2;
		 if(timesTwo<musicalEvents.size()){
			 musicalEvents.get(timesTwo).note = notes[i]; //note on
			 musicalEvents.get(timesTwo+1).note = notes[i];//note off
	     }
	 }
 }

 public  void print(double resolution){
   Collections.sort(musicalEvents);

   for (int i = 0; i < musicalEvents.size(); i++) {
     String line = "";
     MusicalEvent me =(musicalEvents.get(i));
     double startPos = (me.timing / resolution);
     double endPos = me.duration/resolution;
     if(me.velocity!=0){
       for (double s = 0; s < startPos; s++) {
         line += " ";
       }
       //line += "(" + me.note.getSymbol() + ")";
       for (double e = 0; e < endPos; e++) {
         line += "=";
       }
       System.out.println(line);
     }
   }

 }

  public MusicalEvent[] getMusicalEvents(){
    return musicalEvents.toArray(new MusicalEvent[0]);
  }

  /*public static void main(String[] args) {
    MusicalEventSequence playableMusic = new MusicalEventSequence();
  }*/

 /* public void setBpm(double bpm) {
    this.bpm = bpm;
  }

  public double getBpm() {
    return bpm;
  }
  
  public boolean isLooping() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}*/

}
