package octopus.instrument.fretted;


import octopus.Note;
import octopus.NoteException;
import octopus.Notes;

/**
 *
 */

public class Guitar extends FrettedInstrument{// implements HarmonicInstrument{
  
	private static final long serialVersionUID = 1L;

public GuitarChordShapeBuilder chordShapeBuilder;


  public Guitar(){
    super("Standard Acustic Guitar");
    this.patchNumber = 24;
    configGuitar();
  }

/**
 * Creates a standard acustic guitar with 6 strings and 12 frets. The patch number
 * is 24 (GM Nylon Guitar). The standart tuning is:
 *  String 1: E5
 *  String 2: B4
 *  String 3: G4
 *  String 4: D4
 *  String 5: A4
 *  String 6: E4
 */
  public Guitar(String nome){
    super(nome);
    this.patchNumber = 24;
    configGuitar();
  }


  private void  configGuitar(){

      try{

        Note tempNote;

        InstumentString[] strings = new InstumentString[6];
        tempNote = Notes.getE();
        tempNote.setOctavePitch(5);

        strings[0] = new InstumentString(1, tempNote);

        tempNote = Notes.getB();
        tempNote.setOctavePitch(4);
        strings[1] = new InstumentString(2, tempNote);

        tempNote = Notes.getG();
        tempNote.setOctavePitch(4);
        strings[2] = new InstumentString(3, tempNote);

        tempNote = Notes.getD();
        tempNote.setOctavePitch(4);
        strings[3] = new InstumentString(4, tempNote);

        tempNote = Notes.getA();
        tempNote.setOctavePitch(3);
        strings[4] = new InstumentString(5, tempNote);

        tempNote = Notes.getE();
        tempNote.setOctavePitch(3);
        strings[5] = new InstumentString(6, tempNote);

        setStrings(strings);

        setNumberOfFrets(12);
        setPatchNumber(24);

        setInstrumentoInterface(new GuitarGraphicalInterface(this));

      } catch (NoteException noteEx){
        //Parameter in safe range.
      }

  }




}
