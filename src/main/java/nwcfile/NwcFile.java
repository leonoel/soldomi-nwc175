package nwcfile;

import java.io.*;
import java.util.zip.*;

public class NwcFile {
    private static final String HEADER_NWZ = "[NWZ]";

    private NwcFile() {

    }

    private static NwcFile decode(byte[] bytes) {
	String header = new String(bytes, 0, HEADER_NWZ.length());
	if (HEADER_NWZ.equals(header)) {
	    // Compressed
	    byte[] uncompressed = new byte[bytes.length * 5];

	    Deflater deflater = new Deflater();
	    deflater.setInput(bytes, HEADER_NWZ.length(), bytes.length - HEADER_NWZ.length());
	    deflater.finish();
	    int uncompressedLength = deflater.deflate(uncompressed);
	    // TODO : test

	} else {
	    // Uncompressed

	}
	NwcFile nwc = new NwcFile();
	return nwc;
    }

    public static NwcFile fromFile(File file) {
	byte[] bytes = new byte[(int) file.length()];
	try {
	    FileInputStream in = new FileInputStream(file);
	    in.read(bytes);
	    return decode(bytes);
	} catch (IOException e) {
	    return null;
	}
    }
}