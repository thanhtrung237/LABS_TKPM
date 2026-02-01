package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Book;
import java.util.List;

/**
 * Strategy interface for book search
 */
public interface SearchStrategy {
    List<Book> search(List<Book> books, String searchTerm);
}