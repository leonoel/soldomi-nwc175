package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

import java.util.HashMap;
import java.util.Map;

public class NwcClef extends NwcSymbol {

  public enum Symbol {
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
	setSymbol(Symbol.values()[reader.readByte()]);
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
      setOctave(Octave.values()[reader.readByte()]);
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


  private Symbol m_symbol;
  private Octave m_octave;

  public NwcClef() {
  }

  public Symbol getSymbol() {
    return m_symbol;
  }

  public Octave getOctave() {
    return m_octave;
  }

  public void setSymbol(Symbol symbol) {
    m_symbol = symbol;
  }

  public void setOctave(Octave octave) {
    m_octave = octave;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Clef *****" + endl);
    builder.append("Symbol : " + m_symbol + endl);
    builder.append("Octave : " + m_octave + endl);
    builder.append("***** End clef *****" + endl);
    return builder.toString();
  }

  public NwcClef marshall(NwcFileWriter writer)
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcClef unmarshall(NwcFileReader reader)
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
