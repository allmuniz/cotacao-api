package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.entities.user.exceptions.UserFoundException;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final CoinService coinService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, WalletService walletService, CoinService coinService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.coinService = coinService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserResponseAllDto> createUser(UserRequestDto request) {
        Optional<UserEntity> userFind = getUserByEmail(request.email());
        if (userFind.isPresent()) {
            throw new UserFoundException("User " + request.email() + " already exists");
        }

        String passwordEncoder = this.passwordEncoder.encode(request.password());
        UserEntity newUser = new UserEntity(request, passwordEncoder);

        userRepository.save(newUser);

        WalletEntity wallet = this.walletService.createWallet(newUser);

        List<CoinEntity> coins = coinService.getAllCoinsToWallet(wallet);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseAllDto.fromEntity(newUser, coins));
    }

    public ResponseEntity<UserResponseAllDto> findUser (Long userId) {
        UserEntity user = getUserById(userId);
        List<CoinEntity> coins = coinService.getAllCoinsToUser(user);
        return ResponseEntity.ok(UserResponseAllDto.fromEntity(user, coins));
    }

    public void updateUser(UserEntity user, UserRequestDto request) {
        String passwordEncoder = this.passwordEncoder.encode(request.password());

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder);

        userRepository.save(user);
    }

    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserFoundException("User not found"));
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
