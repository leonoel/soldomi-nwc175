package nwcfile;

public class Font implements NwcItem {
  private String m_family;
  private int m_style;

  public Font() {
  }

  public void setFamily(String family) {
    m_family = family;
  }

  public void setStyle(int style) {
    m_style = style;
  }

  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Font *****" + endl);
    builder.append("Family : " + m_family + endl);
    builder.append("Style : " + m_style + endl);
    builder.append("***** End fonts *****" + endl);
    return builder.toString();
  }

  public Font marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Font unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setFamily(reader.readString());
    setStyle(reader.readShort());
    reader.skip(2);
    return this;
  }
}