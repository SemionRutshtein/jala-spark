package il.spark.jala.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfigForSpring {

    @Bean
    public SparkSession sparkSessionDev(){
        return SparkSession.builder()
                .config("spark.mongodb.input.uri", "mongodb://admin:admin@localhost:27017/credit-card.transactions")
                .config("spark.mongodb.input.uri", "mongodb://admin:admin@localhost:27017/credit-card.books")
                .config("spark.mongodb.input.uri", "mongodb://admin:admin@localhost:27017/credit-card.transactions")
                .master("local[*]")
                .appName("jala")
                .getOrCreate();
    }

}