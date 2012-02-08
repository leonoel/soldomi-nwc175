package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class Clef extends SymbolAbstract {

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

  public Clef() {
    super();
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

  public Clef marshall(NwcFileWriter writer)
    throws NwcFileException {
    // TODO
    return this;
  }

  public Clef unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.readByte();
    reader.readByte();
    setKey(reader.readByte());
    reader.readByte();
    setOctave(reader.readByte());
    reader.readByte();
    return this;
  }
}