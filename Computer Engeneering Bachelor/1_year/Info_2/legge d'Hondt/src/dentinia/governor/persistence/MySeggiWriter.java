package dentinia.governor.persistence;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import dentinia.governor.model.Elezioni;

public class MySeggiWriter implements SeggiWriter{
	private String outPutFileName;
	
	
	@Override
	public void stampaSeggi(Elezioni elezioni, String msg) throws IOException {
		PrintWriter pw=new PrintWriter(this.outPutFileName);
		DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL);
		
		pw.append(formatter.format(LocalDateTime.now()));
		pw.append(msg);
		pw.append(elezioni.toString());
		pw.close();
		
	}
	
	public MySeggiWriter(String s) {
		if(s.isEmpty() || s==null)
			throw new IllegalArgumentException();
		this.outPutFileName=s;
	}

}
