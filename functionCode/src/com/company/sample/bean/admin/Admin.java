package com.company.sample.bean.admin;

import java.util.function.Function;

public class Admin implements Function<String, String> {
    private String type;
    private String priseId;
    private String TeamId;

    public void setEnterpriseType(String type) {
        this.type = type;
    }
    public void setEnterpriseId(String priseId) {
        this.priseId = priseId;
    }
    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }
    public String from() {
        return "from";
    }
    @Override
    public String apply(String t) {
        return "admin ";
    }
}
