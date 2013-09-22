package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.NwcUtils;

public class NwcSegment extends NwcSymbol {

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

  public enum Stem {
    NEUTRAL,
    UP,
    DOWN;
  }

  public enum Beam {
    OUTSIDE,
    BEGIN,
    INSIDE,
    END;
  }

  public enum Triplet {
    NONE,
    FIRST,
    INNER,
    LAST;
  }

  private final NwcItem BYTE3 = new NwcItem() {
      private static final int DURATION_START = 0;
      private static final int DURATION_END = 3;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setDuration(Duration.values()[NwcUtils.subByte(reader.readByte(),
						       DURATION_START,
						       DURATION_END)]);
	return this;
      }
  };

  private final NwcItem BYTE5 = new NwcItem() {
      private static final int BEAM_START = 0;
      private static final int BEAM_END = 2;
      private static final int TRIPLET_START = 2;
      private static final int TRIPLET_END = 4;
      private static final int STEM_START = 4;
      private static final int STEM_END = 6;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	int b = reader.readByte();
	setStem(Stem.values()[NwcUtils.subByte(b,
					       STEM_START,
					       STEM_END)]);
	setBeam(Beam.values()[NwcUtils.subByte(b,
					       BEAM_START,
					       BEAM_END)]);
	setTriplet(Triplet.values()[NwcUtils.subByte(b,
						     TRIPLET_START,
						     TRIPLET_END)]);

	return this;
      }
  };


  private final NwcItem BYTE6 = new NwcItem() {

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	reader.skip(1);
	return this;
      }
  };

  private final NwcItem BYTE7 = new NwcItem() {
      private static final int TIE_START = 4;
      private static final int TIE_END = 5;
      private static final int STACCATO_START = 1;
      private static final int STACCATO_END = 2;
      private static final int ACCENT_START = 5;
      private static final int ACCENT_END = 6;
      private static final int SINGLE_DOT_START = 0;
      private static final int SINGLE_DOT_END = 1;
      private static final int DOUBLE_DOT_START = 2;
      private static final int DOUBLE_DOT_END = 3;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	int b = reader.readByte();
	Dots dots;
	if (NwcUtils.subByte(b,
			     SINGLE_DOT_START,
			     SINGLE_DOT_END) != 0) {
	  dots = Dots.DOUBLE;
	} else if (NwcUtils.subByte(b,
				    DOUBLE_DOT_START,
				    DOUBLE_DOT_END) != 0) {
	  dots = Dots.SINGLE;
	} else {
	  dots = Dots.NONE;
	}
	setDots(dots);
	setTie(NwcUtils.subByte(b,
				TIE_START,
				TIE_END) != 0);
	setStaccato(NwcUtils.subByte(b,
				     STACCATO_START,
				     STACCATO_END) != 0);
	setAccent(NwcUtils.subByte(b,
				   ACCENT_START,
				   ACCENT_END) != 0);

	return this;
      }
  };

  private final NwcItem BYTE8 = new NwcItem() {
      private static final int TENUTO_START = 2;
      private static final int TENUTO_END = 3;
      private static final int GRACE_START = 5;
      private static final int GRACE_END = 6;
      private static final int SLUR_START = 0;
      private static final int SLUR_END = 2;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	int b = reader.readByte();
	setTenuto(NwcUtils.subByte(b,
			       TENUTO_START,
			       TENUTO_END) != 0);
	setGrace(NwcUtils.subByte(b,
				  GRACE_START,
				  GRACE_END) != 0);
	setSlur(NwcUtils.subByte(b,
				 SLUR_START,
				 SLUR_END));
	return this;
      }
  };

  private final NwcItem BYTE9 = new NwcItem() {

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setRelativePitch(-reader.readByte());
	return this;
      }
  };

  private final NwcItem BYTE10 = new NwcItem() {
      private static final int ACCIDENTAL_START = 0;
      private static final int ACCIDENTAL_END = 3;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	int b = reader.readByte();
	int accidental = NwcUtils.subByte(b,
					  ACCIDENTAL_START,
					  ACCIDENTAL_END);
	try {
	  setAccidental(Accidental.values()[accidental]);
	} catch (ArrayIndexOutOfBoundsException e) {
	  throw new NwcFileException(e);
	}
	return this;
      }
  };



  private Duration m_duration;
  private Dots m_dots;

  private boolean m_tie;
  private boolean m_staccato;
  private boolean m_accent;
  private boolean m_tenuto;
  private boolean m_grace;
  private int m_slur;

  private int m_relativePitch;
  private Accidental m_accidental;

  private Beam m_beam;
  private Stem m_stem;
  private Triplet m_triplet;
  
  public NwcSegment() {
  }

  public Duration getDuration() {
    return m_duration;
  }

  public void setDuration(Duration duration) {
    m_duration = duration;
  }

  public void setDots(Dots dots) {
    m_dots = dots;
  }

  public Dots getDots() {
    return m_dots;
  }

  public void setTie(boolean tie) {
    m_tie = tie;
  }

  public void setStaccato(boolean staccato) {
    m_staccato = staccato;
  }

  public void setAccent(boolean accent) {
    m_accent = accent;
  }

  public void setTenuto(boolean tenuto) {
    m_tenuto = tenuto;
  }

  public void setGrace(boolean grace) {
    m_grace = grace;
  }

  public void setSlur(int slur) {
    m_slur = slur;
  }

  public void setRelativePitch(int relativePitch) {
    m_relativePitch = relativePitch;
  }

  public int getRelativePitch() {
    return m_relativePitch;
  }

  public void setAccidental(Accidental accidental) {
    m_accidental = accidental;
  }

  public Accidental getAccidental() {
    return m_accidental;
  }

  public void setBeam(Beam beam) {
    m_beam = beam;
  }

  public Beam getBeam() {
    return m_beam;
  }

  public void setStem(Stem stem) {
    m_stem = stem;
  }

  public Stem getStem() {
    return m_stem;
  }

  public void setTriplet(Triplet triplet) {
    m_triplet = triplet;
  }

  public boolean isTriplet() {
    return (m_triplet != Triplet.NONE);
  }

  public Triplet getTriplet() {
    return m_triplet;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Note *****" + endl);
    builder.append("Duration : " + m_duration + endl);
    builder.append("Dots : " + m_dots + endl);
    builder.append("Tie : " + m_tie + endl);
    builder.append("Staccato : " + m_staccato + endl);
    builder.append("Accent : " + m_accent + endl);
    builder.append("Tenuto : " + m_tenuto + endl);
    builder.append("Grace : " + m_grace + endl);
    builder.append("Slur : " + m_slur + endl);
    builder.append("Relative Pitch : " + m_relativePitch + endl);
    builder.append("Accidental : " + m_accidental + endl);
    builder.append("Beam : " + m_beam + endl);
    builder.append("Stem : " + m_stem + endl);
    builder.append("Triplet : " + m_triplet + endl);
    builder.append("***** End note *****" + endl);
    return builder.toString();
  }

  public NwcSegment marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcSegment unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6.unmarshall(reader);
    BYTE7.unmarshall(reader);
    BYTE8.unmarshall(reader);
    BYTE9.unmarshall(reader);
    BYTE10.unmarshall(reader);

    return this;
  }

}
