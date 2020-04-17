package ru.voskhod.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPageDto {

    private int page;

    @JsonProperty("per_page")
    private  int perPage;

    private long total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<UserDto> users;

    public int getPage() {
        return page;
    }

    public UserPageDto setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPerPage() {
        return perPage;
    }

    public UserPageDto setPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public UserPageDto setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public UserPageDto setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    @JsonProperty("users")
    public List<UserDto> getUsers() {
        return users;
    }

    @JsonProperty("data")
    public UserPageDto setUsers(List<UserDto> users) {
        this.users = users;
        return this;
    }
}
