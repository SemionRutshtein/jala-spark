package il.spark.jala.service;

import com.mongodb.spark.MongoSpark;
import il.spark.jala.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparkExecutorService {

    private final SparkSession sparkSession;

    public void getAllTransactions() {

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

        Dataset<Row> rowDataset = MongoSpark.load(javaSparkContext).toDF();
        rowDataset.show();

        sparkSession.stop();
        javaSparkContext.close();

    }

}
