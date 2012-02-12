package nwcfile;

public class Dynamics extends Symbol {
  public enum DynamicsType {
    PIANISSIMO_POSSIBILE,
    PIANISSIMO,
    PIANO,
    MEZZO_PIANO,
    MEZZO_FORTE,
    FORTE,
    FORTISSIMO,
    FORTISSIMO_POSSIBILE;
  }

  private final NwcItem BYTE3 = new NwcItem() {
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

  private final NwcItem BYTE5 = new NwcItem() {
    private static final int TYPE_START = 0;
    private static final int TYPE_END = 3;

    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      setDynamicsType(DynamicsType.values()[NwcUtils.subByte(reader.readByte(),
							     TYPE_START,
							     TYPE_END)]);
      return this;
    }
  };

  private final NwcItem BYTE6TO9 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      reader.skip(4);
      return this;
    }
  };

  private DynamicsType m_dynamicsType;

  public Dynamics() {
  }

  public void setDynamicsType(DynamicsType dynamicsType) {
    m_dynamicsType = dynamicsType;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Dynamics *****" + endl);
    builder.append("Value : " + m_dynamicsType + endl);
    builder.append("***** End dynamics *****" + endl);
    return builder.toString();
  }

  public Dynamics marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Dynamics unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6TO9.unmarshall(reader);
    return this;
  }

}