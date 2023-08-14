package il.spark.jala.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("transactions")
public class Transaction {

    @Id
    private String id;
    private String creditCardSerialId;
    private String externalId;
    private String type;
    private Long amount;
    private String cardType;
    private List<String> transactions;
    
}