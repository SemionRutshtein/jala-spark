package il.spark.jala.controller;

import il.spark.jala.models.Transaction;
import il.spark.jala.service.MongoExecutorService;
import il.spark.jala.service.SparkExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JalaController {

    private final MongoExecutorService mongoExecutorService;
    private final SparkExecutorService sparkExecutorService;


    @GetMapping("/check")
    public ResponseEntity<List<Transaction>> getData() {

        List<Transaction> allTransactions = mongoExecutorService.getAllTransactions();
        return new ResponseEntity<>(allTransactions, HttpStatus.OK);
    }


    @GetMapping("/check2")
    public void getData1() {
        sparkExecutorService.joinSample();
    }
}
