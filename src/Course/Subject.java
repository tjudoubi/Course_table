package Course;

public class Subject{
	public String name;
	public int grade;
	public Integer ban_time;
	public int begin_week;
	public int end_week;
	public Integer classroom_id;
	public String teacher_name;
	public int all_people;
	public Integer now_people;
	public int times;/////每周上课次数
	public Subject(){
		name = new String();
		teacher_name = new String();
		classroom_id = null;
		now_people = null;
		ban_time = null;
	}
	public void copy(Subject subject) {
		// TODO Auto-generated method stub
		this.name = subject.name;
		this.grade = subject.grade;
		this.ban_time = subject.ban_time;
		this.begin_week = subject.begin_week;
		this.end_week = subject.end_week;
		this.classroom_id = subject.classroom_id;
		this.teacher_name = subject.teacher_name;
		this.all_people = subject.all_people;
		this.now_people = subject.now_people;
		this.times = subject.times;
	}
}
