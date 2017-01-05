package radio;

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
		new Radio();
	}
	
	public Radio()
	{
		stations = new StationList("radio.config");
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