package hm.jvm2jvm.example;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import io.mappedbus.MappedBusReader;

public class Main {
	
	private static final String FILE_NAME = "C:/tmp/test";
	private static final long FILE_SIZE = 100000L;
	private static final int RECORD_LENGTH = 1; //TODO divide the file to 32 records for readbility

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}	
	
	public static void main(String[] args) {		
		System.out.println("**********RECEIVER***************");
		try {		
			byte[] rxBytes;
			rxBytes = receiveClass();
			IExam receivedObj = (IExam) deserialize(rxBytes);
			System.out.println(receivedObj.getAnswer());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public static byte[] receiveClass() throws IOException{	

		MappedBusReader reader = new MappedBusReader(FILE_NAME, FILE_SIZE, RECORD_LENGTH); //TODO divide the file to 32 records for readbility
		

		byte[] data = new byte[(int) FILE_SIZE];
		while (true) {
			try {
				reader.open();
				if (reader.next()) {
					 reader.readBuffer(data, 0);
				}
				 else
					 break;
			} catch (EOFException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
