package il.spark.jala.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import static org.apache.spark.sql.functions.sum;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReaderSpServiceImpl implements ReaderSpService {
    private final SparkSession sparkSession;

    @Override
    public void readCsvFile() {
        String csvPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db/transact.csv";

        Dataset<Row> dataset = sparkSession
                .read()
                .option("header", "true")
                .csv(csvPath);

        System.out.println(dataset);
    }

    @Override
    public void writeToParquet() {
        String csvPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db/transact.csv";
        Dataset<Row> csvData = sparkSession.read()
                .option("header", "true")
                .csv(csvPath);

        Dataset<Row> aggregatedData = csvData.groupBy("cardType")
                .agg(sum("amount").alias("totalAmount"));

        String outputPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db";
        aggregatedData.write().parquet(outputPath);

        sparkSession.stop();
    }

    @Override
    public void joinData() {
        String csvPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db/transact.csv";
        String externalJsonPath = "path_to_your_external_json_file.json";

        Dataset<Row> csvData = sparkSession.read()
                .option("header", "true")
                .csv(csvPath);

        Dataset<Row> externalData = sparkSession.read().json(externalJsonPath);

        Dataset<Row> joinedData = csvData.join(externalData, "creditCardSerialId");
        joinedData.show();

        sparkSession.stop();
    }

    @Override
    public void groupingAndAggregation() {
        String csvPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db/transact.csv";
        Dataset<Row> csvData = sparkSession.read()
                .option("header", "true")
                .csv(csvPath);

        Dataset<Row> aggregatedData = csvData
                .groupBy("cardType")
                .agg(sum("amount")
                        .alias("totalAmount"));

        aggregatedData.show();

        sparkSession.stop();
    }

    @Override
    public void filteringData() {
        String csvPath = "/Users/semionrutshtein/IdeaProjects/jala-spark/src/main/resources/db/transact.csv";
        Dataset<Row> csvData = sparkSession.read()
                .option("header", "true")
                .csv(csvPath);

        Dataset<Row> filteredData = csvData.filter(csvData.col("amount").gt(1000));
        filteredData.show();

//        sparkSession.stop();

    }
}
