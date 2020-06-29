package com.jz.nebula.entity.job;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jz.nebula.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "job", schema = "public")
@Getter
@Setter
public class Job implements Serializable {

    public String title;
    public String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User user;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", userId=" + userId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
