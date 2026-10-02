package lab1.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/** Aggregate Root: mọi thay đổi trạng thái chỉ đi qua các phương thức nghiệp vụ. */
public class WalletEntity {

    private final UUID id;
    private BigDecimal balance;
    private WalletStatus status;

    private WalletEntity(UUID id, BigDecimal balance, WalletStatus status) {
        this.id = id;
        this.balance = balance;
        this.status = status;
    }

    /** Factory: tạo ví mới luôn ở trạng thái hợp lệ (ACTIVE, số dư >= 0). */
    public static WalletEntity open(BigDecimal initialBalance) {
        Objects.requireNonNull(initialBalance, "initialBalance");
        if (initialBalance.signum() < 0) {
            throw new DomainException("Số dư ban đầu không được âm!");
        }
        return new WalletEntity(UUID.randomUUID(), initialBalance, WalletStatus.ACTIVE);
    }

    /** Dùng cho Repository/Mapper khi dựng lại aggregate từ DB. */
    public static WalletEntity restore(UUID id, BigDecimal balance, WalletStatus status) {
        if (balance.signum() < 0) {
            throw new DomainException("Dữ liệu không hợp lệ: số dư âm!");
        }
        return new WalletEntity(id, balance, status);
    }

    public void withdrawMoney(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount");
        if (amount.signum() <= 0) {
            throw new DomainException("Số tiền rút phải lớn hơn 0!");
        }
        if (status == WalletStatus.LOCKED) {
            throw new DomainException("Ví điện tử hiện đang bị khóa!");
        }
        if (balance.compareTo(amount) < 0) {
            throw new DomainException("Số dư không đủ để thực hiện giao dịch!");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void lockWallet() {
        this.status = WalletStatus.LOCKED; // idempotent: khóa nhiều lần không lỗi
    }

    // Chỉ có getter, không có setter công khai
    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public WalletStatus getStatus() { return status; }
}
