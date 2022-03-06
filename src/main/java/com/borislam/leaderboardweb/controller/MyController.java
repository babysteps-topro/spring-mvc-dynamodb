package com.borislam.leaderboardweb.controller;

import com.borislam.leaderboardweb.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.*;


@Controller
public class MyController {

    @Autowired
    private DynamoDbClient dynamoDbClient;


    @GetMapping("/")
    public String getLeaderboard(@RequestParam(name = "game") String gameParam, Model model)  {

        // Set up mapping of the partition name with the value
        HashMap<String, AttributeValue> attrValues =
                new HashMap<String,AttributeValue>();

        attrValues.put(":"+"v1", AttributeValue.builder()
                .s(gameParam)
                .build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName("Leaderboard")
                .keyConditionExpression("sk" + " = :" + "v1")
                .indexName("sk-top_score-index")
                .scanIndexForward(false)
                .expressionAttributeValues(attrValues)
                .build();
        List<Leaderboard> result= new ArrayList<Leaderboard>();
        model.addAttribute("playerScore",  result);
        try {
            QueryResponse response = dynamoDbClient.query(queryReq);
            List<Map<String, AttributeValue>> itemList  =  response.items();
            //convert to POJO
            for ( Map<String, AttributeValue> m: itemList) {
                String userId = m.get("user_id") !=null ? m.get("user_id").s() : "";
                String game =m.get("sk") !=null ?  m.get("sk") .s() :"";
                String userName = m.get("player_name") !=null ? m.get("player_name").s(): "";
                String location = m.get("location") !=null ? m.get("location").s() : "" ;
                Integer topScore = m.get("top_score") !=null ?  Integer.parseInt(m.get("top_score").n()) : null ;
                String scoreDate = m.get("top_score_date") !=null ? m.get("top_score_date").s() : "" ;
                Leaderboard ps = new Leaderboard( userId,  game,  userName, location, topScore,  scoreDate);
                result.add(ps);
            }
            System.out.println(itemList.size());

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());

        }
        return "main";
    }




    @GetMapping("/getone")
    public String getDynamoDBItem(@RequestParam(name = "userId") String userIdParam,
                                @RequestParam(name = "game") String gameParam, Model model) {

        HashMap<String,AttributeValue> keyToGet = new HashMap<String,AttributeValue>();

        keyToGet.put("user_id", AttributeValue.builder()
                .s(userIdParam).build());

        keyToGet.put("sk", AttributeValue.builder()
                .s(gameParam).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName("Leaderboard")
                .build();
        List<Leaderboard> result= new ArrayList<Leaderboard>();
        try {
            Map<String,AttributeValue> m = dynamoDbClient.getItem(request).item();

            if (m != null) {
                String userId = m.get("user_id") !=null ? m.get("user_id").s() : "";
                String game =m.get("sk") !=null ?  m.get("sk") .s() :"";
                String userName = m.get("player_name") !=null ? m.get("player_name").s(): "";
                String location = m.get("location") !=null ? m.get("location").s() : "" ;
                Integer topScore = m.get("top_score") !=null ?  Integer.parseInt(m.get("top_score").n()) : null ;
                String scoreDate = m.get("top_score_date") !=null ? m.get("top_score_date").s() : "" ;
                Leaderboard ps = new Leaderboard( userId,  game,  userName, location, topScore,  scoreDate);
                result.add(ps);
            } else {
                System.out.format("No item found with the key");
            }
            model.addAttribute("playerScore",  result);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return "main";
    }


    @GetMapping("/playerscore")
    public String queryPlayerScore(@RequestParam(name = "userId") String userIdParam, Model model) {

        // Set up mapping of the partition name with the value
        HashMap<String, AttributeValue> attrValues =
                new HashMap<String,AttributeValue>();

        attrValues.put(":"+"v1", AttributeValue.builder()
                .s(userIdParam)
                .build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName("Leaderboard")
                .keyConditionExpression("user_id" + " = :" + "v1")
                .expressionAttributeValues(attrValues)
                .build();
        List<Leaderboard> result= new ArrayList<Leaderboard>();
        try {
            QueryResponse response = dynamoDbClient.query(queryReq);
            List<Map<String, AttributeValue>> itemList  =  response.items();
            //convert to POJO
            for ( Map<String, AttributeValue> m: itemList) {
                String userId = m.get("user_id") !=null ? m.get("user_id").s() : "";
                String game =m.get("sk") !=null ?  m.get("sk") .s() :"";
                String userName = m.get("player_name") !=null ? m.get("player_name").s(): "";
                String location = m.get("location") !=null ? m.get("location").s() : "" ;
                Integer topScore = m.get("top_score") !=null ?  Integer.parseInt(m.get("top_score").n()) : null ;
                String scoreDate = m.get("top_score_date") !=null ? m.get("top_score_date").s() : "" ;
                Leaderboard ps = new Leaderboard( userId,  game,  userName, location, topScore,  scoreDate);
                result.add(ps);
            }
            System.out.println(itemList.size());
            //System.out.println(itemList);
            model.addAttribute("playerScore",  result);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return "main";
    }







}
