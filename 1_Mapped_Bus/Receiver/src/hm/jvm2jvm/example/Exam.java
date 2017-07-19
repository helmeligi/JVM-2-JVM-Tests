package hm.jvm2jvm.example;

public class Exam  implements IExam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id=9;
	private String st;
	
	Exam(String s){
		st=s;	
	}
	
	@Override
	public String getAnswer() {
		return st+id;
	}

}
