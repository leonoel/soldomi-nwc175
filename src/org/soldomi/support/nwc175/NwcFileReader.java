package org.soldomi.support.nwc175;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.io.ByteArrayOutputStream;

public class NwcFileReader {
    private static final boolean WRITE_INFLATED = false;
    private static final byte[] HEADER = "[NWZ]\u0000".getBytes();

    private Boolean m_verbose = Boolean.FALSE;
    private final DataInputStream m_data;

    public NwcFileReader(InputStream input) throws NwcFileException {

	ByteArrayOutputStream outputBuffer;
	byte[] buf = new byte[1024];
	int bytesRead; 

	try {
	    for (byte b : HEADER) {
		if (input.read() != b) {
		    throw new NwcFileException("Bad header.");
		}
	    }
	    outputBuffer = new ByteArrayOutputStream();
	    while((bytesRead = input.read(buf, 0, buf.length)) != -1) {
		outputBuffer.write(buf, 0, bytesRead);
	    }
	    outputBuffer.flush();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}

	Inflater inflater = new Inflater();
	inflater.setInput(outputBuffer.toByteArray());

	try {
	    outputBuffer = new ByteArrayOutputStream();
	    while(!inflater.finished()) {
		bytesRead = inflater.inflate(buf);
		outputBuffer.write(buf, 0, bytesRead);
	    }
	    outputBuffer.flush();
	} catch (DataFormatException e) {
	    throw new NwcFileException(e);
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}

	byte[] data = outputBuffer.toByteArray();

	if (WRITE_INFLATED)
	    writeInflated("inflated.nwc", data);

	m_data = new DataInputStream(new ByteArrayInputStream(data));
    }

    public void setVerbose(boolean value) {
	m_verbose = value;
    }

    public void log(String value) {
	if (m_verbose) {
	    System.out.println(value);
	}
    }

    private void writeInflated(String fileName, byte[] data) throws NwcFileException {
	try {
	    FileOutputStream fos = new FileOutputStream(fileName);
	    fos.write(data);
	    fos.close();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }

    public String readString() throws NwcFileException {
	StringBuffer buffer = new StringBuffer();
	int r;
	try {
	    while((r = m_data.read()) != 0) {
		buffer.append((char) r);
	    }
	    return buffer.toString();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }
  
    public int readByte() throws NwcFileException {
	try {
	    return (int) m_data.readByte();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }

    public int readUnsignedByte() throws NwcFileException {
	try {
	    return m_data.readUnsignedByte();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }

    public int readShort() throws NwcFileException {
	try {
	    byte[] b = new byte[2];
	    m_data.read(b);
	    return new DataInputStream(new ByteArrayInputStream(new byte[] {b[1], b[0]})).readShort();
	    //	    int lsb = (int)m_data.readByte();
	    //	    int msb = (int)m_data.readByte();
	    //	    return (msb << 8) + lsb;
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }

    public int readUnsignedShort() throws NwcFileException {
	try {
	    byte[] b = new byte[2];
	    m_data.read(b);
	    return new DataInputStream(new ByteArrayInputStream(new byte[] {b[1], b[0]})).readUnsignedShort();
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }

    public void skip(int byteCount) throws NwcFileException {
	try {
	    m_data.skipBytes(byteCount);
	} catch (IOException e) {
	    throw new NwcFileException(e);
	}
    }
}
