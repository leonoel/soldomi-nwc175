package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class Clef extends Symbol {

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

  private final NwcItem BYTE3 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      try {
	setKey(Key.values()[reader.readByte()]);
      } catch (ArrayIndexOutOfBoundsException e) {
	throw new NwcFileException(e);
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
      setOctave(reader.readByte());
      return this;
    }
  };

  private final NwcItem BYTE6 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      reader.skip(1);
      return this;
    }
  };


  private Key m_key;
  private Octave m_octave;

  public Clef() {
  }

  public void setKey(Key key) {
    m_key = key;
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
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6.unmarshall(reader);
    return this;
  }
}