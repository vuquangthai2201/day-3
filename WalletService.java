package lab1.domain;

import java.math.BigDecimal;

/** Application Service mỏng: điều phối, không chứa luật nghiệp vụ. */
public class WalletService {

    public void withdraw(WalletEntity wallet, BigDecimal amount) {
        wallet.withdrawMoney(amount);
        // TODO: walletRepository.save(wallet);
    }
}
