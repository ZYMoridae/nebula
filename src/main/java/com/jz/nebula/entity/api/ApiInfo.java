package com.jz.nebula.entity.api;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "api_info", schema = "public")
public class ApiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "api_category_id")
    private Long apiCategoryId;

    @OneToOne
    @JoinColumn(name = "api_category_id", updatable = false, insertable = false)
    private ApiCategory apiCategory;

    private String request;

    private String response;

    @Column(name = "end_point")
    private String endPoint;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "api_info_id", nullable = false)
    private Set<ApiInfoHeader> apiInfoHeaders;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Date updatedAt;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Set<ApiInfoHeader> getApiInfoHeaders() {
        return apiInfoHeaders;
    }

    public void setApiInfoHeaders(Set<ApiInfoHeader> apiInfoHeaders) {
        this.apiInfoHeaders = apiInfoHeaders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getApiCategoryId() {
        return apiCategoryId;
    }

    public void setApiCategoryId(Long apiCategoryId) {
        this.apiCategoryId = apiCategoryId;
    }

    public ApiCategory getApiCategory() {
        return apiCategory;
    }

    public void setApiCategory(ApiCategory apiCategory) {
        this.apiCategory = apiCategory;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
