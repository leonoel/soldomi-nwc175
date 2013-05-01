package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcChordStartingWithRest extends NwcSymbol {
  private NwcSegment m_segment;
  
  public void setSegment(NwcSegment segment) {
    m_segment = segment;
  }

  public NwcChordStartingWithRest marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcChordStartingWithRest unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setSegment(new NwcSegment().unmarshall(reader));
    return this;
  }
}
