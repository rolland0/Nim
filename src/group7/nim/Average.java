package group7.nim;

public class Average {
	private double sum;
	private int frequency;

	public void add(double val) {
		frequency++;
		sum += val;
	}

	public double getValue() {
		return sum / frequency;
	}

	public String toString() {
		return "sum: " + sum + " frequency: " + frequency + " value: " + getValue();
	}
}
