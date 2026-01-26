package iuh.fit.bai1.adapter;

/**
 * Target interface trong Adapter Pattern
 * Interface mà client mong đợi (xử lý JSON)
 */
public interface DataProcessor {
    String processData(String data);
}