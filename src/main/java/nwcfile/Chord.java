package nwcfile;

import java.util.List;
import java.util.ArrayList;

public class Chord extends Symbol {

  private Segment m_segment;
  private List<Segment> m_segments = new ArrayList<Segment>();

  public void setSegment(Segment segment) {
    m_segment = segment;
  }

  public void addSegment(Segment segment) {
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
    for (Segment segment : m_segments) {
      builder.append(segment.toString());
    }
    builder.append("***** End notes *****" + endl);
    builder.append("***** End chord *****" + endl);
    return builder.toString();
  }

  @Override
  public Chord marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  @Override
  public Chord unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setSegment(new Segment().unmarshall(reader));
    byte noteCount = reader.readByte();
    reader.skip(1);
    for (int i = 0; i < noteCount; i++) {
      byte crap = reader.readByte();
      addSegment(new Segment().unmarshall(reader));
    }
    return this;
  }

}