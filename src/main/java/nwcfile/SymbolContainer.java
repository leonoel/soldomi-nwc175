package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class SymbolContainer implements NwcItem {
  public enum SymbolType implements NwcEnum {
    CLEF(0x00) {
      public Symbol makeSymbol() {
	return new Clef();
      }
    },
    KEY_SIGNATURE(0x01) {
      public Symbol makeSymbol() {
	return new KeySignature();
      }
    },
    BAR_LINE(0x02) {
      public Symbol makeSymbol() {
	return new BarLine();
      }
    },
    REPEAT(0x03) {
      public Symbol makeSymbol() {
	return new Repeat();
      }
    },
    INSTRUMENT_PATCH(0x04) {
      public Symbol makeSymbol() {
	return new InstrumentPatch();
      }
    },
    TIME_SIGNATURE(0x05) {
      public Symbol makeSymbol() {
	return new TimeSignature();
      }
    },
    TEMPO(0x06) {
      public Symbol makeSymbol() {
	return new Tempo();
      }
    },
    DYNAMICS(0x07) {
      public Symbol makeSymbol() {
	return new Dynamics();
      }
    },
    NOTE(0x08) {
      public Symbol makeSymbol() {
	return new Segment();
      }
    },
    REST(0x09) {
      public Symbol makeSymbol() {
	return new Segment();
      }
    },
    CHORD(0x0A) {
      public Symbol makeSymbol() {
	return new Chord();
      }
    },
    PEDAL(0x0B) {
      public Symbol makeSymbol() {
	return new Pedal();
      }
    },
    MPC(0x0D) { 
      public Symbol makeSymbol() {
	return new Mpc();
      }
    },
    TEMPO_VARIANCE(0x0E) {
      public Symbol makeSymbol() {
	return new TempoVariance();
      }
    },
    DYNAMIC_VARIANCE(0x0F) {
      public Symbol makeSymbol() {
	return new DynamicVariance();
      }
    },
    PERFORMANCE_STYLE(0x10) {
      public Symbol makeSymbol() {
	return new PerformanceStyle();
      }
    },
    TEXT(0x11) {
      public Symbol makeSymbol() {
	return new Text();
      }
    },
    CHORD_STARTING_WITH_REST(0x12) {
      public Symbol makeSymbol() {
	return new ChordStartingWithRest();
      }
    };

    private byte m_code;

    SymbolType (int code) {
      m_code = (byte) code;
    }

    @Override
    public byte getCode() {
      return m_code;
    }

    public abstract Symbol makeSymbol();
  }

  private static final NwcEnumReverseMap<SymbolType> SYMBOL_REVERSE_MAP = new NwcEnumReverseMap<SymbolType>(SymbolType.class);

  private SymbolType m_type;
  private Symbol m_symbol;

  public SymbolContainer() {
    super();
  }

  public SymbolType getType() {
    return m_type;
  }

  public void setType(SymbolType type) {
    m_type = type;
  }

  public Symbol getSymbol() {
    return m_symbol;
  }

  public void setSymbol(Symbol symbol) {
    m_symbol = symbol;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Symbol *****" + endl);
    builder.append("Type : " + m_type + endl);
    builder.append(m_symbol.toString());
    builder.append("***** End Symbol *****" + endl);
    return builder.toString();
  }

  public SymbolContainer marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public SymbolContainer unmarshall(NwcFileReader reader)
    throws NwcFileException {
    byte typeByte = reader.readByte();
    if (!SYMBOL_REVERSE_MAP.contains(typeByte)) {
      throw new NwcFileException("Unrecognized symbol");
    }
    SymbolType type = SYMBOL_REVERSE_MAP.get(typeByte);
    setType(type);
    setSymbol(type.makeSymbol().unmarshall(reader));
    return this;
  }

}