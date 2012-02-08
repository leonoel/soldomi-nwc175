package nwcfile;

public class Repeat extends SymbolAbstract {


  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Repeat *****" + endl);
    builder.append("***** End repeat *****" + endl);
    return builder.toString();
  }

  public Repeat marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Repeat unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(4);
    return this;
  }

}