package com.gmail.ascanada52.conference.room.dto;

import java.util.List;

public class ChatWithParticipants {

    private Integer id;
    private Integer owner;
    private String name;
    private List<Integer> participants;

    public ChatWithParticipants() {

    }

    public ChatWithParticipants(Integer owner, String name, List<Integer> participants) {
        super();
        this.owner = owner;
        this.name = name;
        this.participants = participants;
    }

    public ChatWithParticipants(Integer id, Integer owner, String name, List<Integer> participants) {
        super();
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

}
