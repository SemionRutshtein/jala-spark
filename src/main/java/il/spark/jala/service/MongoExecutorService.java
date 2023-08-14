package il.spark.jala.service;

import il.spark.jala.models.Transaction;

import java.util.List;

public interface MongoExecutorService {
    List<Transaction> getAllTransactions();
}
