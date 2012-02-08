package nwcfile;

public class Tempo extends SymbolAbstract {
  public enum Base {
    EIGHTH,
    DOTTED_EIGHTH,
    QUARTER,
    DOTTED_QUARTER,
    HALF,
    DOTTED_HALF;
  }

  private byte m_bpm;
  private Base m_base;
  private String m_text;

  public Tempo() {
  }

  public void setBpm(byte bpm) {
    m_bpm = bpm;
  }

  public void setBase(Base base) {
    m_base = base;
  }

  public void setText(String text) {
    m_text = text;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Tempo *****" + endl);
    builder.append("***** End tempo *****" + endl);
    return builder.toString();
  }

  public Tempo marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Tempo unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(4);
    setBpm(reader.readByte());
    reader.skip(1);
    setBase(Base.values()[reader.readByte()]);
    setText(reader.readString());
    return this;
  }

}