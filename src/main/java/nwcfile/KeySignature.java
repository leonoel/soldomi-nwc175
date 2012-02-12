package nwcfile;

public class KeySignature extends Symbol {

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
      byte flatBits = reader.readByte();
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
      byte sharpBits = reader.readByte();
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

  public KeySignature () {
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

  public KeySignature marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public KeySignature unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6TO12.unmarshall(reader);
    return this;
  }

}