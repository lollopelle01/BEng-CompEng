package agenda.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringTokenizer;

import agenda.model.Contact;
import agenda.model.Detail;

public class TextContactsPersister implements ContactsPersister{
	private String SEPARATOR=";";
	
	/*private Optional<Contact> readContact(BufferedReader innerReader) throws IOException, BadFileFormatException{
		if(!innerReader.readLine().equals("StartContact")) {
			throw new BadFileFormatException("Non è inizio contatto\n");
		}
		else {
			var c = new Contact("","");
			String nomi=this.readLineSkippingEmpty(innerReader);
			var stk=new StringTokenizer(nomi);
			 if(stk.countTokens()!=2)
		            throw new BadFileFormatException("Contact: not enough tokens");
			c.setName(stk.nextToken(SEPARATOR));
			c.setSurname(stk.nextToken(SEPARATOR));
			this.readDetails(c, innerReader);
			return Optional.of(c);
		}
		
	}
	private void readDetails(Contact c, BufferedReader innerReader) throws IOException, BadFileFormatException {
		String riga=innerReader.readLine();
		if(riga!="EndContact") {
			var stk =new StringTokenizer(riga);
			var detailP=DetailPersister.of(stk.nextToken(SEPARATOR));
			c.getDetailList().add(detailP.load(stk));
			
		}
	}
	private String readLineSkippingEmpty(BufferedReader innerReader) throws IOException {
		String riga;
		do {
			riga=innerReader.readLine();
		}
		while(riga!=null && riga.trim().isEmpty());
		
		return riga;
	}
	private void saveContact(Contact c, Writer innerWriter) throws IOException {
		var sb =new StringBuilder();
		sb.append("StartContact");
		sb.append(c.getName());
		sb.append(c.getSurname());
		this.saveDetails(c.getDetailList(), sb);
		sb.append(FileUtils.NEWLINE);
		sb.append("EndContacts");
		innerWriter.write(sb.toString());
		
	}
	private void saveDetails(List<Detail> detailList, StringBuilder sb) {
		for(Detail d: detailList) {
			var detailP=DetailPersister.of(d.getName());
			detailP.save(d, sb);
		}
	}
	
	@Override
	public List<Contact> load(Reader reader) throws IOException, BadFileFormatException {
		if(reader==null) {
			throw new IOException();
		}
		var innerReader=new BufferedReader(reader);
		List<Contact> result = new ArrayList<Contact>();
		Optional<Contact> c = readContact(innerReader);
		while(c.isPresent()) {
			result.add(c.get());
			c=this.readContact(innerReader);
		}
		return result;
	}

	@Override
	public void save(List<Contact> contacts, Writer writer) throws IOException {
		
	}
	
	MIA VERSIONE
	*/
	


    @Override
    public List<Contact> load(Reader reader) throws IOException, BadFileFormatException {
        if(reader==null) throw new IOException("reader null");
        BufferedReader innerReader = new BufferedReader(reader);
        ArrayList<Contact> result = new ArrayList<>();
        Optional<Contact> c = readContact(innerReader);
        while (c.isPresent()) {
            result.add(c.get());
            c = readContact(innerReader);
        }
        return result;
    }

    private Optional<Contact> readContact(BufferedReader innerReader) throws IOException, BadFileFormatException {
        String line = readLineSkippingEmpty(innerReader);
        if (line == null) {
            return Optional.empty();
        }
        if (!line.equals("StartContact")) {
            throw new BadFileFormatException("StartContact expected");
        }
        
        line = readLineSkippingEmpty(innerReader);
        StringTokenizer tokenizer = new StringTokenizer(line);

        try {
            Contact c = new Contact(tokenizer.nextToken(SEPARATOR), tokenizer.nextToken(SEPARATOR));

            readDetails(c, innerReader);

            return Optional.of(c);
        } catch (NoSuchElementException e) {
            throw new BadFileFormatException("Tokens not found", e);
        }
    }

    private void readDetails(Contact c, BufferedReader innerReader) throws IOException, BadFileFormatException {
        boolean isDetail;
        do {
            String line = readLineSkippingEmpty(innerReader);
            if (line == null) {
                throw new BadFileFormatException("Detail or EndContact expected");
            }
            isDetail = !line.equals("EndContact");
            if (isDetail) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String detailType = tokenizer.nextToken(SEPARATOR);

                DetailPersister detailPersister = DetailPersister.of(detailType);

                if (detailPersister == null) {
                    throw new BadFileFormatException("Unknown Detail Type");
                }

                Detail d = detailPersister.load(tokenizer);
                c.getDetailList().add(d);
            }
        } while (isDetail);
    }
    
    private String readLineSkippingEmpty(BufferedReader innerReader) throws IOException {
        String line;
        do { // skips empty lines
            line = innerReader.readLine();
        } while (line != null && line.trim().isEmpty());
        return line;
    }

    @Override
    public void save(List<Contact> contacts, Writer writer) throws IOException {
        for (Contact c : contacts) {
            saveContact(c, writer);
        }
    }
    
    private void saveContact(Contact c, Writer innerWriter) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("StartContact" + FileUtils.NEWLINE);
        sb.append(c.getName() + SEPARATOR + c.getSurname()
                + FileUtils.NEWLINE);
        saveDetails(c.getDetailList(), sb);
        sb.append("EndContact" + FileUtils.NEWLINE);
        innerWriter.write(sb.toString());
    }

    private void saveDetails(List<Detail> detailList, StringBuilder sb) {
        for (Detail d : detailList) {
            DetailPersister loaderSaver = DetailPersister.of(d.getName());
            loaderSaver.save(d, sb);
        }
    }

}
