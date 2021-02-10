/*
 * author: Garrett
 * date: 2/8/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

public class Job {
    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;
    private String how_to_apply;
    private String company_logo;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCompany() {
        return company;
    }

    public String getCompany_url() {
        return company_url;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHow_to_apply () {
        return how_to_apply;
    }

    public String getCompany_logo () {
        return company_logo;
    }
}
