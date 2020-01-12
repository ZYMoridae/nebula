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
@Table(name = "teacher_meta", schema = "public")
public class TeacherMeta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    @Column(name = "intro")
    private String intro;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "speaking_language")
    private String speakingLanguage;

    @Column(name = "certificates")
    private String certificates;

    @Column(name = "student_min_requirements")
    private String studentMinRequirements;

    @Override
    public String toString() {
        return "TeacherMeta{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", intro='" + intro + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
