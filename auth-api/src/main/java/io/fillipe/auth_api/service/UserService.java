package io.fillipe.auth_api.service;

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

    //2. Autenticar/login
    public Optional<User> authenticate(String email, String rawPassword) {
        //Consulta email no banco de dados via userRepository
        //Retorna Optional pq pode existir ou não correspondência na consulta
        Optional<User> userOptional = userRepository.findByEmail(email);
        //Se existir correspondência, compara as senhas via lógica do BCryptPasswordEncoder
        if (userOptional.isPresent() && passwordEncoder.matches(rawPassword, userOptional.get().getPassword())) {
            //Se if = true, então usuário existe, a senha é válida e a autenticação foi bem sucedida
            return userOptional;
        }
        //Caso usuário não exista ou senha inválida
        return Optional.empty();
    }

    //3. Buscar usuário
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    //4. Atualizar senha
    public User updatePassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

}
