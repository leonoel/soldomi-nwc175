package nwcfile;

import java.util.List;
import java.util.ArrayList;

public class Chord extends Symbol {

  private Note m_note;
  private List<Note> m_notes = new ArrayList<Note>();

  public void setNote(Note note) {
    m_note = note;
  }

  public void addNote(Note note) {
    m_notes.add(note);
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Chord *****" + endl);
    builder.append("***** Base note" + endl);
    builder.append(m_note);
    builder.append("***** End base note" + endl);
    builder.append("***** Notes *****" + endl);
    for (Note note : m_notes) {
      builder.append(note.toString());
    }
    builder.append("***** End notes *****" + endl);
    builder.append("***** End chord *****" + endl);
    return builder.toString();
  }

  @Override
  public Chord marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  @Override
  public Chord unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setNote(new Note().unmarshall(reader));
    byte noteCount = reader.readByte();
    reader.skip(1);
    for (int i = 0; i < noteCount; i++) {
      byte crap = reader.readByte();
      addNote(new Note().unmarshall(reader));
    }
    return this;
  }

}