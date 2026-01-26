package iuh.fit.bai1.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite component trong Composite Pattern
 * Đại diện cho một thư mục có thể chứa File hoặc Directory khác
 */
public class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children;
    
    public Directory(String name) {
        super(name);
        this.children = new ArrayList<>();
    }
    
    @Override
    public void add(FileSystemComponent component) {
        children.add(component);
        this.lastModified = new java.util.Date();
    }
    
    @Override
    public void remove(FileSystemComponent component) {
        children.remove(component);
        this.lastModified = new java.util.Date();
    }
    
    @Override
    public FileSystemComponent getChild(int index) {
        if (index >= 0 && index < children.size()) {
            return children.get(index);
        }
        return null;
    }
    
    @Override
    public void display(int depth) {
        System.out.println(getIndent(depth) + "📁 " + name + "/ (" + children.size() + " items)");
        for (FileSystemComponent child : children) {
            child.display(depth + 1);
        }
    }
    
    @Override
    public long getSize() {
        long totalSize = 0;
        for (FileSystemComponent child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
    
    public int getChildCount() {
        return children.size();
    }
    
    public List<FileSystemComponent> getChildren() {
        return new ArrayList<>(children);
    }
    
    @Override
    public String toString() {
        return "Directory{name='" + name + "', children=" + children.size() + "}";
    }
}