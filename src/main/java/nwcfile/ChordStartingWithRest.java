package nwcfile;

public class ChordStartingWithRest extends Symbol {
  private Note m_note;
  
  public void setNote(Note note) {
    m_note = note;
  }

  public ChordStartingWithRest marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public ChordStartingWithRest unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setNote(new Note().unmarshall(reader));
    return this;
  }
}