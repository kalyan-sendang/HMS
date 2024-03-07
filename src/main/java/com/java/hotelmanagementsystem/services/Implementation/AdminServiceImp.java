package com.java.hotelmanagementsystem.services.Implementation;

import com.java.hotelmanagementsystem.models.Role;
import com.java.hotelmanagementsystem.models.TokenType;
import com.java.hotelmanagementsystem.repositories.RoleRepository;
import com.java.hotelmanagementsystem.repositories.TokenTypeRepository;
import com.java.hotelmanagementsystem.services.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private final RoleRepository roleRepository;
    private final TokenTypeRepository tokenTypeRepository;

    public AdminServiceImp(RoleRepository roleRepository, TokenTypeRepository tokenTypeRepository) {
        this.roleRepository = roleRepository;
        this.tokenTypeRepository = tokenTypeRepository;
    }
    public Role addRole(String role){
        Role.RoleEnum roleEnum = Role.RoleEnum.valueOf(role);
        Role newRole = new Role(roleEnum);
        return roleRepository.save(newRole);
    }
    public TokenType addTokenType(String tokenType){
        TokenType.TokenTypeEnum tokenTypeEnum = TokenType.TokenTypeEnum.valueOf(tokenType);
        TokenType type = new TokenType(tokenTypeEnum);
        return tokenTypeRepository.save(type);

    }

}
