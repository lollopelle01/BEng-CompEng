package agenda.persistence;

import java.util.StringTokenizer;

import agenda.model.Detail;
import agenda.model.DetailFactory;
import agenda.model.EMail;
import agenda.model.Phone;

public class EMailPersister implements DetailPersister{
	private String SEPARATOR=";";
	@Override
	public Detail load(StringTokenizer source) throws BadFileFormatException {
		 EMail e = (EMail) DetailFactory.of("Email");
	        if(source.countTokens()!=2)
	            throw new BadFileFormatException("Email: not enough tokens");
	        e.setDescription(source.nextToken(SEPARATOR));
	        e.setValue(source.nextToken(SEPARATOR));
	        return e;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		EMail e=(EMail) d;
		sb.append(e.getDescription());
		sb.append(SEPARATOR);
		sb.append(e.getName());
		sb.append(SEPARATOR);
		sb.append(e.getValues());
		sb.append(FileUtils.NEWLINE);
	}

}
