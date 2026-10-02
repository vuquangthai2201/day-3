package lab1.domain;

/** Ngoại lệ nghiệp vụ: ném ra khi vi phạm luật bất biến (invariant) của domain. */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
