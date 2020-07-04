package com.jz.nebula.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

@Entity
@Data
@Table(name = "notification", schema = "public")
public class Notification implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5928329494837900154L;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    NotificationStatus notificationStatus;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "status_id")
    private Long statusId;

    private String body;

}
