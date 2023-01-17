package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.User;

public class UserDto {
    private String username;
    private String role;

    static public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();

        userDto.username = user.getUsername();
        userDto.role = user.getRole();
        return userDto;
    }

    public UserDto() {
        // Para jersey
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
