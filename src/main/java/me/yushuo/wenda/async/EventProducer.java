package me.yushuo.wenda.async;

import com.alibaba.fastjson.JSON;
import me.yushuo.wenda.util.JedisUtil;
import me.yushuo.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisUtil jedisUtil;

    public boolean fireEvent(EventModel eventModel) {
        try{
            String key = RedisKeyUtil.getEventQueue();
            String value = JSON.toJSONString(eventModel);
            jedisUtil.lpush(key, value);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
