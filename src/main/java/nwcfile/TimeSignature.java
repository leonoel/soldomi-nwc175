package nwcfile;

public class TimeSignature extends SymbolAbstract {

  public enum BeatValue {
    WHOLE,
    HALF,
    QUARTER,
    EIGHTH,
    SIXTEENTH,
    THIRTY_SECOND;
  }

  private byte m_beatCount;
  private BeatValue m_beatValue;

  public void setBeatCount(byte beatCount) {
    m_beatCount = beatCount;
  }

  public void setBeatValue(BeatValue beatValue) {
    m_beatValue = beatValue;
  }
  
  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Repeat *****" + endl);
    builder.append("Byte count : " + m_beatCount + endl);
    builder.append("Byte value : " + m_beatValue + endl);
    builder.append("***** End repeat *****" + endl);
    return builder.toString();
  }

  public TimeSignature marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public TimeSignature unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(2);
    setBeatCount(reader.readByte());
    reader.skip(1);
    try {
      setBeatValue(BeatValue.values()[reader.readByte()]);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new NwcFileException(e);
    }
    reader.skip(3);
    return this;
  }


}