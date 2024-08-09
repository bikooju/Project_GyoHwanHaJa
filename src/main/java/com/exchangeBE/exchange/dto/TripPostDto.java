package com.exchangeBE.exchange.dto;

import com.exchangeBE.exchange.entity.TopicEntity;
import java.time.LocalDate;
import java.util.List;

public class TripPostDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> photos;
    private TopicEntity topic;

    // 기본 생성자 (no-args constructor)
    public TripPostDto() {}

    // 모든 필드를 포함한 생성자 (MainServiceTest에 필요한 생성자)
    public TripPostDto(Long id, String title, String description, LocalDate startDate, LocalDate endDate, List<String> photos, TopicEntity topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.photos = photos;
        this.topic = topic;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }
}
