package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.NwcUtils;

import java.util.List;
import java.util.ArrayList;

public class NwcStaff implements NwcItem {
    public enum EndBar {
	SECTION_CLOSE,
	MASTER_REPEAT_CLOSE,
	SINGLE,
	DOUBLE,
	OPEN_HIDDEN;
    }

    public enum StaffType {
	STANDARD,
	UPPER_GRAND_STAFF,
	LOWER_GRAND_STAFF,
	ORCHESTRA;
    }

    private String m_name;
    private String m_group;
    private EndBar m_endBar;
    private boolean m_muted;
    private int m_channel;
    private StaffType m_staffType;
    private int m_upperSize;
    private int m_lowerSize;
    private int m_lineCount;
    private int m_layer;
    private int m_partVolume;
    private int m_stereoPan;
    private int m_lyrics;
    private int m_lyricsCount;
    private int m_color;
    private int m_symbolCount;
    private List<NwcSymbolContainer> m_symbols = new ArrayList<NwcSymbolContainer>();

    public NwcStaff() {
    }

    public String getName() {
	return m_name;
    }

    public void setName(String name) {
	m_name = name;
    }

    public List<NwcSymbolContainer> getSymbols() {
	return m_symbols;
    }

    public void setGroup(String group) {
	m_group = group;
    }

    public void setEndBar(EndBar endBar) {
	m_endBar = endBar;
    }

    public void setMuted(boolean muted) {
	m_muted = muted;
    }

    public void setChannel(int channel) {
	m_channel = channel;
    }
  
    public void setStaffType(StaffType staffType) {
	m_staffType = staffType;
    }

    public void setUpperSize(int upperSize) {
	m_upperSize = upperSize;
    }

    public void setLowerSize(int lowerSize) {
	m_lowerSize = lowerSize;
    }

    public void setLineCount(int lineCount) {
	m_lineCount = lineCount;
    }

    public void setLayer(int layer) {
	m_layer = layer;
    }

    public void setPartVolume(int partVolume) {
	m_partVolume = partVolume;
    }

    public void setStereoPan(int stereoPan) {
	m_stereoPan = stereoPan;
    }

    public void setColor(int color) {
	m_color = color;
    }

    public void setSymbolCount(int symbolCount) {
	m_symbolCount = symbolCount;
    }

    public void addSymbol(NwcSymbolContainer symbol) {
	m_symbols.add(symbol);
    }

    public String toString() {
	String endl = System.getProperty("line.separator");
	StringBuilder builder = new StringBuilder();
	builder.append("***** Staff *****" + endl);
	builder.append("Name : " + m_name + endl);
	builder.append("Group : " + m_group + endl);
	builder.append("End bar : " + m_endBar + endl);
	builder.append("Muted : " + m_muted + endl);
	builder.append("Channel : " + m_channel + endl);
	builder.append("Staff type : " + m_staffType + endl);
	builder.append("Upper size : " + m_upperSize + endl);
	builder.append("Lower size : " + m_lowerSize + endl);
	builder.append("Line count : " + m_lineCount + endl);
	builder.append("Layer : " + m_layer + endl);
	builder.append("Part volume : " + m_partVolume + endl);
	builder.append("Stereo pan : " + m_stereoPan + endl);
	builder.append("Lyrics count : " + m_lyricsCount + endl);
	builder.append("Color : " + m_color + endl);
	builder.append("Symbol count : " + m_symbolCount + endl);

	builder.append("***** Symbols *****" + endl);
	for (NwcSymbolContainer symbol : m_symbols) {
	    builder.append(symbol.toString());
	}
	builder.append("***** End Symbols *****" + endl);
	builder.append("***** End Staff *****" + endl);

	return builder.toString();
    }

    public NwcStaff marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
    }

    public NwcStaff unmarshall(NwcFileReader reader)
	throws NwcFileException {
	reader.log("NwcStaff : unmarshall started");

	setName(reader.readString());
	setGroup(reader.readString());

	try {
	    setEndBar(EndBar.values()[NwcUtils.subByte(reader.readByte(), 0, 3)]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new NwcFileException(e);
	}

	setMuted(NwcUtils.subByte(reader.readByte(), 0, 1) != 0);

	reader.skip(1);
	setChannel(reader.readByte());
	reader.skip(9);

	try {
	    setStaffType(StaffType.values()[NwcUtils.subByte(reader.readByte(), 0, 2)]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new NwcFileException(e);
	}

	reader.skip(1);
	setUpperSize(reader.readByte());
	reader.skip(1);
	setLowerSize(reader.readByte());
	reader.skip(1);
	setLineCount(reader.readByte());
	reader.skip(4);
	setPartVolume(reader.readByte());
	reader.skip(1);
	setStereoPan(reader.readByte());
	reader.skip(8);

	int symbolCount = reader.readUnsignedShort();
	setSymbolCount(symbolCount);

	for (int i = 1; i < symbolCount - 1; i++) {
	    reader.log("NwcStaff : read symbol");
	    addSymbol(new NwcSymbolContainer().unmarshall(reader));
	}

	reader.log("NwcStaff : unmarshall finished (" + (symbolCount - 2) + " symbols read)");
	return this;
    }

}
