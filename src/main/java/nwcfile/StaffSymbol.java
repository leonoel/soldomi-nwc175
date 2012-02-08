package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class StaffSymbol implements NwcItem {
  public enum SymbolEnum implements NwcEnum {
    CLEF(0x00) {
      public SymbolAbstract makeSymbol() {
	return new Clef();
      }
    },
    KEY_SIGNATURE(0x01) {
      public SymbolAbstract makeSymbol() {
	return new KeySignature();
      }
    },
    BAR_LINE(0x02) {
      public SymbolAbstract makeSymbol() {
	return new BarLine();
      }
    },
    REPEAT(0x03) {
      public SymbolAbstract makeSymbol() {
	return new Repeat();
      }
    },
    INSTRUMENT_PATCH(0x04) {
      public SymbolAbstract makeSymbol() {
	return new InstrumentPatch();
      }
    },
    TIME_SIGNATURE(0x05) {
      public SymbolAbstract makeSymbol() {
	return new TimeSignature();
      }
    },
    TEMPO(0x06) {
      public SymbolAbstract makeSymbol() {
	return new Tempo();
      }
    },
    DYNAMICS(0x07) {
      public SymbolAbstract makeSymbol() {
	return new Dynamics();
      }
    },
    NOTE(0x08) {
      public SymbolAbstract makeSymbol() {
	return new Note();
      }
    },
    REST(0x09) {
      public SymbolAbstract makeSymbol() {
	//	return new Rest();
	return null;
      }
    },
    CHORD(0x0A) {
      public SymbolAbstract makeSymbol() {
	//	return new Chord();
	return null;
      }
    },
    PEDAL(0x0B) {
      public SymbolAbstract makeSymbol() {
	//	return new Pedal();
	return null;
      }
    },
    MPC(0x0D) { 
      public SymbolAbstract makeSymbol() {
	//	return new Mpc();
	return null;
      }
    },
    FERMATA(0x0E) {
      public SymbolAbstract makeSymbol() {
	//	return new Fermata();
	return null;
      }
    },
    DYNAMIC_VARIANCE(0x0F) {
      public SymbolAbstract makeSymbol() {
	//	return new DynamicVariance();
	return null;
      }
    },
    PERFORMANCE_STYLE(0x10) {
      public SymbolAbstract makeSymbol() {
	//	return new PerformanceStyle();
	return null;
      }
    },
    TEXT(0x11) {
      public SymbolAbstract makeSymbol() {
	//	return new Text();
	return null;
      }
    },
    CHORD_STARTING_WITH_REST(0x12) {
      public SymbolAbstract makeSymbol() {
	//	return new ChordStartingWithRest();
 	return null;
      }
    };

    private byte m_code;

    SymbolEnum (int code) {
      m_code = (byte) code;
    }

    @Override
    public byte getCode() {
      return m_code;
    }

    public abstract SymbolAbstract makeSymbol();
  }

  private static final NwcEnumReverseMap<SymbolEnum> SYMBOL_REVERSE_MAP = new NwcEnumReverseMap<SymbolEnum>(SymbolEnum.class);

  private SymbolEnum m_type;
  private SymbolAbstract m_item;

  public StaffSymbol() {
    super();
  }

  public void setType(SymbolEnum type) {
    m_type = type;
  }

  public void setItem(SymbolAbstract item) {
    m_item = item;
  }

  public StaffSymbol marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public StaffSymbol unmarshall(NwcFileReader reader)
    throws NwcFileException {
    byte typeByte = reader.readByte();
    if (!SYMBOL_REVERSE_MAP.contains(typeByte)) {
      throw new NwcFileException("Unrecognized symbol");
    }
    SymbolEnum type = SYMBOL_REVERSE_MAP.get(typeByte);
    setType(type);
    setItem(type.makeSymbol().unmarshall(reader));
    return this;
  }

}