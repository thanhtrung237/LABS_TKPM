package iuh.fit.bai1.composite;

/**
 * Leaf component trong Composite Pattern
 * Đại diện cho một tập tin trong hệ thống
 */
public class File extends FileSystemComponent {
    private String content;
    private long size;
    
    public File(String name, String content) {
        super(name);
        this.content = content;
        this.size = content.length();
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
        this.size = content.length();
        this.lastModified = new java.util.Date();
    }
    
    @Override
    public void display(int depth) {
        System.out.println(getIndent(depth) + "📄 " + name + " (" + size + " bytes)");
    }
    
    @Override
    public long getSize() {
        return size;
    }
    
    @Override
    public String toString() {
        return "File{name='" + name + "', size=" + size + " bytes}";
    }
}