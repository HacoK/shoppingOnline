package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.service.TransService;

import java.util.HashMap;
import java.util.Map;

public class RepoFactory {
    private final static RepoFactory repoFactory=new RepoFactory();

    private Map<String, Object> repoMap;

    private RepoFactory(){
        repoMap = new HashMap<String, Object>();
        repoMap.put("UserRepo",new UserRepo());
        repoMap.put("OrderRepo",new OrderRepo());
        repoMap.put("GoodsRepo",new GoodsRepo());
        repoMap.put("TransService",new TransService());
    }

    public void registerInstance(String key, Object instance) {
        if (!repoMap.containsKey(key)) {
            repoMap.put(key,instance);
        }
    }

    public Object getInstance(String key) {
        return repoMap.get(key);
    }

    public static RepoFactory getFactory() {
        return repoFactory;
    }


}
