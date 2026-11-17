package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Notice {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Lob
    private String content;

    @CreationTimestamp
    private Date creationTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(@NotNull @NotEmpty String content) {
        this.content = content;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public @NotNull @NotEmpty String getContent() {
        return content;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
