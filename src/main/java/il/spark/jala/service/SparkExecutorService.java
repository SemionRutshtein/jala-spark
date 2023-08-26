package il.spark.jala.service;

import com.mongodb.spark.MongoSpark;
import il.spark.jala.models.Artist;
import il.spark.jala.models.Book;
import il.spark.jala.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparkExecutorService {

    private final SparkSession sparkSession;

    public void getAllTransactions() {

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

        Dataset<Row> rowDataset = MongoSpark.load(javaSparkContext).toDF();
        rowDataset.show();
        long count = rowDataset.count();

        System.out.println("SHOW COUNT OF CURRENT DB TABLE : " + count);

        sparkSession.stop();
        javaSparkContext.close();

    }

    public void joinSample() {

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

        Dataset<Row> transactionsDataset = loadMongoCollection(javaSparkContext, "credit-card.transactions");
        Dataset<Row> booksDataset = loadMongoCollection(javaSparkContext, "credit-card.books");
        Dataset<Row> artistsDataset = loadMongoCollection(javaSparkContext, "credit-card.artists");

        // Select the first entity from each dataset
        Row firstTransaction = transactionsDataset.first();
        Row firstBook = booksDataset.first();
        Row firstArtist = artistsDataset.first();

        Dataset<Row> firstTransactionDF = sparkSession.createDataFrame(Collections.singletonList(firstTransaction), firstTransaction.schema());
        Dataset<Row> firstBookDF = sparkSession.createDataFrame(Collections.singletonList(firstBook), firstBook.schema());
        Dataset<Row> firstArtistDF = sparkSession.createDataFrame(Collections.singletonList(firstArtist), firstArtist.schema());


        // Create a DataFrame for the joined data
        // Create a DataFrame for the joined data
//        Dataset<Row> joinedDataset = firstTransactionDF
//                .join(firstBookDF, firstTransactionDF.col("amount").equalTo(firstBookDF.col("amount")))
//                .join(firstArtistDF, firstTransactionDF.col("externalId").equalTo(firstArtistDF.col("externalId")))
//                .select("creditCardSerialId", "externalId", "amount", "cardType");




        Dataset<Row> joinedDataset = firstTransactionDF
                .join(firstBookDF, firstTransactionDF.col("amount").equalTo(firstBookDF.col("amount")))
                .join(firstArtistDF, firstTransactionDF.col("externalId").equalTo(firstArtistDF.col("externalId")))
                .select(
                        firstTransactionDF.col("creditCardSerialId"),
                        firstTransactionDF.col("externalId"),
                        firstTransactionDF.col("amount"),
                        firstTransactionDF.col("cardType")
//                        firstBookDF.col("subtitle"),       // Make sure the column name matches the field name in the JSON
//                        firstArtistDF.col("knownAs")
                );


        // Perform further transformations or aggregations

        // Write the final dataset to a new MongoDB collection
        writeMongoCollection(joinedDataset, "credit-card.collectedEtlData");
    }

    private Dataset<Row> loadMongoCollection(JavaSparkContext javaSparkContext, String collectionUri) {
        return MongoSpark.load(javaSparkContext).withPipeline(Collections.singletonList(Document.parse("{ $match: { } }"))).toDF();
    }

    private void writeMongoCollection(Dataset<Row> dataset, String collectionUri) {
        MongoSpark.write(dataset).option("uri", "mongodb://admin:admin@localhost:27017/" + collectionUri).mode("overwrite").save();
    }
}
