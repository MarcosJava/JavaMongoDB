package mongodb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class Test {
	
	
	public void iniciarMongo() throws Exception{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		
		DBCollection coll = db.getCollection("a");
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
	}
	
	public void trazendoTudoMongo() throws Exception{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		
		DBCollection coll = db.getCollection("a");
		DBCursor cursor = coll.find();
		while ( cursor.hasNext()){
			System.out.println(cursor.next());
		}
	}
	
	
	public void trazendoTudoMongoImg() throws Exception{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		
		DBCollection coll = db.getCollection("myfiles.chunks");
		DBCursor cursor = coll.find();
//		while ( cursor.hasNext()){
//			System.out.println(cursor.next());
//		}
		
		
		coll = db.getCollection("myfiles.files");
		cursor = coll.find();
		while ( cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		
		coll = db.getCollection("myfiles.indexes");
		cursor = coll.find();
//		while ( cursor.hasNext()){
//			System.out.println(cursor.next());
//		}
		
		
	}
	
	
	public void insertMongo(String nome, int idade, char sexo, float altura, float peso) throws Exception {
		
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		
		DBCollection coll = db.getCollection("a");
		
		BasicDBObject doc = new BasicDBObject("nome", nome)
        .append("idade", idade)
        .append("sexo", sexo)
        .append("info", new BasicDBObject("x", altura).append("y", peso));
		coll.insert(doc);
	}
	
	public void insertImagemMongo() throws Exception {
		
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		GridFS gridFS = new GridFS(db, "myfiles");
		
		FileInputStream inputStream = null;
		 
		try {
		         inputStream = new FileInputStream("C:/Users/u6448938/Desktop/JAVA/MONGODB/video1.mp4");
		} catch (FileNotFoundException e) {
		         e.printStackTrace();
		}
		 
		BasicDBObject meta = new BasicDBObject();
		meta.put("tags", Arrays.asList("mongodb", "gridfs", "java"));
		meta.put("description", "MongoDB GridFS lesson");
		 
		GridFSInputFile gridFSInputFile = 
		          gridFS.createFile(inputStream, "gridfs.mp4");
		 
		gridFSInputFile.setMetaData(meta);
		gridFSInputFile.setContentType("video/mp4");
		gridFSInputFile.save();
		
		
		
	}
	
	public void trazendoImagem() throws Exception{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("teste");
		GridFS gridFS = new GridFS(db, "myfiles");
		
		
		DBObject video = new BasicDBObject("filename", "gridfs.mp4");
		 
		GridFSDBFile gridFSDBFile = gridFS.findOne(video);
		 
		try {
		         FileOutputStream outputStream = 
		                     new FileOutputStream("C:/Users/u6448938/Desktop/JAVA/MONGODB/video_copy.mp4");
		 
		         gridFSDBFile.writeTo(outputStream);
		} catch (FileNotFoundException e) {
		         System.out.println("Can't read file - " + e.getCause());
		} catch (IOException e) {
		         System.out.println("Can't write file copy - " + e.getCause());
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		Test test = new Test();
		try {
			//test.insertMongo();
			
			//test.insertMongo("Marcos", 23, 'M', 1.73f, 80.5f);
			
			//test.trazendoTudoMongoImg();
			//test.trazendoImagem();
			//test.insertImagemMongo();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
