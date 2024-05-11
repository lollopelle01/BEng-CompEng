package agenda.persistence;

import java.util.StringTokenizer;

import agenda.model.Detail;
import agenda.model.DetailFactory;
import agenda.model.Phone;

public class PhonePersister implements DetailPersister{
	private String SEPARATOR=";";
	@Override
	public Detail load(StringTokenizer source) throws BadFileFormatException {
		 Phone p = (Phone) DetailFactory.of("Phone");
	        if(source.countTokens()!=2)
	            throw new BadFileFormatException("Phone: not enough tokens");
	       p.setDescription(source.nextToken(SEPARATOR));
	       p.setValue(source.nextToken(SEPARATOR));
	        return p;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		Phone p=(Phone) d;
		sb.append(p.getName());
		sb.append(SEPARATOR);
		sb.append(p.getDescription());
		sb.append(SEPARATOR);
		sb.append(p.getValue());
		sb.append(FileUtils.NEWLINE);
	}

}
