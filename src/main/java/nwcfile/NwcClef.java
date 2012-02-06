package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcClef extends NwcStaffSymbol {

  public enum Key {
    TREBLE,
    BASS,
    ALTO,
    TENOR;
  }

  public enum Octave {
     NONE,
     UP,
     DOWN;
  }

  private Key m_key;
  private Octave m_octave;

  public NwcClef() {
    super(Symbol.CLEF);
  }

  public void setKey(byte key) {
    m_key = Key.values()[key];
  }

  public void setOctave(byte octave) {
    m_octave = Octave.values()[octave];
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Clef *****" + endl);
    builder.append("Key : " + m_key + endl);
    builder.append("Octave : " + m_octave + endl);
    builder.append("***** End clef *****" + endl);
    return builder.toString();
  }

  public static NwcClef fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    NwcClef clef = new NwcClef();
    reader.readByte();
    reader.readByte();
    clef.setKey(reader.readByte());
    reader.readByte();
    clef.setOctave(reader.readByte());
    reader.readByte();
    return clef;
  }
}