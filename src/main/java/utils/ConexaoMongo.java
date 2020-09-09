package utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConexaoMongo {
  private final String DB_NAME = "loja_pokemao";
  private MongoClient mongoClient;
  private MongoDatabase mongoDatabase;

	public MongoClient getMongoClient() {
		return this.mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getMongoDatabase() {
		return this.mongoDatabase;
	}

	public void setMongoDatabase(MongoDatabase mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

  public ConexaoMongo() {
    mongoClient = new MongoClient();
    mongoDatabase = mongoClient.getDatabase(DB_NAME);
  }

  public void closeMongoClient(){
    mongoClient.close();
  }
}