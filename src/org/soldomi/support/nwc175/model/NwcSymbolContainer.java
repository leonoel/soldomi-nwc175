package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.NwcEnum;
import org.soldomi.support.nwc175.NwcEnumReverseMap;

import java.util.HashMap;
import java.util.Map;

public class NwcSymbolContainer implements NwcItem {
  public enum SymbolType implements NwcEnum {
    CLEF(0x00) {
      public NwcSymbol makeSymbol() {
	return new NwcClef();
      }
    },
    KEY_SIGNATURE(0x01) {
      public NwcSymbol makeSymbol() {
	return new NwcKeySignature();
      }
    },
    BAR_LINE(0x02) {
      public NwcSymbol makeSymbol() {
	return new NwcBarLine();
      }
    },
    REPEAT(0x03) {
      public NwcSymbol makeSymbol() {
	return new NwcRepeat();
      }
    },
    INSTRUMENT_PATCH(0x04) {
      public NwcSymbol makeSymbol() {
	return new NwcInstrumentPatch();
      }
    },
    TIME_SIGNATURE(0x05) {
      public NwcSymbol makeSymbol() {
	return new NwcTimeSignature();
      }
    },
    TEMPO(0x06) {
      public NwcSymbol makeSymbol() {
	return new NwcTempo();
      }
    },
    DYNAMICS(0x07) {
      public NwcSymbol makeSymbol() {
	return new NwcDynamics();
      }
    },
    NOTE(0x08) {
      public NwcSymbol makeSymbol() {
	return new NwcSegment();
      }
    },
    REST(0x09) {
      public NwcSymbol makeSymbol() {
	return new NwcSegment();
      }
    },
    CHORD(0x0A) {
      public NwcSymbol makeSymbol() {
	return new NwcChord();
      }
    },
    PEDAL(0x0B) {
      public NwcSymbol makeSymbol() {
	return new NwcPedal();
      }
    },
    FLOW_DIRECTION(0x0C) {
      public NwcSymbol makeSymbol() {
	  return new NwcFlowDirection();
      }
    },
    MPC(0x0D) { 
      public NwcSymbol makeSymbol() {
	return new NwcMpc();
      }
    },
    TEMPO_VARIANCE(0x0E) {
      public NwcSymbol makeSymbol() {
	return new NwcTempoVariance();
      }
    },
    DYNAMIC_VARIANCE(0x0F) {
      public NwcSymbol makeSymbol() {
	return new NwcDynamicVariance();
      }
    },
    PERFORMANCE_STYLE(0x10) {
      public NwcSymbol makeSymbol() {
	return new NwcPerformanceStyle();
      }
    },
    TEXT(0x11) {
      public NwcSymbol makeSymbol() {
	return new NwcText();
      }
    },
    CHORD_STARTING_WITH_REST(0x12) {
      public NwcSymbol makeSymbol() {
	return new NwcChordStartingWithRest();
      }
    };

    private int m_code;

    SymbolType (int code) {
      m_code = code;
    }

    @Override
    public int getCode() {
      return m_code;
    }

    public abstract NwcSymbol makeSymbol();
  }

    private static final NwcEnumReverseMap<SymbolType> SYMBOL_REVERSE_MAP = new NwcEnumReverseMap<SymbolType>(SymbolType.class);

    private SymbolType m_type;
    private NwcSymbol m_symbol;

    public NwcSymbolContainer() {
	super();
    }

    public SymbolType getType() {
	return m_type;
    }

    public void setType(SymbolType type) {
	m_type = type;
    }

    public NwcSymbol getSymbol() {
	return m_symbol;
    }

    public void setSymbol(NwcSymbol symbol) {
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

    public NwcSymbolContainer marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
    }

    public NwcSymbolContainer unmarshall(NwcFileReader reader)
	throws NwcFileException {
	int typeByte = reader.readUnsignedByte();
	if (!SYMBOL_REVERSE_MAP.contains(typeByte)) {
	    throw new NwcFileException("Unrecognized symbol : " + typeByte);
	}
	SymbolType type = SYMBOL_REVERSE_MAP.get(typeByte);
	setType(type);
	setSymbol(type.makeSymbol().unmarshall(reader));
	return this;
    }

}
