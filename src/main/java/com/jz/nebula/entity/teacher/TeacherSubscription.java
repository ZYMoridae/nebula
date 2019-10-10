package com.jz.nebula.entity.teacher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "teacher_subscription", schema = "public")
public class TeacherSubscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "teacher_id")
    private Long teacherId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "updated_at")
    private Date expiredAt;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
}
