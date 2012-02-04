package nwcfile;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.zip.Deflater;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;
import java.io.CharArrayReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;

public class NwcFile {
  private static final String HEADER_NWZ = "[NWZ]";

  private String m_company;
  private String m_product;

  private int m_minorVersion;
  private int m_majorVersion;

  private String m_name1;
  private String m_name2;

  private String m_title;
  private String m_author;
  private String m_copyright1;
  private String m_copyright2;
  private String m_comment;

  private int m_measureStart;
  private String m_margins;

  private List<NwcFont> m_fonts;
  private List<NwcStaff> m_staffList;

  private NwcFile(String company,
		  String product,
		  int minorVersion,
		  int majorVersion,
		  String name1,
		  String name2,
		  String title,
		  String author,
		  String copyright1,
		  String copyright2,
		  String comment,
		  int measureStart,
		  String margins,
		  List<NwcFont> fonts,
		  List<NwcStaff> staffList) {
    m_company = company;
    m_product = product;
    m_company = company;
    m_product = product;

    m_minorVersion = minorVersion;
    m_majorVersion = majorVersion;

    m_name1 = name1;
    m_name2 = name2;

    m_title = title;
    m_author = author;

    m_copyright1 = copyright1;
    m_copyright2 = copyright2;
    m_comment = comment;

    m_measureStart = measureStart;
    m_margins = margins;

    m_fonts = new ArrayList<NwcFont>(fonts);
    m_staffList = new ArrayList<NwcStaff>(staffList);
  }

  public static NwcFile fromInputStream(InputStream in) throws NwcFileException {
    int length;
    byte[] buffer;
    byte[] bytes;
    try {
      bytes = new byte[in.available()];
      in.read(bytes, 0, bytes.length);
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
    if (HEADER_NWZ.equals(new String(bytes, 0, HEADER_NWZ.length()))) {
      buffer  = new byte[bytes.length * 5];
      Inflater inflater = new Inflater();
      inflater.setInput(bytes, HEADER_NWZ.length() + 1, bytes.length - HEADER_NWZ.length() - 1);
      try {
	length = inflater.inflate(buffer);
      } catch (DataFormatException e) {
	throw new NwcFileException(e);
      }
    } else {
      throw new NwcFileException("Bad header.");
    }

    try {
      FileOutputStream fos = new FileOutputStream("uncompressed.nwc");
      fos.write(buffer, 0, length);
      fos.close();
    } catch (IOException e) {
      System.out.println("Could not write uncompressed file.");
    }

    InputStream input = new ByteArrayInputStream(buffer, 0, length);
    NwcFileReader reader = new NwcFileReader(input);

    return fromNwcFileReader(reader);
  }

  public static NwcFile fromNwcFileReader(NwcFileReader reader) throws NwcFileException {
    String company = reader.getNextField();
    String product = reader.getNextField();
    String version = reader.getNextField();
    int minorVersion = version.getBytes()[0];
    int majorVersion = version.getBytes()[1];

    String name1 = reader.getNextField();
    String name2 = reader.getNextField();

    reader.getNextField();

    String title = reader.getNextField();
    String author = reader.getNextField();
    String copyright1 = reader.getNextField();
    String copyright2 = reader.getNextField();
    String comment = reader.getNextField();

    reader.getNextField();
    reader.getNextField();

    int measureStart = reader.getNextField().getBytes()[0];
    String margins = reader.getNextField();

    reader.getNextField();
    reader.getNextField();
    reader.getNextField();

    List<NwcFont> fonts = new ArrayList<NwcFont>();
    for (int i = 0; i < 12; i++) {
      fonts.add(NwcFont.fromNwcFileReader(reader));
    }

    reader.getNextField();
    byte staffCount = reader.getNextField().getBytes()[0];

    List<NwcStaff> staffList = new ArrayList<NwcStaff>();
    for (int i = 0; i < staffCount; i++) {
      staffList.add(NwcStaff.fromNwcFileReader(reader));
    }

    return new NwcFile(company,
		       product,
		       minorVersion,
		       majorVersion,
		       name1,
		       name2,
		       title,
		       author,
		       copyright1,
		       copyright2,
		       comment,
		       measureStart,
		       margins,
		       fonts,
		       staffList);
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("Company : " + m_company + endl);
    builder.append("Product : " + m_product + endl);

    builder.append("Version : " + m_majorVersion + "." + m_minorVersion + endl);
    builder.append("Name 1: " + m_name1 + endl);
    builder.append("Name 2 : " + m_name2 + endl);

    builder.append("Title : " + m_title + endl);
    builder.append("Author : " + m_author + endl);

    builder.append("Copyright 1 : " + m_copyright1 + endl);
    builder.append("Copyright 2 : " + m_copyright2 + endl);
    builder.append("Comment : " + m_comment + endl);

    builder.append("Measure start : " + m_measureStart + endl);
    builder.append("Margins : " + m_margins + endl);

    builder.append(endl);

    builder.append("***** Fonts *****" + endl);
    int i = 1;
    for(NwcFont font : m_fonts) {
      builder.append("Font " + i + " :" + endl); 
      builder.append(font.toString());
      i++;
    }

    builder.append("***** Staff list *****" + endl);
    i = 1;
    for(NwcStaff staff : m_staffList) {
      builder.append("Staff " + i + " :" + endl); 
      builder.append(staff.toString());
      i++;
    }

    return builder.toString();
  }

  public static void main (String[] args) {
    if (args.length != 1) {
      System.out.println("Usage : nwcfile <file.nwc>");
      System.exit(0);
    }
    String filePath = args[0];
    try {
      NwcFile nwcFile = fromInputStream(new FileInputStream(filePath));
      System.out.println("***** File info *****");
      System.out.println(nwcFile.toString());
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    } catch (NwcFileException e) {
      System.out.println(e.toString());
    }
  }

}