package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<CoinEntity, Long> {

    List<CoinEntity> findAllByWallet(WalletEntity wallet);

    @Query("SELECT c FROM CoinEntity c WHERE c.wallet.user.id = :userId")
    List<CoinEntity> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM CoinEntity c WHERE c.wallet.user = :user AND c.notification = :notification")
    List<CoinEntity> findAllByUserAndNotification(@Param("user") UserEntity user, @Param("notification") Boolean notification);

    CoinEntity findByWalletAndCode(WalletEntity wallet, String code);

    @Query("SELECT c FROM CoinEntity c WHERE c.wallet = :wallet AND c.isPrincipal = true")
    Optional<CoinEntity> findPrincipalCoinByWallet(@Param("wallet") WalletEntity wallet);

}
