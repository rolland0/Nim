package group7.nim;

public class Average {
	private double sum;
	private int frequency;
	private double value;

	public void add(double val) {
		frequency++;
		sum += val;
		value = sum / frequency;
	}

	public double getValue() {
		return value;
	}

	public String toString() {
		return "sum: " + sum + " frequency: " + frequency + " value: " + value;
	}
}
