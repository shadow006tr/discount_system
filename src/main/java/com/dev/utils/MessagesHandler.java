package com.dev.utils;


import com.google.gson.Gson;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.dev.Persist;
import com.dev.objects.*;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessagesHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();
    private static Map <String, DiscountAndSession>sessionMap =new HashMap<>();
    private static int totalSessions;
    private Persist persist;

    @PostConstruct
    public void init () {
        new Thread(() -> {
            while (true) {
                for (Map.Entry <String, DiscountAndSession> entry:sessionMap.entrySet()){
                    this.checkIfDiscountOccur(entry.getValue().getDiscounts(),entry.getKey());
                }

            }
        }).start();
    }




    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Map<String, String> map = Utils.splitQuery(session.getUri().getQuery());
        sessionMap.put(map.get("token"),new DiscountAndSession(persist.getSalesRelevantForUser(map.get("token")),session));
        sessionList.add(session);
        totalSessions = sessionList.size();
        System.out.println("afterConnectionEstablished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("handleTextMessage");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
        System.out.println("afterConnectionClosed");
    }

    public void sendNewNotification(String token,DiscountObject discount) {
            WebSocketSession session=sessionMap.get(token).getSession();
            String json =new Gson().toJson(discount);
            try {
                session.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void checkIfDiscountOccur(List<DiscountObject> discounts, String token) {
        Date now=new Date();
        for (DiscountObject discount:discounts){
            Date startDiscount =discount.getDiscountStart();
            Date endDiscount =discount.getDiscountEnd();
            if (startDiscount.compareTo(now)==0||endDiscount.compareTo(now)==0){
                sendNewNotification(token,discount);
            }
        }
    }

}
