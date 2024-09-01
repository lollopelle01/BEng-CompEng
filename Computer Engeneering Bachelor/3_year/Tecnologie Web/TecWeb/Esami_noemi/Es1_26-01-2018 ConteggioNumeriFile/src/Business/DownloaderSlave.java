package Business;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class DownloaderSlave extends Thread{
	
	private String url;
	private String path;
	private int counter;

	public DownloaderSlave(String url, String contextPath) {
		super();
		this.url = url;
		this.path = contextPath;
		this.counter = 0;
	}
	
	public String getUrl()
	{
		return this.url;
	}
	
	public void setUrl(String u)
	{
		this.url = u;
	}
	
	public int getCounter() {
		return counter;
	}

	public void resetCounter()
	{
		this.counter = 0 ;
	}

	@Override
	public void run() {
		try {
			System.out.println(url);
			//String fileName = getFileNameFromURL(url);
			System.out.println(this.getName()+": "+this.path);
			System.out.println(this.getName()+": "+url);
			//URL page = new URL(url);
			BufferedReader in = new BufferedReader(new FileReader(url));
//			File f = new File(this.path+"/"+fileName+".html");
//			f.createNewFile();
//			PrintWriter writer = new PrintWriter(f);
			
			//BufferedWriter out = new BufferedWriter(new FileWriter(f));
			String inputLine="";
			while((inputLine = in.readLine())!=null)
			{
				for(int i=0;i<inputLine.length();i++)
				{
					char car = inputLine.charAt(i);
					if(car <= '9' || car >= '0')
					{
						counter++;
					}
				}
			}			
			in.close();
			
			
			System.out.println(this.getName()+": Job Done!");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getFileNameFromURL(String url)
	{
		StringTokenizer st = new StringTokenizer(url,".");
		st.nextToken();
		return st.nextToken();
		 
	}

}
