package com.pay.fakepay.entity.dto;

public class SystemUserGroupDTO {
    private Long id;
    
    private String username;
    private String groupname;

    public SystemUserGroupDTO(Long id, String username, String groupname) {
        this.id = id;
        this.username = username;
        this.groupname = groupname;
    }
    
    public SystemUserGroupDTO(String username, String groupname) {
        this(null, username, groupname);
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getGroupname() {
        return groupname;
    }
     
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
