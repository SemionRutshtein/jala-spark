package il.spark.jala.service;

import il.spark.jala.models.Transaction;
import il.spark.jala.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoExecutorServiceImpl implements MongoExecutorService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> all = new ArrayList<>();

        try {
            all = transactionRepository.findAll();
        } catch (Exception e) {
            log.error("Received exception when trying to execute data e -> {}", e.getMessage());
        }

        log.info("All entities count is {}", all.size());
        return all;
    }
}
