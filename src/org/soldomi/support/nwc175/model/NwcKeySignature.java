package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.NwcUtils;

public class NwcKeySignature extends NwcSymbol {

  public enum Note {
    A,B,C,D,E,F,G;
  }

  private final NwcItem BYTE3 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      int flatBits = reader.readByte();
      for (Note note : Note.values()) {
	setFlat(note, NwcUtils.subByte(flatBits,
				       note.ordinal(),
				       note.ordinal() + 1) != 0);
      }
      return this;
    }
  };

  private final NwcItem BYTE5 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      int sharpBits = reader.readByte();
      for (Note note : Note.values()) {
	setSharp(note, NwcUtils.subByte(sharpBits,
					note.ordinal(),
					note.ordinal() + 1) != 0);
      }
      return this;
    }
  };

  private final NwcItem BYTE6TO12 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      reader.skip(7);
      return this;
    }
  };


  private boolean[] m_flats = new boolean[Note.values().length];
  private boolean[] m_sharps = new boolean[Note.values().length];

  public NwcKeySignature () {
    for (Note note : Note.values()) {
      m_flats[note.ordinal()] = false;
      m_sharps[note.ordinal()] = false;
    }
  }

  public void setFlat(Note note, boolean present) {
    m_flats[note.ordinal()] = present;
  }

  public void setSharp(Note note, boolean present) {
    m_sharps[note.ordinal()] = present;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Key signature *****" + endl);
    builder.append("Flats :");
    for (Note note : Note.values()) {
      if (m_flats[note.ordinal()]) {
	builder.append(" ");
	builder.append(note);
      }
    } 
    builder.append(endl);
    builder.append("Sharps :");
    for (Note note : Note.values()) {
      if (m_sharps[note.ordinal()]) {
	builder.append(" ");
	builder.append(note);
      }
    } 
    builder.append(endl);
    builder.append("***** End key signature *****" + endl);
    return builder.toString();
  }

  public NwcKeySignature marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcKeySignature unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6TO12.unmarshall(reader);
    return this;
  }
  
  /* Adam 2012.12.13: procedures for @NoteWar/app/utils/nwcStaffImporter.java */
  public boolean areSomeFlat() {
    for (Note note : Note.values()) {
      if (m_flats[note.ordinal()]) return true;
    }
    return false;
  }
  public boolean areSomeSharp() {
    for (Note note : Note.values()) {
      if (m_sharps[note.ordinal()]) return true;
    }
    return false;
  }
  public boolean[] getFlats()  {
    return m_flats;
  }
  public boolean[] getSharps() {
    return m_sharps;
  }
  public Boolean isSharp(Note note) {
    return m_sharps[note.ordinal()];
  }
  public Boolean isFlat(Note note) {
    return m_flats[note.ordinal()];
  }
}
