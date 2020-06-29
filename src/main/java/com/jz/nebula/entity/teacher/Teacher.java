package com.jz.nebula.entity.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "teacher_info", schema = "public")
public class Teacher extends User implements Serializable {

    //    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "id", nullable = false, updatable = false, insertable = false)
    @Transient
    private TeacherMeta teacherMeta;
}
