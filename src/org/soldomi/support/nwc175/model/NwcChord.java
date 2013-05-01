package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

import java.util.List;
import java.util.ArrayList;

public class NwcChord extends NwcSymbol {

  private NwcSegment m_segment;
  private List<NwcSegment> m_segments = new ArrayList<NwcSegment>();

  public void setSegment(NwcSegment segment) {
    m_segment = segment;
  }

  public void addSegment(NwcSegment segment) {
    m_segments.add(segment);
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Chord *****" + endl);
    builder.append("***** Base note" + endl);
    builder.append(m_segment);
    builder.append("***** End base note" + endl);
    builder.append("***** Notes *****" + endl);
    for (NwcSegment segment : m_segments) {
      builder.append(segment.toString());
    }
    builder.append("***** End notes *****" + endl);
    builder.append("***** End chord *****" + endl);
    return builder.toString();
  }

  @Override
  public NwcChord marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  @Override
  public NwcChord unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setSegment(new NwcSegment().unmarshall(reader));
    byte noteCount = reader.readByte();
    reader.skip(1);
    for (int i = 0; i < noteCount; i++) {
      byte crap = reader.readByte();
      addSegment(new NwcSegment().unmarshall(reader));
    }
    return this;
  }

}
