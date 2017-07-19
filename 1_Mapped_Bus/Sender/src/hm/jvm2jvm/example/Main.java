package hm.jvm2jvm.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import io.mappedbus.MappedBusReader;
import io.mappedbus.MappedBusWriter;

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
		   File file = new File(FILE_NAME);
		   try {
			file.createNewFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		    
		
		IExam exam = new Exam("Tx");
		try {
			//byte[] objBytes = serialize(exam);
			byte[] objBytes = convertToBytes(exam);
			
			sendClass(objBytes);
			
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
	
	public static void sendClass(byte[] objBytes) {
		try {
			MappedBusWriter writer = new MappedBusWriter(FILE_NAME, FILE_SIZE, RECORD_LENGTH, false);
			writer.open();		
			//TODO divide file using RECORD_LENGTH
			writer.write(objBytes, 0, objBytes.length);			
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
	
	//
	private static byte[] convertToBytes(Object object) throws IOException {
	    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
	         ObjectOutput out = new ObjectOutputStream(bos)) {
	        out.writeObject(object);
	        return bos.toByteArray();
	    } 
	}
}
