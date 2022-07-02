package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solution")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long dbId;

    @Column
    private long id;
    @Column
    @JsonIgnore
    private String completedBy;

    @Column
    private LocalDateTime completedAt;

    public Solution() {
    }

    public Solution(Long quizId, String completedBy, LocalDateTime completedAt) {
        this.id = quizId;
        this.completedBy = completedBy;
        this.completedAt = completedAt;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long id) {
        this.dbId = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long quizId) {
        this.id = quizId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
