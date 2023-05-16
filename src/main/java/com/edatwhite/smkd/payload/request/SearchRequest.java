package com.edatwhite.smkd.payload.request;

public class SearchRequest {
    private Long user_id;
    private String search_value;

    public SearchRequest(Long user_id, String search_value) {
        this.user_id = user_id;
        this.search_value = search_value;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getSearch_value() {
        return search_value;
    }

    public void setSearch_value(String search_value) {
        this.search_value = search_value;
    }
}
