package nwcfile;

public class InstrumentPatch extends SymbolAbstract {



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
    reader.skip(10);
    return this;
  }

}