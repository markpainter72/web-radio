package radio;

import java.io.DataInputStream;
import java.io.IOException;

public class MPGHandler implements Runnable
{
	Thread inthread;
	Process process;
	RadioGUI titleCallback;
	
	public MPGHandler(RadioGUI titleCallback)
	{
		this.titleCallback = titleCallback;
	}
	
	public void start(String url, int volume)
	{
		if (process == null)
		{
			try 
			{
				process = Runtime.getRuntime().exec(buildCMD(url, volume));
				if (inthread == null)
				{
					inthread = new Thread(this);
					inthread.start();
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("runtime error, sorry");
				System.out.println("have you installed mpg123 ?");
				System.exit(-1);
			}
		}
	}
	
	public void stop()
	{
		if (inthread != null)
		{
			inthread.interrupt();
			inthread.stop();
		}
		if (process != null)
		{
			process.destroy();
			process = null;
		}
	}
	
	public void reload(String url, int volume)
	{
		stop();
		start(url, volume);
	}
	
	private String buildCMD(String url, int volume)
	{
		double volumePercent = volume/100.0;
		int volVal = (int) (volumePercent * 65536); //16 bit
		
		String result = new String();
		result += "mpg123 -f ";
		result += String.valueOf(volVal);
		result += " ";
		result += url;
		return result;
	}
	
	public void run() 
	{
		DataInputStream in = new DataInputStream(process.getErrorStream());//but why?
		while(true)
		{
			String msg = new String();
			try
			{
				msg = in.readLine();
			}
			catch(Exception e)
			{
				return;
			}
			if (msg.startsWith("ICY-META"))
			{
				String[] split = msg.split("=");
				titleCallback.setStreamTitle(split[1].substring(1, split[1].length()-2));
			}
		}
	}
}