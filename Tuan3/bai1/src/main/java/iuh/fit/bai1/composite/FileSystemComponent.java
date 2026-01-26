package iuh.fit.bai1.composite;

import java.util.Date;

/**
 * Abstract component trong Composite Pattern
 * Định nghĩa interface chung cho File và Directory
 */
public abstract class FileSystemComponent {
    protected String name;
    protected Date lastModified;
    
    public FileSystemComponent(String name) {
        this.name = name;
        this.lastModified = new Date();
    }
    
    public String getName() {
        return name;
    }
    
    public Date getLastModified() {
        return lastModified;
    }
    
    // Abstract methods
    public abstract void display(int depth);
    public abstract long getSize();
    
    // Default implementations for composite operations
    public void add(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add to a file");
    }
    
    public void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a file");
    }
    
    public FileSystemComponent getChild(int index) {
        throw new UnsupportedOperationException("Cannot get child from a file");
    }
    
    protected String getIndent(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        return indent.toString();
    }
}