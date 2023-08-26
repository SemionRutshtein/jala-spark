package il.spark.jala.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("artists")
public class Artist {
    private String name;
    private String knownAs;
    private String born;
    private String died;
    private String nationality;
    private List<String> knownFor;
    private List<String> movements;
}
