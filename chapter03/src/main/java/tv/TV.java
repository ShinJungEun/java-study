package tv;

public class TV {
	private int channel;
	private int volume;
	private boolean power;

	//	public int getChannel() {
	//		
	//	}
	//	
	//	public int  getVolume() {
	//		
	//	}
	//	
	//	public boolean isPower() {
	//		
	//	}

	public TV(int channel, int volume, boolean power) {
		this.channel = channel;
		this.volume = volume;
		this.power = power;
	}

	public void power(boolean on) {
		if(on)
			this.power = true;
		else
			this.power = false;
	}

	public void channel(int channel) {


		this.channel = channel;
	}

	public void channel(boolean up) {
		channel(channel);
		if(up) {
			if(channel < 255)
				channel++;
		}
		else {
			if(channel > 1)
				channel--;
		}

		// 3항연산자
		// channel(channel + (up ? 1 : -1));  

	}

	public void volume(int volume) {
		this.volume = volume;
	}

	public void volume(boolean up) {
		volume(volume);
		if(up) {
			if(volume < 255)
				volume++;
		}
		else {
			if(volume > 1)
				volume--;
		}
	}

	public void status() {
		System.out.println("TV[channel=" + channel + ", volume=" + volume + ", power=" + power + "]");
	}
}
