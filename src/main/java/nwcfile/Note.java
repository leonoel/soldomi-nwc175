package nwcfile;

public class Note extends SymbolAbstract {

  public enum Duration {
    WHOLE,
    HALF,
    QUARTER,
    EIGHTH,
    SIXTEENTH,
    THIRTY_SECOND,
    SIXTY_FOURTH;  
  }

  public enum Dots {
    NONE,
    SINGLE,
    DOUBLE;
  }

  public enum Accidental {
    SHARP,
    FLAT,
    NATURAL,
    DOUBLE_SHARP,
    DOUBLE_FLAT,
    AUTO;
  }

  private static final int DURATION_MASK = Integer.parseInt("111", 2);
  private static final int SINGLE_DOT_MASK = Integer.parseInt("1", 2);
  private static final int DOUBLE_DOT_MASK = Integer.parseInt("100", 2);
  private static final int ACCIDENTAL_MASK = Integer.parseInt("111", 2);
  private static final int STEM_MASK = Integer.parseInt("110000", 2);
  private static final int BEAM_MASK = Integer.parseInt("11", 2);
  private static final int TRIPLET_MASK = Integer.parseInt("1100", 2);

  private Duration m_duration;
  private Dots m_dots;
  private byte m_pitch;
  private Accidental m_accidental;

  private byte m_beam;
  private byte m_stem;
  private byte m_triplet;
  
  public Note() {
  }

  public void setDuration(Duration duration) {
    m_duration = duration;
  }

  public void setDots(Dots dots) {
    m_dots = dots;
  }

  public void setPitch(byte pitch) {
    m_pitch = pitch;
  }

  public void setAccidental(Accidental accidental) {
    m_accidental = accidental;
  }

  public void setBeam(byte beam) {
    m_beam = beam;
  }

  public void setStem(byte stem) {
    m_stem = stem;
  }

  public void setTriplet(byte triplet) {
    m_triplet = triplet;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Note *****" + endl);
    builder.append("***** End note *****" + endl);
    return builder.toString();
  }

  public Note marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Note unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(2);
    byte durationByte = reader.readByte();
    reader.skip(1);
    byte decorationByte = reader.readByte();
    reader.skip(1);
    byte dotsByte = reader.readByte();
    reader.skip(1);
    byte pitchByte = reader.readByte();
    byte accidentalByte = reader.readByte();

    setDuration(Duration.values()[durationByte & DURATION_MASK]);

    setStem((byte) ((decorationByte & STEM_MASK) >> 4));
    setBeam((byte) ((decorationByte & BEAM_MASK) >> 2));
    setTriplet((byte) (decorationByte & TRIPLET_MASK));

    Dots dots;
    if ((dotsByte & SINGLE_DOT_MASK) != 0) {
      dots = Dots.SINGLE;
    } else if ((dotsByte & DOUBLE_DOT_MASK) != 0) {
      dots = Dots.DOUBLE;
    } else {
      dots = Dots.NONE;
    }
    setDots(dots);
    setPitch(pitchByte);
    setAccidental(Accidental.values()[accidentalByte & ACCIDENTAL_MASK]);
    return this;
  }

}