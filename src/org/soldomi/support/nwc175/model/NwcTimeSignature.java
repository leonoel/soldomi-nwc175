package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcTimeSignature extends NwcSymbol {

  public enum BeatValue {
    WHOLE,
    HALF,
    QUARTER,
    EIGHTH,
    SIXTEENTH,
    THIRTY_SECOND;
  }

  public enum Style {
    STANDARD,
    COMMON_TIME,
    ALLA_BREVE;
  }

  private final NwcItem BYTE3 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      setBeatCount(reader.readByte());
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
      try {
	setBeatValue(BeatValue.values()[reader.readByte()]);
      } catch (ArrayIndexOutOfBoundsException e) {
	throw new NwcFileException(e);
      }
      return this;
    }
  };

  private final NwcItem BYTE6TO8 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
	reader.skip(1);
	try {
	    setStyle(Style.values()[reader.readByte()]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new NwcFileException(e);
	}
	reader.skip(1);
      return this;
    }
  };

  private int m_beatCount;
  private BeatValue m_beatValue;
  private Style m_style;

  public int getBeatCount() {
    return m_beatCount;
  }

  public void setBeatCount(int beatCount) {
    m_beatCount = beatCount;
  }

  public BeatValue getBeatValue() {
    return m_beatValue;
  }

  public void setBeatValue(BeatValue beatValue) {
    m_beatValue = beatValue;
  }

  public Style getStyle() {
    return m_style;
  }

  public void setStyle(Style style) {
    m_style = style;
  }
  
  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Repeat *****" + endl);
    builder.append("Byte count : " + m_beatCount + endl);
    builder.append("Byte value : " + m_beatValue + endl);
    builder.append("***** End repeat *****" + endl);
    return builder.toString();
  }

  public NwcTimeSignature marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcTimeSignature unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6TO8.unmarshall(reader);
    return this;
  }


}
