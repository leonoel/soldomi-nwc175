package nwcfile;

public class TimeSignature extends Symbol {

  public enum BeatValue {
    WHOLE,
    HALF,
    QUARTER,
    EIGHTH,
    SIXTEENTH,
    THIRTY_SECOND;
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
      reader.skip(3);
      return this;
    }
  };

  private byte m_beatCount;
  private BeatValue m_beatValue;

  public byte getBeatCount() {
    return m_beatCount;
  }

  public void setBeatCount(byte beatCount) {
    m_beatCount = beatCount;
  }

  public BeatValue getBeatValue() {
    return m_beatValue;
  }

  public void setBeatValue(BeatValue beatValue) {
    m_beatValue = beatValue;
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

  public TimeSignature marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public TimeSignature unmarshall(NwcFileReader reader)
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