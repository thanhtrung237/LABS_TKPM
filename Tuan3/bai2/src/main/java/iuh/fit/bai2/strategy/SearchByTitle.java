package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Book;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Strategy for searching books by title
 */
public class SearchByTitle implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String searchTerm) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
}