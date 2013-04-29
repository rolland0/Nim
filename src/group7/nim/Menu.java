package group7.nim;

import java.util.ArrayList;

public class Menu {
	private ArrayList<MenuItem> menuItems;
	private String message;
	
	public Menu(String msg) {
		menuItems = new ArrayList<MenuItem>();
		message = msg;
	}
	
	public void addItem(MenuItem item) {
		menuItems.add(item);
	}
	
	public String getOptions() {
		String rtn = message;
		for(int i = 0; i < menuItems.size(); i++) {
			rtn += "\n" + i + " " + menuItems.get(i).getOption();
		}
		return rtn + "\n";
	}
	
	public boolean isValid(int target) {
		return target >= 0 && target < menuItems.size();
	}
	
	public void execute(int target) {
		menuItems.get(target).run();
	}
	
	public int highestOption() {
		return menuItems.size();
	}
}
