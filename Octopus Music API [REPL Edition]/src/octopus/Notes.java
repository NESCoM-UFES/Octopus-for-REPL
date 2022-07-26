package octopus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * The Notes class creates and performs computations over Note objects.   
 *
 * @see Interval
 * @see Note
 * @author Leandro Lesqueves Costalonga
 * @version 1.1
 */
public class Notes {


	private static Note A = new Note("A","A",Intervals.getMajorSecond(),Intervals.getMajorSecond());
	private static Note B = new Note("B","B",Intervals.getMajorSecond(),Intervals.getMinorSecond());
	private static Note C = new Note("C","C",Intervals.getMinorSecond(),Intervals.getMajorSecond());
	private static Note D = new Note("D","D",Intervals.getMajorSecond(),Intervals.getMajorSecond());
	private static Note E = new Note("E","E",Intervals.getMajorSecond(),Intervals.getMinorSecond());
	private static Note F = new Note("F","F",Intervals.getMinorSecond(),Intervals.getMajorSecond());
	private static Note G = new Note("G","G",Intervals.getMajorSecond(),Intervals.getMajorSecond());

	//Diatonic Scale: Used to find the relationship between natural notes.
	static Note notes[] = {C, D, E, F, G, A, B};

	//Cromatic Scles: Udes to deal with de flat and sharp notes. 
	static Object cromaticNotes[] = {C,"accidented note ", D,"accidented note ", E, F,
		"accidented note ", G, "accidented note ", A, "accidented note ",
		B};



	/*
	 * Retorna a nota L�.
	 * @return A nota L�.
	 */
	public static Note getA() {return (Note)A.clone();}
	

	public static Note getA(int accident) {
		Note retorno = (Note)A.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}

	/*
	 * Retorna a nota Si.
	 * @return A nota Si.
	 */
	public static Note getB() {return (Note)B.clone();}
	public static Note getB(int accident) {
		Note retorno = (Note)B.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}
	public static Note getBaseNote(Note note) throws NoteException{
		return getNote(getBaseNoteSymbol(note));
	}
	public static String getBaseNoteSymbol(Note note) {
		return note.getSymbol().substring(0,1);
	}
	/**
	 * Return a clone of the C note;
	 * @return
	 */
	public static Note getC() { return (Note)C.clone();}
	public static Note getC(int accident) {
		Note retorno = (Note)C.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}

		return retorno;

	}
	// getCromaticNoteIndex recebe como par�metro um objeto do tipo
	// Note. Retorna o �ndice (0 - 11) desta Note na escala cromatica.
	/*
	 * Retorna a posi��o da Note na escala crom�tica.
	 * @param nota Note que se busca a posi��o.
	 * @return Posi��o da Note na rela��o de notas crom�ticas.
	 */
	public static int getCromaticNoteIndex(Note nota) {
		int valorAlteracao = 0;
		int indexNotaRetorno = -1;

		if (nota.isAccidental()) {
			String acc = nota.getAccident();
			if(acc.equals("#")){
				valorAlteracao = 1;
			}else{
				if(acc.equals("b")){
					valorAlteracao = -1;
				}else{
					if(acc.equals("##")){
						valorAlteracao = 2;
					}else{
						if(acc.equals("bb")){
							valorAlteracao = -2;
						}
					}
				}
			}
			//valorAlteracao = (nota.getAccident().equals("#"))? (1) :(- 1);

			try {

				nota = getNote(getBaseNoteSymbol(nota));

			} catch (NoteException e) {
				return -1;
			}
		}

		// Buscando a fundamental
		for (int i=0;i<12 ;i++ ) {
			if (cromaticNotes[i] instanceof Note) {
				if (nota.symbol.equals(((Note) cromaticNotes[i]).symbol)){
					indexNotaRetorno += (i+1);
					break;
				}
			}
		}

		indexNotaRetorno = (indexNotaRetorno != -1)? indexNotaRetorno+=valorAlteracao: -1;

		// Tratamento circular para n�o estourar o vetor;
		while (indexNotaRetorno > 11) {
			indexNotaRetorno -= 12; // Diminuo pela quantidade de elementos do vetor!
			//oitava++;
		}
		
		/*while (indexNotaRetorno < 0) {
			indexNotaRetorno += 12; // Diminuo pela quantidade de elementos do vetor!
			//oitava++;
		}*/
		
		
		return indexNotaRetorno;
	}
	/*
	 * Retorna a nota R�.
	 * @return A nota R�.
	 */
	public static Note getD() {return (Note)D.clone();}
	public static Note getD(int accident) {
		Note retorno = (Note)D.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}
	/*
	 * Retorna a quantidade de semitons entre duas notas,
	 * podendo considerar as regi�es de oitava ou n�o.
	 * @param nota1 Note origem.
	 * @param nota2 Note destino.
	 * @param considerOctave Considera-se regi�es de oitava ou n�o.
	 * @return Quantidade de semitons entre duas notas.
	 */
	public static int getDistance(Note note1, Note note2, boolean octaveCounting)  {
		int retorno;
		int posNota1 = getCromaticNoteIndex(note1);
		int posNota2 = getCromaticNoteIndex(note2);

		if (octaveCounting) {
			int indiceNota1 = note1.getOctavePitch();
			int indiceNota2 = note2.getOctavePitch();
			int diferenca = 0;

			if (indiceNota1!=indiceNota2) {
				if (indiceNota1 < indiceNota2) {
					diferenca = indiceNota2 - indiceNota1;
					retorno=((diferenca * 12) + (posNota2 - posNota1));
				}else{
					diferenca = indiceNota1 - indiceNota2;
					retorno=((diferenca * -12) + (posNota2 - posNota1));
				}
			}else{
				retorno = posNota2 -posNota1;
			}
		}else{
			retorno = posNota2 -posNota1;
			retorno = (retorno < 0)?retorno+=12: retorno;
		}

		return retorno;
	}
	public static Note getDoubleFlat(Note notaBase) {
		Note notaAlteradaRetorno;
		String simbNovaNota = notaBase.getSymbol() + "bb";
		String nomeNovaNota = notaBase.getName() + " Double Flat";
		/*@todo errado*/
		notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota, Intervals.getMinorSecond(),Intervals.getMinorSecond());
		return notaAlteradaRetorno;
	}
	public static Note getDoubleSharp(Note notaBase) {
		Note notaAlteradaRetorno;
		String simbNovaNota = notaBase.getSymbol() + "##";
		String nomeNovaNota = notaBase.getName() + " Double Sharp";
		notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota,Intervals.getMinorSecond(),
				Intervals.getMinorSecond());
		return notaAlteradaRetorno;
	}
	/*
	 * Retorna a nota Mi.
	 * @return A nota Mi.
	 */
	public static Note getE() {return (Note)E.clone();}

	public static Note getE(int accident) {
		Note retorno = (Note)E.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}
	/*
	 * Retorna a nota F�.
	 * @return A nota F�.
	 */
	public static Note getF() {return (Note)F.clone();}

	// Para transformar a Note em Note com bemol � necess�rio acrescentar o "b"
	// ao symbol da notaBase, acrescentar a String "Bemol" ao nomeNota
	// e criar a notaAlteradaRetorno passando a Note, o nome da Note e os intervalos
	// anterior e posterior.
	/*
	 * Retorna uma Note com bemol.
	 * @param notaBase A Note que ser� transformada em Note com bemol.
	 * @return Note com bemol.
	 */

	public static Note getF(int accident) {
		Note retorno = (Note)F.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}

	public static Note getFlat(Note notaBase) {
		Note notaAlteradaRetorno;
		String simbNovaNota = notaBase.getSymbol() + "b";
		String nomeNovaNota = notaBase.getName() + " Flat";
		notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota, Intervals.getMinorSecond(),Intervals.getMinorSecond());

		return notaAlteradaRetorno;
	}


	/*
	 * Retorna a nota Sol.
	 * @return A nota Sol.
	 */
	public static Note getG() {return (Note)G.clone();}

	public static Note getG(int accident) {
		Note retorno = (Note)G.clone();
		switch (accident)    {
		case -2: retorno = getDoubleFlat(retorno); break;
		case -1: retorno = getFlat(retorno); break;
		case 1: retorno = getSharp(retorno); break;
		case 2: retorno = getDoubleSharp(retorno); break;
		}
		return retorno;

	}

	public static Note getNote(Note notaFundamental, int semitones)  throws NoteException{
		
		
		if (semitones ==0) return notaFundamental;
		
		Interval intervalo = Intervals.getInterval(Math.abs(semitones));		
		if(semitones > 0){

			return getNote(notaFundamental, intervalo);
		}else{

			return getNote(notaFundamental, intervalo, true);
		}
		
	}
		
		private static Note getNote(Note fundamentalNote, Interval interval, boolean countDown) throws NoteException{

			Note returnNote= null;
			int cont = 1;
			Note baseNote = Notes.getNote(getBaseNoteSymbol(fundamentalNote));
			baseNote.setOctavePitch(fundamentalNote.getOctavePitch());
			int posNote = Notes.getNoteIndex(baseNote);
			posNote-= (interval.getNumericInterval() -1); // -1 ou +1 ?
			int octave = fundamentalNote.getOctavePitch();
			while (posNote <0){
				posNote+=7;
				octave--;
			}
			returnNote = (Note)notes[posNote].clone();


			int distNotes = Notes.getDistance(fundamentalNote,returnNote,false);
			int dif = interval.getDistanceFromRoot() + distNotes;
			while (dif > 11) {
				dif -= 12; // Diminuo pela quantidade de elementos do vetor!
			}
			while (dif < -11) {
				dif += 12; // Diminuo pela quantidade de elementos do vetor!
			}


			/*switch(dif){
			case 1: returnNote= Notes.getSharp(returnNote);break;
			case -11: returnNote = Notes.getSharp(returnNote);break;
			case 2: returnNote = Notes.getDoubleSharp(returnNote);break;
			case -10: returnNote = Notes.getDoubleSharp(returnNote);break;
			case -1: returnNote= Notes.getFlat(returnNote);break;
			case 11: returnNote = Notes.getFlat(returnNote);break;
			case -2: returnNote = Notes.getDoubleFlat(returnNote);break;
			case 10: returnNote = Notes.getDoubleFlat(returnNote);break;

			}*/
			
			switch(dif){
			case -1: returnNote= Notes.getSharp(returnNote);break;
			case 11: returnNote = Notes.getSharp(returnNote);break;
			case -2: returnNote = Notes.getDoubleSharp(returnNote);break;
			case 10: returnNote = Notes.getDoubleSharp(returnNote);break;
			case 1: returnNote= Notes.getFlat(returnNote);break;
			case -11: returnNote = Notes.getFlat(returnNote);break;
			case 2: returnNote = Notes.getDoubleFlat(returnNote);break;
			case -10: returnNote = Notes.getDoubleFlat(returnNote);break;

			}


			if ((dif>2)&& (dif<10)||
					(dif > -10)&&(dif < -2)){
				throw new NoteException(
						"Is not allowed double sharp or flat for this note and this interval. \n"
								+ "Try to use next natural note instead.",fundamentalNote.getName());
			}


			returnNote.setOctavePitch(octave);

			return returnNote;
		}
	
	public static Note getNote(Note fundamentalNote, Interval interval) throws NoteException{

		Note returnNote= null;
		int cont = 1;
		Note baseNote = Notes.getNote(getBaseNoteSymbol(fundamentalNote));
		baseNote.setOctavePitch(fundamentalNote.getOctavePitch());
		int posNote = Notes.getNoteIndex(baseNote);
		posNote+= (interval.getNumericInterval() -1);
		int octave = fundamentalNote.getOctavePitch();
		while (posNote > 6){
			posNote-=7;
			octave++;
		}
		returnNote = (Note)notes[posNote].clone();


		int distNotes = Notes.getDistance(fundamentalNote,returnNote,false);
		int dif = interval.getDistanceFromRoot() - distNotes;
		while (dif > 11) {
			dif -= 12; // Diminuo pela quantidade de elementos do vetor!
		}
		while (dif < -11) {
			dif += 12; // Diminuo pela quantidade de elementos do vetor!
		}


		switch(dif){
		case 1: returnNote= Notes.getSharp(returnNote);break;
		case -11: returnNote = Notes.getSharp(returnNote);break;
		case 2: returnNote = Notes.getDoubleSharp(returnNote);break;
		case -10: returnNote = Notes.getDoubleSharp(returnNote);break;
		case -1: returnNote= Notes.getFlat(returnNote);break;
		case 11: returnNote = Notes.getFlat(returnNote);break;
		case -2: returnNote = Notes.getDoubleFlat(returnNote);break;
		case 10: returnNote = Notes.getDoubleFlat(returnNote);break;

		}


		if ((dif>2)&& (dif<10)||
				(dif > -10)&&(dif < -2)){
			throw new NoteException(
					"Is not allowed double sharp or flat for this note and this interval. \n"
							+ "Try to use next natural note instead.",fundamentalNote.getName());
		}


		returnNote.setOctavePitch(octave);

		return returnNote;
	}


	/*
	 * Retorna a Note correspondente ao argumento passado. Ex: "D" -> Note R�.
	 * @param symbol S�mbolo que dar� origem a uma Note.
	 * @return A Note correspondente ao argumento passado.
	 */
	public static Note getNote(String noteSymbol) throws NoteException {
		//try{
		return getNote(noteSymbol, 4);
		/*}catch(NoteException noteEx){
     // always pass OctavePitch 4...so no worries.
     return null;
   }*/
	}
	public static Note getNote(String noteSymbol, int octavePitch) throws
	NoteException {

		Note notaRetorno = null;

		int indexNota = -1;
		String nota = noteSymbol;
		String alteracao = null;

		if (isAccidentalNote(noteSymbol)) {
			nota = noteSymbol.substring(0,1);
			alteracao = noteSymbol.substring(1,2);
		}

		for (int i=0;i < notes.length; i++) {
			if (nota.equals(notes[i].getSymbol())) {
				indexNota = i;
				notaRetorno = (Note)notes[i].clone();
				break;
			}
		}

		if(notaRetorno == null){
			throw new NoteException("Unknown note symbol:" + noteSymbol, noteSymbol);
		}
		
		if (alteracao != null) {
			if (alteracao.equals("#")) {

				String intervalo = notes[indexNota].getNextInterval().getSymbol();
				if (intervalo.equals("2")) {
					notaRetorno = getSharp(notes[indexNota]);
				}else{
					throw new NoteException("This note can not be sharp",notes[indexNota].getSymbol());
					//System.out.println("Esta nota n�o pode ser Sustenida");
					//erro
				}
			}else{
				if (alteracao.equals("b")) {


					String intervalo = notes[indexNota].getPreviousInterval().getSymbol();
					if (intervalo.equals("2")) {
						notaRetorno = getFlat(notes[indexNota]);
					}else{
						//System.out.println("Esta nota n�o pode ser Bemol");
						throw new NoteException("This note can not be flat : " + notes[indexNota].getSymbol(), notes[indexNota].getSymbol());
						//erro
					}
				}
			}
		}

		if(notaRetorno!= null){
			notaRetorno.setOctavePitch(octavePitch);
		}

		return notaRetorno;
	}

	/*
	 * Dada uma nota e dado um intervalo retorna a Note correspondente ao intervalo
	 * dado contando a partir da nota dada. Note que a nota e o intervalo s�o textos.
	 * Ex: ("C","b3") -> Note Mi bemol.
	 * @param simbFundamental S�mbolo que dar� origem a uma Note.
	 * @param simbIntervalo S�mbolo que dar� origem a um Interval.
	 * @return A Note correspondente ao intervalo dado contando a partir da nota dada.
	 */
	public static Note getNote(String simbFundamental, String simbIntervalo)  throws NoteException {
		Interval intervalo = Intervals.getInterval(simbIntervalo);
		Note notaFundamental = getNote(simbFundamental);
		return getNote(notaFundamental, intervalo);
	}

	public static Note[] getNotes(String[] noteSymbols) throws NoteException{
		
		Note[] notes = new Note[noteSymbols.length];
		for (int i = 0; i < noteSymbols.length; i++) {
			notes[i] = getNote(noteSymbols[i]);
		}
		return notes;
	
	}
	
	/*
	 * Retorna a posi��o da nota na rela��o de Notas (Notas naturais).
	 * @param notaProcurada Note que se busca a posi��o.
	 * @return Posi��o da Note na rela��o de notas naturais.
	 */
	public  static int getNoteIndex(Note notaProcurada) {
		int indexRetorno = -1;
		for (int i =0; i< notes.length ; i++) {
			if (notaProcurada.symbol == notes[i].getSymbol()) {
				indexRetorno = i;
				break;
			}
		}
		return indexRetorno;
	}





	// Para transformar a Note em Note com sustenido � necess�rio acrescentar o "#"
	// ao symbol da notaBase, acrescentar a String "Sustenido" ao nomeNota
	// e criar a notaAlteradaRetorno passando a Note, o nome da Note e os intervalos
	// anterior e posterior.
	/*
	 * Retorna uma Note com sustenido.
	 * @param notaBase A Note que ser� transformada em Note com sustenido.
	 * @return Note com sustenido.
	 */
	public static Note getSharp(Note notaBase) {
		Note notaAlteradaRetorno;
		String simbNovaNota = notaBase.getSymbol() + "#";
		String nomeNovaNota = notaBase.getName() + " Sharp";
		notaAlteradaRetorno = new Note(simbNovaNota, nomeNovaNota,Intervals.getMinorSecond(),
				Intervals.getMinorSecond());
		return notaAlteradaRetorno;
	}

	/*
	 * Verifica se a nota � alterada (D�#, Mib etc.).
	 * Ou seja verifica se na String passada como argumento existe o caracter '#' (Sustenido)
	 * ou 'b'(Bemol), indicando uma altera��o na nota.
	 * @param simbNota Note que pode ser alterada ou n�o.
	 * @return True se a nota for alterada, False caso contr�rio.
	 */
	private static boolean isAccidentalNote(String simbNota) {
		boolean retorno;
		if ((simbNota.indexOf('#')!= -1)||(simbNota.indexOf('b')!= -1)) {
			retorno = true;
		}else{
			retorno= false;
		}
		return retorno;
	}

	public static Note[] transpose(Note[] notes, int semitones) throws NoteException{
		int octaves = 0;
		while(semitones >11){
			 semitones-=12; 
			 octaves++;
		 }
		for (int i = 0; i < notes.length; i++) {
			notes[i] = getNote(notes[i],semitones);
			int noteOctave = notes[i].getOctavePitch();
			if(semitones > 0) notes[i].setOctavePitch(noteOctave + octaves);
			else notes[i].setOctavePitch(noteOctave - octaves);
		}
		return notes;
	}
	
	public static Note[] suffle(Note[] notes, int noNotes){
		Vector<Note> returnNotes = new Vector<Note>();

		for (int i = 0; i < noNotes; i++) {
			int randomIndex = (int)(Math.random()* notes.length);		  
			returnNotes.add(notes[randomIndex]);		
		}
		return returnNotes.toArray(new Note[0]);
	}
	
	public static Note[] suffle(Note[] notes){
						
		List<Note> noteList = Arrays.asList(notes);

		Collections.shuffle(noteList);

		return noteList.toArray(new Note[0]);
	}
	
	/*public static Note[] transpose(Note[] notes, int semitones) throws NoteException{
		if(semitones > 0) {
			int octaves = 0;
			while(semitones >11){
				 semitones-=12; 
				 octaves++;
			 }
			
			for (int i = 0; i < notes.length; i++) {
				Interval interval = IntervalFactory.getInterval(semitones);
				notes[i] = getNote(notes[i], interval);
				int noteOctave = notes[i].getOctavePitch();
				 notes[i].setOctavePitch(noteOctave + octaves);	
			}
		}else{
			int octaves = 0;
			while(semitones <0){
				 semitones+=12; 
				 octaves++;
			 }
			int adjustedSemintones = 12 + semitones;
			for (int i = 0; i < notes.length; i++) {
				Interval interval = IntervalFactory.getInterval(adjustedSemintones);
				int noteOctave = notes[i].getOctavePitch();
				notes[i] = getNote(notes[i], interval);
				notes[i].setOctavePitch(noteOctave - octaves);
			}
		}
		return notes;
	}*/

	public static void main(String args[]) {
		Interval[] intervals = Intervals.getIntervals();
		for(int i=0;i< notes.length;i++){
			for(int j=0;j<intervals.length;j++){
				try{
					System.out.println(notes[i].name + " " + intervals[j].getSymbol() +
							" = " +
							getNote(notes[i], intervals[j]).symbol + "\n");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

	}


}



