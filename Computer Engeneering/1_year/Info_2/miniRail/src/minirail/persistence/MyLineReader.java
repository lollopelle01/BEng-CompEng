package minirail.persistence;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import minirail.model.Line;
import minirail.model.Section;

public class MyLineReader implements LineReader{
	private Line line;
	private String lineName;
	private List<Section> sections;
	
	@Override
	public Line getLine() {
		return this.line;
	}
	
	public MyLineReader(Reader reader) throws BadFileFormatException {
		this.sections=new ArrayList<Section>();
		BufferedReader br=new BufferedReader(reader);
		try {
			String riga;
			if((riga=br.readLine())!=null) {
				String[] splittati=riga.trim().split(" ");
				if(splittati.length!=2) throw new BadFileFormatException("campi linea mancanti");
				if(!splittati[0].equalsIgnoreCase("Line")) throw new BadFileFormatException("formato linea errato");
				this.lineName=splittati[1];
				
				while((riga=br.readLine())!=null) {
					String[] splittati2=riga.trim().split(" ");
					if(splittati2.length!=3) throw new BadFileFormatException("campi sezione mancanti");
					if(!splittati2[0].equalsIgnoreCase("Section")) throw new BadFileFormatException("formato sezione errato");
					
					String nomeSezione=splittati2[1];
					
					double lunghezza=Double.parseDouble(splittati2[2]);
					
					this.sections.add(new Section(nomeSezione, lunghezza));
				}
				this.line=new Line(this.lineName, this.sections);
			}
			else {
				throw new BadFileFormatException("linea nulla");
			}
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}

}
