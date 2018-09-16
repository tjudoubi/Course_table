package Course;

import java.util.HashMap;

public class Class {
	public Subject[] subject;
	public int grade;
	public HashMap<String,Integer> map;
	
	public Class(){
		subject = new Subject[20];
		for(int i = 0;i < 20;i++){
			subject[i] = new Subject();
		}
		map = new HashMap<String,Integer>();
	}
}
