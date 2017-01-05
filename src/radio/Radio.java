package radio;

import java.io.File;

public class Radio 
{
	RadioGUI gui;
	StationList stations;
	MPGHandler handler;
	
	boolean playing = false;
	String streamURL;
	int volume = 50;
	
	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			if (new File(args[0]).exists() == false)
			{
				System.out.println("config file not found: " + args[0]);
				return;
			}
			new Radio(args[0]);
		}
		else
		{
			new Radio();
		}
	}
	
	public Radio()
	{
		stations = new StationList("radio.config");
		streamURL = stations.stations.get(0).url;
		gui = new RadioGUI(this, stations);
		handler = new MPGHandler(gui);
	}
	
	public Radio(String path)
	{
		stations = new StationList(path);
		streamURL = stations.stations.get(0).url;
		gui = new RadioGUI(this, stations);
		handler = new MPGHandler(gui);
	}
	
	public void playButton()
	{
		playing = !playing;
		if (playing)
		{
			gui.setButtonText("  Pause  ");
			handler.start(streamURL, volume);
		}
		else
		{
			gui.setButtonText("  Play   ");
			handler.stop();
		}
	}
	
	public void volumeChanged(int volume)
	{
		this.volume = volume;
		if (playing)
		{
			handler.reload(streamURL, volume);
		}
	}
	
	public void stationChanged(String name)
	{
		this.streamURL = stations.getURL(name);
		if (playing)
		{
			handler.reload(streamURL, volume);
		}
	}
	
	public void onExit()
	{
		handler.stop();
	}
}