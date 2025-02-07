package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.entities.user.exceptions.UserFoundException;
import com.project.api_cotacao.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CoinService coinService;
    private final WalletCoinService walletCoinService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CoinService coinService, WalletCoinService walletCoinService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.coinService = coinService;
        this.walletCoinService = walletCoinService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserResponseAllDto> createUser(UserRequestDto request) {
        Optional<UserEntity> userFind = getUserByEmail(request.email());
        if (userFind.isPresent()) {
            throw new UserFoundException("User " + request.email() + " already exists");
        }

        CoinEntity coin = this.coinService.getCoinById(1L);
        String passwordEncoder = this.passwordEncoder.encode(request.password());

        UserEntity newUser = new UserEntity(request, passwordEncoder, coin);
        userRepository.save(newUser);

        this.walletCoinService.createWalletCoin(newUser.getWallet(), coin);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseAllDto.fromEntity(newUser));
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserFoundException("User not found"));
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
