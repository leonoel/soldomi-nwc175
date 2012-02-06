package nwcfile;

public class NwcKeySignature extends NwcStaffSymbol{

  public enum Note {
    A(0x01),
    B(0x02),
    C(0x04),
    D(0x08),
    E(0x10),
    F(0x20),
    G(0x40);

    private byte m_value;

    Note(int value) {
      m_value = (byte) value;
    }
  }

  private boolean[] m_flats = new boolean[Note.values().length];
  private boolean[] m_sharps = new boolean[Note.values().length];

  public NwcKeySignature () {
    super(Symbol.KEY_SIGNATURE);
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

  public static NwcKeySignature fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    NwcKeySignature keySignature = new NwcKeySignature();
    reader.skip(2);
    byte flatBits = reader.readByte();
    reader.skip(1);
    byte sharpBits = reader.readByte();
    reader.skip(7);

    byte mask = 0;
    for (Note note : Note.values()) {
      mask = (byte) (mask << 1);
      keySignature.setFlat(note, (flatBits & mask) != 0);
      keySignature.setSharp(note, (sharpBits & mask) != 0);
    }

    return keySignature;
  }

}