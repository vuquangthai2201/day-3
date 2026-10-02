package lab1.domain;

import java.math.BigDecimal;

public class WalletDemo {
    public static void main(String[] args) {
        WalletEntity w = WalletEntity.open(new BigDecimal("100"));
        w.withdrawMoney(new BigDecimal("40"));
        System.out.println("Còn lại: " + w.getBalance()); // 60

        try { w.withdrawMoney(new BigDecimal("500")); }
        catch (DomainException e) { System.out.println(e.getMessage()); }

        w.lockWallet();
        try { w.withdrawMoney(new BigDecimal("10")); }
        catch (DomainException e) { System.out.println(e.getMessage()); }
    }
}
