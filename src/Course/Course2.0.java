package Course;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

//软件学院一共3个年级需要排课，大一4个班，大二2个班
//构造一个类，用来存储每个班级的课程表及课程信息
//大一、大二均需要上通识教育课，课程教室可容纳软工3个班；大一大二均需要上体育课，体育场馆可容纳软工3个班；
//高数、线代教室可容纳软工4个班，程序1、程序2在机房上课（机房容纳2个班），思修教室4个班，马原教室4个班，工图4个班，概率四个班，数据四个班，马原2个班，Java两个班，
//大一需要学习高数*3、线代*2、离散*2、计导*2、程序1、体育、思修、工图*2（两节课在同一天上）、通识
//大二需要学习概率*2、数据*2、马原、java*2、通识、体育、大物*2、程序2*2（在java上完之后上）
//通识课只能在下午上

public class Course {
	private static Class [] every_class;
	private static Classroom[] classroom;
	private static Subject[] subject;

	
	public Course() throws IOException{
		
		
	}
	
	public static void main(String[] args) throws IOException{
		init_subject();
		init_Class();
		init_Classroom();
		if(arrange(0,0)){
			System.out.println("ok");
			System.out.println("classroom 0:10 :+"+classroom[0].booked_up[10]);
			System.out.println("subject 6:"+subject[6].all_people);
		}else{
			System.out.println("l");
		}
	}
	
	public static boolean arrange(int class_index, int course_index){
		if(class_index == 6){
			for(int j = 0;j < 6;j++){
				System.out.print("j:"+j);
			for(int i = 0; i < 20;i++){
				
				if(!every_class[j].subject[i].name.equals(""))
				System.out.print(" "+i+":"+every_class[j].subject[i].name+" "+every_class[j].subject[i].classroom_id);
				
			}
			System.out.println();
			}
//			for(int i = 0;i < 4;i++){
//				System.out.print(" class_index:"+i);
//				HashMap<String,Integer> map = every_class[i].map;
//				Iterator iter = map.entrySet().iterator();
//				while (iter.hasNext()) {
//					Map.Entry entry = (Map.Entry) iter.next();
//					Object key = entry.getKey();
//					Object val = entry.getValue();
//					System.out.print(key+" "+val+"----");
//				}
//				
//			}
//			System.out.println();
			if(match_all_subject()){
				return true;
			}else{
				return false;
			}
			
		}
		
//		System.out.println("arrange:"+class_index+ " course_index:"+course_index);
		int len = every_class[class_index].subject.length;
		for(int j = 0;j < subject.length;j++){
			if(check(class_index,course_index,j)){
				do_something(class_index,course_index,j);
				if(course_index == 19){
					if(arrange(class_index+1,0)){
						return true;
					}
				}else{
					if(arrange(class_index,course_index+1)){
						return true;
					}
				}
				back_do_something(class_index,course_index,j);
			}
		}
		return false;
	}

	
	private static boolean match_all_subject(){
		for(int i = 0;i < every_class.length;i++){
			for(int j = 0;j < subject.length;j++){
				String str = subject[j].name;
				int a = subject[j].times;
				HashMap<String,Integer> map = every_class[i].map;
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					if(str.equals(key.toString())){
						if(subject[j].times != Integer.valueOf(val.toString())){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	
	private static void back_do_something(int class_index, int course_index, int subject_index) {
		if(subject_index == subject.length-1){
			return ;
		}
		
		int value = every_class[class_index].map.get(subject[subject_index].name);
		every_class[class_index].map.put(subject[subject_index].name, value-1);
//		System.out.println(" back_do_something:"+class_index+ "course_index:"+course_index+" "+every_class[class_index].subject[course_index].classroom_id);
		int a_id = every_class[class_index].subject[course_index].classroom_id;
		String subject_name = subject[subject_index].name;
		if(subject[subject_index].end_week > 11 &&subject[subject_index].begin_week == 4){
			classroom[a_id].booked_up[course_index] -= 1;classroom[a_id].booked_down[course_index] -= 1;
		}else if(subject[subject_index].end_week <= 11 &&subject[subject_index].begin_week == 4){
			classroom[a_id].booked_up[course_index] -= 1;
		}else if(subject[subject_index].end_week > 11 &&subject[subject_index].begin_week == 12){
			classroom[a_id].booked_down[course_index] -= 1;
		}
		every_class[class_index].subject[course_index] = null;
		// TODO Auto-generated method stub
		
	}

	private static void do_something(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		every_class[class_index].subject[course_index] = new Subject();
		if(subject_index == subject.length-1){
			return ;
		}
		
//		System.out.println("do_something :"+class_index+" course_index:"+course_index+" subject_index:"+subject_index);
		every_class[class_index].subject[course_index].copy(subject[subject_index]);
//		add_one(class_index, course_index, subject_index);
		if(every_class[class_index].map.containsKey(subject[subject_index].name)){
			int value = every_class[class_index].map.get(subject[subject_index].name);
			every_class[class_index].map.put(subject[subject_index].name, value+1);
		}else{
			every_class[class_index].map.put(subject[subject_index].name, 1);
		}
		
		if(check_room_same(class_index,course_index,subject_index)){
			int a_id = 0;
			String str2 = subject[subject_index].name;
			for(int i = 0;i < class_index;i++){
				if(every_class[i].subject[course_index].name.equals(str2)&&every_class[i].subject[course_index].now_people < subject[subject_index].all_people){
					every_class[i].subject[course_index].now_people += 1;
					a_id = every_class[i].subject[course_index].classroom_id;
					every_class[class_index].subject[course_index].now_people = every_class[i].subject[course_index].now_people;
					
				}
			}
			if((subject[subject_index].end_week > 11 &&subject[subject_index].begin_week == 4)){//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////***************************************************
				classroom[a_id].booked_up[course_index] += 1;classroom[a_id].booked_down[course_index] += 1; every_class[class_index].subject[course_index].classroom_id = a_id;
			}else if((subject[subject_index].end_week <= 11 &&subject[subject_index].begin_week == 4)){
				classroom[a_id].booked_up[course_index] += 1; every_class[class_index].subject[course_index].classroom_id = a_id;
			}else if((subject[subject_index].end_week > 11 &&subject[subject_index].begin_week == 12)){
				classroom[a_id].booked_down[course_index] += 1; every_class[class_index].subject[course_index].classroom_id = a_id;
			}
		}else{
				String subject_name = subject[subject_index].name;
				if(subject_name.equals("体育")&&classroom[9].booked_down[course_index]==0){
					classroom[9].booked_down[course_index]+= 1;classroom[9].booked_up[course_index]+=1;
					every_class[class_index].subject[course_index].classroom_id = 9;
				}else{
				for(int i = 0;i < classroom.length-2;i++){
					int begin = subject[subject_index].begin_week;
					int end = subject[subject_index].end_week;
					if(begin == 4&&end == 19&&classroom[i].up_people>=subject[subject_index].all_people){
						if(classroom[i].booked_down[course_index]==0&&classroom[i].booked_up[course_index]==0){
							classroom[i].booked_down[course_index]+= 1;classroom[i].booked_up[course_index]+=1;every_class[class_index].subject[course_index].classroom_id = i;break;
						}	
					}else if(begin == 4&&end == 11&&classroom[i].up_people>=subject[subject_index].all_people){
						if(classroom[i].booked_up[course_index]==0){
							classroom[i].booked_up[course_index]+=1;every_class[class_index].subject[course_index].classroom_id = i;break;
						}
					}else if(begin == 12&&end == 19&&classroom[i].up_people>=subject[subject_index].all_people){
						if(classroom[i].booked_down[subject_index]==0){
							classroom[i].booked_down[course_index]+=1;every_class[class_index].subject[course_index].classroom_id = i;break;
						}
					}
				}
				}
		}
		
	}
	


//	private static void add_one(int class_index, int course_index, int subject_index) {
//		// TODO Auto-generated method stub
//		String name = every_class[class_index].subject[course_index].name;
//		for(int i = 0;i < class_index;i++){
//			if(name.equals(every_class[i].subject[course_index].name))
//				every_class[i].subject[course_index].now_people += 1;
//		}
//		
//	}

	private static boolean check(int class_index, int course_index, int subject_index) {
		if(subject_index == subject.length-1){
			return true;
		}
		if(!check_hebankewenti(class_index, course_index, subject_index)){
			return false;
		}
		if(!check_full_class(class_index,course_index,subject_index)){
			return false;
		}
		// TODO Auto-generated method stub
		if(!check_match(class_index,course_index,subject_index)){
//			System.out.println("check_match:"+subject_index);
			return false;
		}
		if(!check_full(class_index,course_index,subject_index)){
//			System.out.println("check_full:"+subject_index);
			return false;
		}
		
		if(!check_repeat(class_index,course_index,subject_index)){
//			System.out.println("check_repeat:"+subject_index);
			return false;
		}
		
		if(!check_time(class_index,course_index,subject_index)){
			return false;
		}
		
		if(!check_room_same(class_index,course_index,subject_index)){
			
			if(!check_classroom(class_index,course_index,subject_index)){
				return false;
			}
		}
		
		return true;
	}

	

	private static boolean check_hebankewenti(int class_index, int course_index, int subject_index) {
	// TODO Auto-generated method stub
		int all_people = subject[subject_index].all_people;
		if(class_index%all_people != 0){
			if(!search_up(class_index,course_index,subject_index,class_index%all_people)){
				return false;
			}
		}
	return true;
}

	private static boolean search_up(int class_index, int course_index, int subject_index,int mod) {
		// TODO Auto-generated method stub
		int a = mod;
		String name = subject[subject_index].name;
		for(int i = class_index-1;a!= 0;i--){
			if(!every_class[i].subject[course_index].name.equals(name)){
				return false;
			}
			a--;
		}
		return true;
	}

	private static boolean check_full_class(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		String name = subject[subject_index].name;
		for(int i = 0;i < class_index;i++){
			if(every_class[i].subject[course_index].name.equals(name)){
				int begin = subject[subject_index].begin_week;
				int end = subject[subject_index].end_week;
				if(begin == 4&&end == 19){
					if(classroom[i].booked_down[course_index]==subject[subject_index].all_people||classroom[i].booked_up[course_index]==subject[subject_index].all_people){
						System.out.println("class_index:"+class_index+ ";"+subject_index);
						return false;
					}
				}else if(begin == 4&&end == 11){
					if(classroom[i].booked_up[course_index]==subject[subject_index].all_people){
						return false;
					}
				}else if(begin == 12&&end == 19){
					if(classroom[i].booked_down[course_index]==subject[subject_index].all_people){
						return false;
					}
				}
			}
		}
		return true;
	}

	
	
	
	
	private static boolean check_room_same(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		String str2 = subject[subject_index].name;
		for(int i = 0;i < class_index;i++){
			if(every_class[i].subject[course_index].name.equals(str2)&&every_class[i].subject[course_index].now_people < subject[subject_index].all_people){
				return true;
			}
		}
		return false;
		
	}

	private static boolean check_match(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		int grade = subject[subject_index].grade;
		if(grade == 0){
			return true;
		}
		if(grade == every_class[class_index].grade){
			return true;
		}
		
		return false;
	}

	private static boolean check_full(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		String subject_name = subject[subject_index].name;
		if(every_class[class_index].map.containsKey(subject_name)){
			if(every_class[class_index].map.get(subject_name) < subject[subject_index].times){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	private static boolean check_classroom(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		String subject_name = subject[subject_index].name;
		if(!subject_name.equals("体育")){
		for(int i = 0;i < classroom.length-2;i++){
			int begin = subject[subject_index].begin_week;
			int end = subject[subject_index].end_week;
			if(begin == 4&&end == 19&&classroom[i].up_people>=subject[subject_index].all_people){
				if(classroom[i].booked_down[course_index]==0&&classroom[i].booked_up[course_index]==0){
					return true;
				}
			}else if(begin == 4&&end == 11&&classroom[i].up_people>=subject[subject_index].all_people){
				if(classroom[i].booked_up[course_index]==0){
					return true;
				}
			}else if(begin == 12&&end == 19&&classroom[i].up_people>=subject[subject_index].all_people){
				if(classroom[i].booked_down[course_index]==0){
					return true;
				}
			}
		}
		}else{
			if(classroom[9].booked_down[course_index]==0&&classroom[9].booked_up[course_index]==0){
				return true;
			}
		}
		return false;
	}

	private static boolean check_time(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		String str = subject[subject_index].teacher_name;
		String str2 = subject[subject_index].name;
		for(int i = 0;i < class_index;i++){
			if(every_class[i].subject[course_index].teacher_name.equals(str)&&!every_class[i].subject[course_index].name.equals(str2)){
				return false;
			}
			if(every_class[i].subject[course_index].name.equals(str2)&&every_class[i].subject[course_index].now_people >= subject[subject_index].all_people){
				return false;
			}
			if(subject[subject_index].ban_time!=null&&subject[subject_index].ban_time == course_index%4){
				return false;
			}
		}
		return true;
	}

	
	private static boolean check_repeat(int class_index, int course_index, int subject_index) {
		// TODO Auto-generated method stub
		int day = course_index/4;
		int begin_index = day*4;
		for(int i = begin_index;i < course_index;i++){
			if(every_class[class_index].subject[i].name.equals(subject[subject_index].name)){
				return false;
			}
		}
		
		return true;
	}

	private static void init_subject() throws IOException {
		// TODO Auto-generated method stub
		String pathName = "课程.txt";
		File file = new File(pathName);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"GBK");
		BufferedReader br = new BufferedReader(isr);
		String content = br.readLine() ;
		br.close();
		isr.close();
		content = content.substring(2, content.length()-2);
		content = content.replace("},{", ";");
		String[] arrContent = content.split(";");
		
		String[] rowsName = new String[]{"name","grade","ban_time","begin_week","end_week","classroom_id","teacher_name","all_people","times","noew_people"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		
		for(String arrc : arrContent){
			JSONObject jsonObj = JSONObject.fromObject("{"+arrc+"}");
//				String code = jsonObj.getString("COUNTRY_CODE"); 
//				String name = jsonObj.getString("COUNTRY_NAME"); 
//				String code = jsonObj.getString("code"); 
//				String name = jsonObj.getString("name");  
			String name = jsonObj.getString("name"); 
			String grade = jsonObj.getString("grade");
			String ban_time = jsonObj.getString("ban_time");
			String begin_week = jsonObj.getString("begin_week");
			String end_week = jsonObj.getString("end_week"); 
			String classroom_id = jsonObj.getString("classroom_id");
			String teacher_name = jsonObj.getString("teacher_name");
			String all_people = jsonObj.getString("all_people");
			String times = jsonObj.getString("times");
			String now_people = jsonObj.getString("now_people");
			
			
			Object[] obj = new Object[rowsName.length];
			obj[0] = name;
			obj[1] = grade;
			obj[2] = ban_time;
			obj[3] = begin_week;
			obj[4] = end_week;
			obj[5] = classroom_id;
			obj[6] = teacher_name;
			obj[7] = all_people;
			obj[8] = times;
			obj[9] = now_people;
			dataList.add(obj);
			}
		
		subject = new Subject[dataList.size()+1];
		for(int i = 0 ; i < subject.length;i++){
			subject[i] = new Subject();
		}
		for(int i = 0 ;i < dataList.size();i++){
			Object[] obj = dataList.get(i);
			subject[i].name = obj[0].toString();
			subject[i].grade = Integer.valueOf(obj[1].toString());
			
			if(obj[2].toString().equals("null"))
				subject[i].ban_time = null;
			else
				subject[i].ban_time = Integer.valueOf(obj[2].toString());
			
			subject[i].begin_week = Integer.valueOf(obj[3].toString());
			subject[i].end_week = Integer.valueOf(obj[4].toString());
			
			if(obj[5].toString().equals("null"))
				subject[i].classroom_id = null;
			else
				subject[i].classroom_id = Integer.valueOf(obj[5].toString());
			subject[i].teacher_name = obj[6].toString();
			subject[i].all_people = Integer.valueOf(obj[7].toString());
			subject[i].times = Integer.valueOf(obj[8].toString());
			subject[i].now_people = Integer.valueOf(obj[9].toString());
			
		}
		print();
		
		
	}
	
	
	public static void init_Class(){
		every_class = new Class[6];
		for(int i = 0;i < every_class.length;i++){
			every_class[i] = new Class();
			if(i < 4){
				every_class[i].grade = 1;
			}else{
				every_class[i].grade = 2;
			}
		}
	}
	
	public static void init_Classroom(){
		classroom = new Classroom[10];
		for(int i = 0;i < 10;i++){
			classroom[i] = new Classroom();
			classroom[i].id = i;
			classroom[i].up_people = 4;
		}
		classroom[8].up_people = 2;/////机房
		classroom[9].up_people = 3;/////体育馆
		
	}
	
	
	public static void print(){
		for(int i = 0;i < subject.length;i++){
			System.out.println("name:"+subject[i].name+" grade"+subject[i].grade+" ban_time:"+subject[i].ban_time+" begin_week:"+subject[i].begin_week+" end_week:"+subject[i].end_week+" classroom_id:"+subject[i].classroom_id+" teacher_name:"+subject[i].teacher_name+" all_people:"+subject[i].all_people+" times:"+subject[i].times);
		}
	}
}
