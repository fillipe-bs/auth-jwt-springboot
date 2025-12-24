package io.fillipe.auth_api.service;

import io.fillipe.auth_api.exception.InvalidPasswordException;
import io.fillipe.auth_api.exception.UserNotFoundException;
import io.fillipe.auth_api.model.User;
import io.fillipe.auth_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    //Injeção de dependências da camada de serviço

    private final UserRepository userRepository; //Repositório para acesse e manipular dados no banco
    private final BCryptPasswordEncoder passwordEncoder; //Para criptografar as senhas antes de salvar no banco

    // Construtor
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Funções
    // 1. Registrar usuário
    public User register(User user) {
        //Para criptografar a senha:
        //Pega senha fornecida pelo usuário com get, criptografa com encode e atualiza com set
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Para salvar no banco
        return userRepository.save(user);
    }


    // 2. Buscar usuário pelo email
    public User getByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return optionalUser.get();
    }

    //3. Autenticar/login
    public User authenticate(String email, String rawPassword) {
        //Buscar usuário por email
        Optional<User> optionalUser = userRepository.findByEmail(email);

        //Verificar se existe usuário
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        //Extrair o usuário de dentro do Optional
        User user = optionalUser.get();

        //Verificar se a senha informada corresponde com a senha salva (has)
        boolean passwordMatches = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!passwordMatches){
            throw new InvalidPasswordException("Invalid password");
        }

        //Retorno se login for válido
        return user;
    }

    //4. Buscar usuário
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    //5. Atualizar senha
    public User updatePassword(User user, String oldPassword, String newPassword){
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid current password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }


}
