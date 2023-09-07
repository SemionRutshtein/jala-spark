package il.spark.jala.service;

public interface ReaderSpService {
    void readCsvFile();
    void writeToParquet();
    void joinData();
    void groupingAndAggregation();
    void filteringData();
}
