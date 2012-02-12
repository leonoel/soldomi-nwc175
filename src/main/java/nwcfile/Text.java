package nwcfile;

public class Text extends Symbol {
  private final NwcItem BYTE3 = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setPosition(reader.readByte());
	return this;
      }
  };

  private final NwcItem BYTE5 = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	reader.skip(1);
	return this;
      }
  };

  private final NwcItem LAST_STRING = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setValue(reader.readString());
	return this;
      }
  };

  private byte m_position;
  private String m_value;

  public void setPosition(byte position) {
    m_position = position;
  }

  public void setValue(String value) {
    m_value = value;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Text *****" + endl);
    builder.append("Value : " + m_value + endl);
    builder.append("***** End text *****" + endl);
    return builder.toString();
  }

  public Text marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Text unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    LAST_STRING.unmarshall(reader);
    return this;
  }
}