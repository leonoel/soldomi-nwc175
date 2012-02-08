package nwcfile;

public class KeySignature extends SymbolAbstract {

  public enum Note {
    A,B,C,D,E,F,G;
  }

  private boolean[] m_flats = new boolean[Note.values().length];
  private boolean[] m_sharps = new boolean[Note.values().length];

  public KeySignature () {
    super();
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
    builder.append("Flats : " + m_flats + endl);
    builder.append("Sharps : " + m_sharps + endl);
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
    reader.skip(2);
    byte flatBits = reader.readByte();
    reader.skip(1);
    byte sharpBits = reader.readByte();
    reader.skip(7);

    byte mask = 0;
    for (Note note : Note.values()) {
      mask = (byte) (mask << 1);
      setFlat(note, (flatBits & mask) != 0);
      setSharp(note, (sharpBits & mask) != 0);
    }

    return this;
  }

}