package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcMpc extends NwcSymbol {

  public NwcMpc marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcMpc unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(36);
    return this;
  }


}
