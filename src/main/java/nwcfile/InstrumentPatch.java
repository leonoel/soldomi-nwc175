package nwcfile;

public class InstrumentPatch extends Symbol {

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

  private final NwcItem BYTE5TO10 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      reader.skip(6);
      return this;
    }
  };

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Instrument patch *****" + endl);
    builder.append("***** End instrument patch *****" + endl);
    return builder.toString();
  }

  public InstrumentPatch marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public InstrumentPatch unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5TO10.unmarshall(reader);
    return this;
  }

}