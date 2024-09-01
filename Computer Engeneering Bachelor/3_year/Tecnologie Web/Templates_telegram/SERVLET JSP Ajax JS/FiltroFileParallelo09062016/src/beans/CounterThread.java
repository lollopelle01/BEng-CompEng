package beans;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CounterThread extends Thread {
	
	private RandomAccessFile reader;
	private Result result;
	
	public CounterThread (RandomAccessFile reader, Result result) {
		this.reader = reader;
		this.result = result;
	}

	@Override
	public void run () {
		int count = 0;
		char temp;
		try {
			for (int i=0; i<1024; i++) {
				temp = (char) reader.read();
				if (temp == result.getCar())
					count++;
			} 
			reader.close();
			
			result.addToCount(count);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
