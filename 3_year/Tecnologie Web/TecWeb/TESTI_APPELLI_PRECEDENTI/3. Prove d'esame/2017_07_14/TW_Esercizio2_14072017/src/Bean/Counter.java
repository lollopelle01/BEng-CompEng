package Bean;

public class Counter {
	
	int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Counter() {
		this.value = 0;
	}
	
	public void incrementOf(int value)
	{
		this.value += value;
	}
	
	public void reset()
	{
		this.value = 0;
	}

	@Override
	public String toString() {
		return "Counter [value=" + value + "]";
	}
	
	
	
	

}
