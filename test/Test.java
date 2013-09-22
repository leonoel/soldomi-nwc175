import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileImporter;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.model.NwcFile;
import org.soldomi.model.tune.Tune;

public class Test {
    private static final String[] FILES = new String[] {
	"nwc/Apache.nwc",
	"nwc/CrescentCity2.nwc"
    };
    
    public static void main(String[] args) {
	for(String file : FILES) {
	    try {
		boolean result = readStreamAsNwc(new FileInputStream(file));
		System.out.println(file + " : " + (result ? "Success" : "Failure"));
	    } catch (FileNotFoundException e) {
		System.out.println(file + " : not found");
	    }
	}
    }

    private static boolean readStreamAsNwc(InputStream is) {
	try {
	    NwcFileReader reader = new NwcFileReader(is);
	    reader.setVerbose(true);
	    NwcFile nwcfile = new NwcFile().unmarshall(reader);
	    Tune tune = NwcFileImporter.run(nwcfile);
	    return true;
	} catch (NwcFileException e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
