package nwcfile;

public class BarLine extends Symbol {
  public enum BarLineType {
    SINGLE,
    DOUBLE,
    SECTION_OPEN,
    SECTION_CLOSE,
    MASTER_REPEAT_OPEN,
    MASTER_REPEAT_CLOSE,
    LOCAL_REPEAT_OPEN,
    LOCAL_REPEAT_CLOSE;
  }

  private final NwcItem BYTE3 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      try {
	setType(BarLineType.values()[reader.readByte()]);
      } catch (ArrayIndexOutOfBoundsException e) {
	throw new NwcFileException(e);
      }
      return this;
    }
  };

  private BarLineType m_type;

  public BarLine() {
    super();
  }

  public void setType(BarLineType type) {
    m_type = type;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Bar line *****" + endl);
    builder.append("Type : " + m_type + endl);
    builder.append("***** End bar line *****" + endl);
    return builder.toString();
  }

  @Override
  public BarLine marshall(NwcFileWriter writer)
    throws NwcFileException {
    // TODO
    return this;
  }

  @Override
  public BarLine unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    return this;
  }
  
}