package br.com.jfood.dto;

public class UserDTO {
    private String name;
    private String cpf;
    private String email;
    private int role;

    public UserDTO(String name, String cpf, String email, int role) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }
}
