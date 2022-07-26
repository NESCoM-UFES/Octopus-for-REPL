

package octopus;

import java.util.Vector;

import octopus.communication.MusicalEventSequence;

/**
 * Scale is used to instantiate a group of notes based on a particular intervals formation.
 * A Scale is to Notes what a HarmonicProgression is to Chords. 
 * It can be a Diatonic(7 notes) or Pentatonic(5 note) scale in both Major and Minor, in any mode or degree.
 *
 */
public class Scale implements Playable{ //Diatonic

	Note key;
	int mode;

	public static final int DEGREE_TONIC = 1;
	public static final int DEGREE_SUPERTONIC = 2;
	public static final int DEGREE_MEDIANT = 3;
	public static final int DEGREE_SUBDOMINANT = 4;
	public static final int DEGREE_DOMINANT = 5;
	public static final int DEGREE_SUBMEDIANT = 6;
	public static final int DEGREE_LEADING_NOTE = 7;

	public static final int MODE_IONIAN = 0;
	public static final int MODE_DORIAN = 1;
	public static final int MODE_PHRYGIAN = 2;
	public static final int MODE_LYDIAN = 3;
	public static final int MODE_MIXOLYDIAN = 4;
	public static final int MODE_AEOLIAN = 5;
	public static final int MODE_LOCRIAN = 6;

	public static final int MODE_MAJOR = 0;
	public static final int MODE_MINOR = 5;
	public static final int MODE_PENTA_MAJOR = 1;
	public static final int MODE_PENTA_MINOR = 3;

	// protected static int[] majorScale = {2,2,1,2,2,2,1};
	protected static Interval[] majorScaleI = {
		Intervals.getMajorSecond(), Intervals.getMajorSecond(),
		Intervals.getMinorSecond(), Intervals.getMajorSecond(),
		Intervals.getMajorSecond(),
		Intervals.getMajorSecond(), Intervals.getMinorSecond()};


	// protected static int[] minorScale = {2,1,2,2,1,2,2};
	protected static Interval[] minorScaleI = {
		Intervals.getMajorSecond(), Intervals.getMinorSecond(),
		Intervals.getMajorSecond(), Intervals.getMajorSecond(),
		Intervals.getMinorSecond(),
		Intervals.getMajorSecond(), Intervals.getMajorSecond()};

	// protected static int[] pentatonicMinorScale = {3,2,2,3,2};
	protected static Interval[] pentatonicMinorScale = {
		Intervals.getMinorThird(), Intervals.getMajorSecond(),
		Intervals.getMajorSecond(), Intervals.getMinorThird(),
		Intervals.getMajorSecond()};

	Note[] notes;

	//  protected static int[] pentatonicScale = {2,2,3,2,3};
	protected static Interval[] pentatonicMajorScale = {
		Intervals.getMajorSecond(), Intervals.getMajorSecond(),
		Intervals.getMinorThird(), Intervals.getMajorSecond(),
		Intervals.getMinorThird()};

	private static Note noteC = new Note("C", "C",
			Intervals.getMinorSecond(),
			Intervals.getMajorSecond());
	private static Note noteD = new Note("D", "D",
			Intervals.getMajorSecond(),
			Intervals.getMajorSecond());
	private static Note noteE = new Note("E", "E",
			Intervals.getMajorSecond(),
			Intervals.getMinorSecond());
	private static Note noteF = new Note("F", "F",
			Intervals.getMinorSecond(),
			Intervals.getMajorSecond());
	private static Note noteG = new Note("G", "G",
			Intervals.getMajorSecond(),
			Intervals.getMajorSecond());
	private static Note noteA = new Note("A", "A",
			Intervals.getMajorSecond(),
			Intervals.getMajorSecond());
	private static Note noteB = new Note("B", "A",
			Intervals.getMajorSecond(),
			Intervals.getMinorSecond());


	/*
	 * Contains only natural notes;
	 */
	protected static Note CmajDiatonicNotes[] = {
		noteC, noteD, noteE, noteF, noteG, noteA, noteB};
	protected static Note CmajPentaNotes[] = {
		noteC, noteD, noteE, noteG, noteA};

	protected static Note cromaticSharpNotes[] = {
		noteC, Notes.getC(Note.SHARP),
		noteD, Notes.getD(Note.SHARP),
		noteE, 
		noteF, Notes.getF(Note.SHARP),
		noteG, Notes.getG(Note.SHARP),
		noteA, Notes.getA(Note.SHARP),
		noteB};

	/*protected static Note cromaticFlatNotes[] = {
		Notes.getC(Note.FLAT),noteC, 
		Notes.getD(Note.FLAT),noteD,
		Notes.getE(Note.FLAT),noteE, 
		noteF, 
		Notes.getG(Note.FLAT),noteG,
		Notes.getA(Note.FLAT),noteA, 
		Notes.getB(Note.FLAT),noteB};*/
	
	protected static Note cromaticFlatNotes[] = {
			noteC, 
			Notes.getD(Note.FLAT),noteD,
			Notes.getE(Note.FLAT),noteE, 
			noteF, 
			Notes.getG(Note.FLAT),noteG,
			Notes.getA(Note.FLAT),noteA, 
			Notes.getB(Note.FLAT),noteB};


    /* Tested: All notes, sharps anf flats. MODE_MAJOR - OK
     * 
     */
	public static Scale getDiatonicScale(Note key, int mode) {
		Scale scale = null;
		
		if(mode == MODE_MAJOR)  scale = getScale(key, majorScaleI, CmajDiatonicNotes);
		else if (mode == MODE_MINOR )  scale = getScale(key, minorScaleI, CmajDiatonicNotes);
		
		//scale.setMode(mode);
		return scale;
	}

	
	public static Scale getPentatonicScale(Note key, int mode) throws
	NoteException {
		Scale scale = getScale(key, pentatonicMajorScale, CmajPentaNotes);
				
		if (mode == MODE_MINOR ) scale = getScale(key, pentatonicMinorScale, CmajPentaNotes);;
		return scale;
	}

	
	public static Scale getScale(Note key, int[] semitones) {
	
		Interval[] intervals = new Interval[semitones.length]; 
		for (int i = 0; i < semitones.length; i++) {
			//Interval interval = intervals[i];
			intervals[i] = Intervals.getInterval(semitones[i]);
		}
		return getScale(key,intervals,CmajDiatonicNotes);
		//return getScale(key,intervals);
	
	
	}
	private static Scale getScale(Note key, Interval[] intervals , Note notesScale[])  {
		Scale scale = new Scale();
		scale.notes = new Note[intervals.length];
		scale.notes[0] = key;
		int octaveSum = Notes.getDistance(Notes.getC(),scale.notes[0],false);

		//for (int i = 1; i < scale.notes.length; i++) {
		for (int i = 1; i < intervals.length; i++) {
			try {								
				scale.notes[i] = Notes.getNote(scale.notes[i - 1],
						intervals[i - 1]);
			} catch (NoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Checks is the a full cycle of the scale waa completed and update the octavePitch to next octave.

			/*  octaveSum+=intervals[i - 1].getDistanceFromRoot();
      if (octaveSum >11){
         scale.notes[i].octavePitch++; 
      }*/

		}
		return scale;

	}

	public static Scale getCromaticScale(Note key) throws NoteException{
		if((key.isDoubleFlat())||(key.isDoubleSharp())){
			throw new NoteException("Double flat or sharp not supported in this method", key.getSymbol());
		}

		Scale scale = new Scale();
		scale.notes = new Note[12];
		Note cromaticNotes[] = key.isFlat()?cromaticFlatNotes:cromaticSharpNotes;

		int octave = key.getOctavePitch();
		scale.notes[0] = key;
		int cont=1;
 		int i = Notes.getCromaticNoteIndex(key) + 1;
 		
		while(cont<12){
			if(i>cromaticNotes.length - 1){
				i=0;
				octave++;
			}/*else {
				if (i < 0) {
					octave--;
					i=0;
				}
			}*/
			
			
			/*if((cont==0)&&(i==0)){
				scaleNote = key;
			}*/
			scale.notes[cont] = (Note)cromaticNotes[i].clone();;
			scale.notes[cont].setOctavePitch(octave);
			cont++;
			i++;
		}

		return scale;
	}

	public int getSize(){
		return notes.length + 1;
	}

	//Funciona 100% mas limitado a escala diatonica - Deixar de referencia
	/*private static Scale getDiatonicScale(Note key){
    Scale scale = new Scale();
    scale.notes = new Note[7];
    Note notaRetorno = null;
    boolean accidented = key.isAccidental();
    boolean sharp = key.isSharp();
    boolean flat = key.isFlat();

    int cont = 1;
    Note baseNote = NoteCollection.getNote(key.getNotaBase());
    scale.notes[0] = key;
    int   posNote = NoteCollection.getNoteIndex(baseNote);
    posNote = posNote==6?0:posNote+ 1;
    while(cont<7){
      int distScale = majorScale[cont -1];
      Note note = (Note)NoteCollection.notas[posNote].clone();
   int distNotes = NoteCollection.getDistance(scale.notes[cont-1],note,false);
      if(distNotes==distScale){
        scale.notes[cont] = note;
      }else{
        if (distNotes>distScale){
          scale.notes[cont] = NoteCollection.getFlat(note);
          if(distNotes-distScale>1){
            scale.notes[cont].name+=" 2";
          }
        }else{
          scale.notes[cont] = NoteCollection.getSharp(note);
          if(distScale-distNotes>1){
            scale.notes[cont].name+="2";
          }

        }
      }

      posNote = posNote==6?0:posNote+ 1;
      cont++;
    }


      return scale;
     }*/



	@SuppressWarnings("unused")
	private static int getNoteIndex(Note[] notes, Note note) {
		int r = -1;
		for (int i = 0; i < notes.length; i++) {
			if (notes[i].getSymbol().equals(note.getSymbol())) {
				return i;
			}
		}
		return r;
	}

	public void setMode(int mode) {

		Note[] notesTemp = new Note[notes.length];
		int i2 = 0;
		int i1 = mode;
		int octaveCorretion = 0;

		while ((i2 < notes.length) &&
				(i1 < notes.length)){ // If you choose a mode higher than 4 in a pentatonic scale it will trigger an error.
			notesTemp[i2] = notes[i1];
			notesTemp[i2].octavePitch+=octaveCorretion;
			i2++;
			if(i1 == notes.length - 1){
				i1=0;
				octaveCorretion = 1;
			}else{
				i1 = i1 + 1;
			}

		}
		if(notesTemp[0] != null){
			notes = notesTemp;
		}

	}

	@Override
	public String toString() {
		String retorno = "";
		retorno+=(
				"------------------------------------------------------------------- \n");
		for (int i = 0; i < notes.length; i++) {

			retorno+=(notes[i].toString() + " Midi: " + notes[i].getMidiValue() + "\n");

		}
		return retorno;
	}
	public Note[] getNotes() {
		return notes;
	}

	public Note[] getSuffledNotes(int nNotes){
		Vector<Note> returnNotes = new Vector<Note>();

		for (int i = 0; i < nNotes; i++) {
			int randomIndex = (int)(Math.random()* notes.length);		  
			returnNotes.add(notes[randomIndex]);		
		}
		return returnNotes.toArray(new Note[0]);
	}

	public Note getNote(int degree) {
		return notes[degree-1];
	}

	@Override
	public MusicalEventSequence getMusicalEventSequence() {
		RhythmPattern rhythmPattern = RhythmPattern.getConstantRhythmPattern(notes.length, RhythmConstants.QUARTER_NOTE);  

		Melody melody = new Melody(this.getNotes(), rhythmPattern);
		MusicalEventSequence musicalEventSequence = melody.getMusicalEventSequence();

		return musicalEventSequence;    
	}





}




