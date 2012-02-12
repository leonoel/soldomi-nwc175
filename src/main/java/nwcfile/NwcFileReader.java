package nwcfile;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.io.ByteArrayOutputStream;

public class NwcFileReader {
  private static final byte[] HEADER = "[NWZ]\u0000".getBytes();

  private DataInputStream m_data;

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

    // To be deleted
    try {
      FileOutputStream fos = new FileOutputStream("uncompressed.nwc");
      fos.write(data);
      fos.close();
    } catch (IOException e) {
      System.out.println("Could not write uncompressed file.");
    }

    m_data = new DataInputStream(new ByteArrayInputStream(data));
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
  
  public byte readByte() throws NwcFileException {
    try {
      return m_data.readByte();
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
  }

  public short readShort() throws NwcFileException {
    try {
      byte lsb = m_data.readByte();
      byte msb = m_data.readByte();
      return (short) ((msb << 8) | (lsb & 0xFF));
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