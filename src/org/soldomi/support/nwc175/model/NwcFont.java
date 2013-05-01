package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcFont implements NwcItem {
  private String m_family;
  private int m_style;

  public NwcFont() {
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

  public NwcFont marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcFont unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setFamily(reader.readString());
    setStyle(reader.readShort());
    reader.skip(2);
    return this;
  }
}
