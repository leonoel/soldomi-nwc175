package nwcfile;

import java.io.InputStream;

public class NwcFont {
  private String m_family;
  private int m_style;

  private NwcFont(String family,
		  int style) {
    super();
    m_family = family;
    m_style = style;
  }

  public String getFamily() {
    return m_family;
  }

  public int getStyle() {
    return m_style;
  }

  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("Family : " + m_family + endl);
    builder.append("Style : " + m_style + endl);
    return builder.toString();
  }

  public static NwcFont fromNwcFileReader(NwcFileReader reader) throws NwcFileException {
    String family = reader.getNextField();
    int style = reader.getNextField().getBytes()[0];
    return new NwcFont(family, style);
  }
}