package radio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StationList 
{
	ArrayList<Station> stations = new ArrayList<Station>();
	
	public StationList(String file)
	{
		String content = new String();
		try
		{
			content = readData(file);
		}
		catch(Exception e){}
		String[] lines = content.split("\n");
		if (lines.length == 0)
		{
			System.out.println("no stations found in 'radio.config'");
			System.exit(-1);
		}
		try
		{
			for (int i = 0; i < lines.length; i++)
			{
				String[] lineSplit = lines[i].split("~");
				stations.add(new Station(lineSplit[0], lineSplit[1]));
			}
		}
		catch(Exception e)
		{
			System.out.println("incorrect format:");
			System.out.println("<station name>~<station URL>[newline]");
			System.out.println("<station name>~<station URL>[newline]");
			System.out.println("etc...");
			System.exit(-1);
		}
	}
	
	public String getURL(String name)
	{
		for (int i = 0; i < stations.size(); i++)
		{
			if (stations.get(i).name.equals(name))
			{
				return stations.get(i).url;
			}
		}
		return null;
	}
	
	public String readData(String name) throws IOException
	{
	    BufferedReader reader = new BufferedReader( new FileReader (name));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append('\n');
	    }
	    reader.close();
	    return stringBuilder.toString();
	}
}