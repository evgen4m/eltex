package com.esoft.eltex.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {

    @SerializedName("roleId")
    private String roleId = null;

    @SerializedName("username")
    private String username = null;

    @SerializedName("email")
    private String email = null;

    @SerializedName("permissions")
    private List<String> listPermissions = null;

    public String getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getListPermissions() {
        return listPermissions;
    }
}
