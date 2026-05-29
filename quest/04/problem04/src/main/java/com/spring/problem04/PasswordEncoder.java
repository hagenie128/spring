package com.spring.problem04;

public class PasswordEncoder {
    private final String algorithm;

    public PasswordEncoder(String algorithm) {
        this.algorithm = algorithm;
    }

    public String encode(String password) {
        System.out.println("[PasswordEncoder] " + algorithm + " 방식으로 비밀번호 암호화");
        return "encoded-" + password;
    }
}
