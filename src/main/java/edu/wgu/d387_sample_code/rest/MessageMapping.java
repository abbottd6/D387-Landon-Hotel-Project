package edu.wgu.d387_sample_code.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.Executors.newFixedThreadPool;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MessageMapping{
    static ExecutorService messageExecutor=newFixedThreadPool(5);
    String[] welcomeMessage;
    String welcome_en;
    String welcome_fr;
    Properties prop = new Properties();


    @RequestMapping(path = "/welcome", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] displayMessage() throws InterruptedException {
        List<String> messageList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(2);
        messageExecutor.execute(()-> {
            try {
                InputStream stream = new ClassPathResource("welcomeMessage_en_CA.properties").getInputStream();
                prop.load(stream);
                welcome_en = prop.getProperty("welcome_en");
                synchronized (messageList) {
                    messageList.add(welcome_en);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                latch.countDown();
            }
        });
        messageExecutor.execute(()-> {
            try {
                InputStream stream = new ClassPathResource("welcomeMessage_fr_CA.properties").getInputStream();
                prop.load(stream);
                welcome_fr = prop.getProperty("welcome_fr");
                synchronized (messageList) {
                    messageList.add(welcome_fr);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                latch.countDown();
            }
        });
        latch.await();
        welcomeMessage = new String[]{"Thread 1: " + messageList.get(0) + "\n" + "Thread 2: " + messageList.get(1)};
        System.out.println(Arrays.toString(welcomeMessage));
        return welcomeMessage;
    }



}
