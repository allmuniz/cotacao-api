package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.entities.user.exceptions.UserFoundException;
import com.project.api_cotacao.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CoinService coinService;
    private final WalletCoinService walletCoinService;

    public UserService(UserRepository userRepository, CoinService coinService, WalletCoinService walletCoinService) {
        this.userRepository = userRepository;
        this.coinService = coinService;
        this.walletCoinService = walletCoinService;
    }

    public ResponseEntity<UserResponseAllDto> createUser(UserRequestDto request) {

        UserEntity user = getUserByEmail(request.email());
        if (user != null) throw new UserFoundException("User already exists");

        CoinEntity coin = this.coinService.getCoinById(1L);

        UserEntity newUser = new UserEntity(request, coin);
        userRepository.save(newUser);

        this.walletCoinService.createWalletCoin(newUser.getWallet(), coin);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseAllDto.fromEntity(newUser));
    }

    /*public ResponseEntity<UserResponseBalanceDto> updatePrincipalBalance(Long id) {
        UserEntity user = getUserById(id);


    }*/

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserFoundException("User not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
