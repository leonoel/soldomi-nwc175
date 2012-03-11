package nwcfile;

public class ChordStartingWithRest extends Symbol {
  private Segment m_segment;
  
  public void setSegment(Segment segment) {
    m_segment = segment;
  }

  public ChordStartingWithRest marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public ChordStartingWithRest unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setSegment(new Segment().unmarshall(reader));
    return this;
  }
}