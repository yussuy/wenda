package me.yushuo.wenda.async;

import me.yushuo.wenda.model.EntityType;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EntityType type;
    private int actorId;
    private int entityType;
    private int entityId;
    private int eventOwnerId;

    private Map<String, String> exts = new HashMap<>();

    public void setExt(String key, String value) {
        exts.put(key, value);
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EntityType getType() {
        return type;
    }

    public EventModel setType(EntityType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEventOwnerId() {
        return eventOwnerId;
    }

    public EventModel setEventOwnerId(int eventOwnerId) {
        this.eventOwnerId = eventOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
