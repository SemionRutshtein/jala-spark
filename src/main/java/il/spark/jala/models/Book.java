package il.spark.jala.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {

    private String isbn;
    private String title;
    private String subtitle;
    private String author;
    private String published;
    private String publisher;
    private Integer pages;
    private String description;
    private String website;
}
