package org.soldomi.support.nwc175;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math3.fraction.Fraction;

import org.soldomi.support.nwc175.model.NwcKeySignature;
import org.soldomi.support.nwc175.model.NwcTimeSignature;
import org.soldomi.support.nwc175.model.NwcClef;
import org.soldomi.support.nwc175.model.NwcStaff;
import org.soldomi.support.nwc175.model.NwcSegment;
import org.soldomi.support.nwc175.model.NwcSymbolContainer;
import org.soldomi.support.nwc175.model.NwcFile;

import org.soldomi.model.tune2.Tune;
import org.soldomi.model.tune2.Staff;
import org.soldomi.model.tune2.Block;
import org.soldomi.model.tune2.Sect;
import org.soldomi.model.tune2.Syst;
import org.soldomi.model.tune2.Segment;
import org.soldomi.model.tune2.Symbol;
import org.soldomi.model.tune2.SymbolType;
import org.soldomi.model.tune2.Tuplet;
import org.soldomi.model.tune2.Note;
import org.soldomi.model.tune2.TimeSignature;
import org.soldomi.model.tune2.NoteValue;
import org.soldomi.model.tune2.KeySignature;
import org.soldomi.model.tune2.NotePitch;
import org.soldomi.model.tune2.Accidental;
import org.soldomi.model.tune2.Clef;
import org.soldomi.model.tune2.DurationSymbol;

public class NwcFileImporter {

    private final class StaffImporter {
	public Staff staff;
	private Fraction currentTime;
	private Clef currentClef;
	private Iterator<NwcSymbolContainer> symbolContainerIterator;
	private Tuplet currentTuplet;
	

	public StaffImporter(NwcStaff _nwcStaff, Staff _staff) {
	    staff = _staff;
	    currentTime = Fraction.ZERO;
	    currentClef = Clef.TREBLE;
	    symbolContainerIterator = _nwcStaff.getSymbols().iterator();
	    currentTuplet = null;
	}

	private void addClef(NwcClef nwcClef) {
	    currentClef = toClef(nwcClef);
	    Symbol clef = Symbol.newClef(currentTime, currentClef);
	    staff = staff.addSymbol(clef);
	    currentBlock = currentBlock.addSymbol(clef);
	}

	private void addSegment(NwcSegment nwcSegment, Boolean isRest) {
	    Symbol symbol;
	    DurationSymbol durationSymbol = toDurationSymbol(nwcSegment.getDuration());
	    //	    SymbolType symbolType = isRest ? durationSymbol.restSymbolType : durationSymbol.noteSymbolType;
	    Integer dotCount = toDotCount(nwcSegment.getDots());

	    Fraction duration = new Fraction(durationSymbol.duration(dotCount));
	    NwcSegment.Triplet nwcTriplet = nwcSegment.getTriplet();
	    if (NwcSegment.Triplet.NONE != nwcTriplet) {
		duration = duration.multiply(new Fraction(2, 3));
	    }

	    if (NwcSegment.Triplet.FIRST == nwcTriplet) {
		currentTuplet = new Tuplet();
	    }


	    symbol = Symbol.newSegment(currentTime, durationSymbol, isRest, duration);
	    staff = staff.addSymbol(symbol);
	    currentBlock = currentBlock.addSymbol(symbol);

	    if (!isRest) {
		//Note note = new Note();
		//note.segment.bind(segment);
		//note.pitch.set(currentClef.pitch.addInterval((int) nwcSegment.getRelativePitch()));
	        //note.accidental.set(toAccidental(nwcSegment.getAccidental())); 
	    }

	    /*segment.dotCount.set(dotCount);

	    if (currentTuplet != null) {
		currentTuplet.segments.bind(segment);
	    }

	    if (NwcSegment.Triplet.LAST == nwcTriplet) {
		Fraction currentTupletDuration = Fraction.ZERO;
		for (Segment tupletSegment : currentTuplet.segments.group()) {
		    currentTupletDuration.add(tupletSegment.duration.get());
		}
		currentTuplet.duration.set(currentTupletDuration.longValue());
		currentTuplet = null;
	    }
            */

	    incrementTime(duration);
	}

	private void addTimeSignature(NwcTimeSignature nwcTimeSignature) {
	    /*
	      Symbol symbol = new Symbol();
	    symbol.staff.bind(staff);
	    symbol.block.bind(currentBlock);
	    symbol.startTime.set(currentTime);
	    /symbol.type.set(toSymbolType(nwcTimeSignature.getStyle()));
	    
	    if (SymbolType.STANDARD_TIME_SIGNATURE == symbol.type.get()) {
		TimeSignature timeSignature = new TimeSignature();
		timeSignature.symbol.bind(symbol.timeSignature);
		timeSignature.beatCount.set(nwcTimeSignature.getBeatCount().intValue());
		timeSignature.beatValue.set(toNoteValue(nwcTimeSignature.getBeatValue()));
	    }
            */

	}

	private void addKeySignature(NwcKeySignature nwcKeySignature) {
	    /*
	    Symbol symbol = new Symbol();
	    symbol.staff.bind(staff.symbols);
	    symbol.block.bind(currentBlock.symbols);
	    symbol.startTimeNumerator.set(currentTime.getNumerator());
	    symbol.startTimeDenominator.set(currentTime.getDenominator());
	    symbol.type.set(SymbolType.KEY_SIGNATURE);
	    
	    KeySignature keySignature = new KeySignature();
	    keySignature.symbol.bind(symbol);
	    keySignature.a.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.A));
	    keySignature.b.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.B));
	    keySignature.c.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.C));
	    keySignature.d.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.D));
	    keySignature.e.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.E));
	    keySignature.f.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.F));
	    keySignature.g.set(toNotePitch(nwcKeySignature, NwcKeySignature.Note.G));
	    */
	}

	private void incrementTime(Fraction duration) {
	    currentTime = currentTime.add(duration);
	    if (maxTime.compareTo(currentTime) < 0) {
		maxTime = currentTime;
	    }
	}

	public void stepStaffSymbolsToBar() {
	    boolean bar = false;
	    while(!bar && symbolContainerIterator.hasNext()) {
		NwcSymbolContainer symbolContainer = symbolContainerIterator.next();
		switch(symbolContainer.getType()) {
		case REST: {
		    addSegment((NwcSegment) symbolContainer.getSymbol(), true);
		    break;
		}

		case NOTE: {
		    addSegment((NwcSegment) symbolContainer.getSymbol(), false);
		    break;
		}
		case TIME_SIGNATURE: {
		    addTimeSignature((NwcTimeSignature) symbolContainer.getSymbol());
		    break;
		}
		case KEY_SIGNATURE: {
		    addKeySignature((NwcKeySignature) symbolContainer.getSymbol());
		    break;
		}
		case CLEF: {
		    addClef((NwcClef) symbolContainer.getSymbol());
		    break;
		}
		case BAR_LINE: {
		    bar = true;
		    break;
		}
		default: {
		}
		} // end switch
	    } // end while
		    
	}

    }

    private final NwcFile nwcFile;
    private Tune tune;

    private final List<StaffImporter> staffImporters = new ArrayList<StaffImporter>();

    private Sect currentSect;
    private Block currentBlock;
    private Fraction maxTime;

    private NwcFileImporter(NwcFile _nwcFile) {
	nwcFile = _nwcFile;

	tune = new Tune(nwcFile.getTitle().length() == 0 ? "Untitled" : nwcFile.getTitle());

	currentSect = new Sect(0L);
	currentBlock = new Block(0L);
	maxTime = Fraction.ZERO;

	for(NwcStaff nwcStaff : nwcFile.getStaves()) {
	    Staff staff = new Staff(nwcStaff.getName());
	    staffImporters.add(new StaffImporter(nwcStaff, staff));
	}


	addAllSymbols();
	//	adjustTimeSignatures();
	//	propagateKeySignatures();

	for (StaffImporter staffImporter : staffImporters) {
	    tune = tune.addSyst(new Syst(staffImporter.staff.name).addStaff(staffImporter.staff));
	}

	tune = tune.addSect(currentSect);
    }

    public static Tune run(NwcFile nwcFile) {
	return new NwcFileImporter(nwcFile).tune;
    }

    private StaffImporter minTimeStaff() {
	StaffImporter result = null;
	for (StaffImporter staff : staffImporters) {
	    if (staff.symbolContainerIterator.hasNext() && (result == null || result.currentTime.compareTo(staff.currentTime) > 0)) {
		result = staff;
	    }
	}
	return result;
    }

    private boolean areStavesSynchronized() {
	//	boolean sync = true;
	for (StaffImporter staff : staffImporters) {
	    //	    if (!staff.symbolContainerIterator.hasNext()) {
	    //		staff.currentTime = maxTime;
	    //	    }
	    //	    sync = sync && (staff.currentTime.equals(maxTime));
	    if (staff.symbolContainerIterator.hasNext() && !staff.currentTime.equals(maxTime)) {
		return false;
	    }
	}
	//	return sync;
	return true;
    }

    private boolean isSymbolLeft() {
	for (StaffImporter staffImporter : staffImporters) {
	    if (staffImporter.symbolContainerIterator.hasNext())
		return true;
	}
	return false;
    }

    private void addAllSymbols() {
	while(true) {
	    minTimeStaff().stepStaffSymbolsToBar();
	    if (areStavesSynchronized()) {
		if (isSymbolLeft()) {
		    currentBlock = new Block(maxTime.longValue());
		} else {
		    return;
		}
	    }
	}
    }

    private static SymbolType toSymbolType(NwcTimeSignature.Style style) {
	switch(style) {
	case COMMON_TIME:
	    return SymbolType.COMMON_TIME;
	case ALLA_BREVE:
	    return SymbolType.ALLA_BREVE;
	case STANDARD:
	    return SymbolType.STANDARD_TIME_SIGNATURE;
	default:
	    return null;
	}
    }

    private static Clef toClef(NwcClef clef) {
	switch(clef.getSymbol()) {
	case TREBLE:
	    return Clef.TREBLE;
	case BASS:
	    return Clef.BASS;
	case ALTO:
	    return Clef.ALTO;
	case TENOR:
	    return Clef.TENOR;
	default:
	    return Clef.UNDEFINED;
	}
    }

    private static DurationSymbol toDurationSymbol(NwcSegment.Duration duration) {
	switch(duration) {
	case WHOLE:
	    return DurationSymbol.WHOLE;
	case HALF:
	    return DurationSymbol.HALF;
	case QUARTER:
	    return DurationSymbol.QUARTER;
	case EIGHTH:
	    return DurationSymbol.EIGHTH;
	case SIXTEENTH:
	    return DurationSymbol.SIXTEENTH;
	case THIRTY_SECOND:
	    return DurationSymbol.THIRTY_SECOND;
	case SIXTY_FOURTH:
	    return DurationSymbol.SIXTY_FOURTH;
	default:
	    return DurationSymbol.UNDEFINED;
	}
    }

    private static NoteValue toNoteValue(NwcTimeSignature.BeatValue beatValue) {
	switch(beatValue) {
	case WHOLE:
	    return NoteValue.WHOLE;
	case HALF:
	    return NoteValue.HALF;
	case QUARTER:
	    return NoteValue.QUARTER;
	case EIGHTH:
	    return NoteValue.EIGHTH;
	case SIXTEENTH:
	    return NoteValue.SIXTEENTH;
	case THIRTY_SECOND:
	    return NoteValue.THIRTY_SECOND;
	default:
	    return null;
	}
    }

    private static Integer toDotCount(NwcSegment.Dots nwcDots) {
	switch(nwcDots) {
	case NONE:
	    return 0;
	case SINGLE:
	    return 1;
	case DOUBLE:
	    return 2;
	default:
	    return 0;
	}
    }

    private static Accidental toAccidental(NwcSegment.Accidental nwcAccidental) {
	switch(nwcAccidental) {
	case SHARP:
	    return Accidental.SHARP;
	case FLAT:
	    return Accidental.FLAT;
	case NATURAL:
	    return Accidental.NATURAL;
	case DOUBLE_SHARP:
	    return Accidental.DOUBLE_SHARP;
	case DOUBLE_FLAT:
	    return Accidental.DOUBLE_FLAT;
	case AUTO:
	    return Accidental.AUTO;
	default:
	    return Accidental.AUTO;
	}
    }

    private static NotePitch toNotePitch(NwcKeySignature nwcKeySignature, NwcKeySignature.Note note) {
	return nwcKeySignature.isSharp(note) ? NotePitch.SHARP :
	    nwcKeySignature.isFlat(note) ? NotePitch.FLAT :
	    NotePitch.NATURAL;
    }


}
