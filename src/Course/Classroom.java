package Course;

public class Classroom {
	public int[] booked_up;////前半学期
	public int[] booked_down;////后半学期
	public int id;
	public int up_people;
	public String[] subject_name;
	public Classroom(){
		booked_up = new int[20];
		booked_down = new int[20];
		subject_name = new String[20];
		for(int i = 0;i < 20;i++){
			subject_name[i] = null;
		}
		for(int i = 0; i < 20;i++){
			booked_up[i] = 0;
			booked_down[i] = 0;
		}
	}
	
}
