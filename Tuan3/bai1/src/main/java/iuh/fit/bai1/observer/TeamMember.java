package iuh.fit.bai1.observer;

/**
 * Concrete Observer - Thành viên nhóm theo dõi trạng thái task
 */
public class TeamMember implements Observer {
    private String name;
    private String role;
    
    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
    @Override
    public void update(Subject subject) {
        if (subject instanceof TaskStatus) {
            TaskStatus task = (TaskStatus) subject;
            System.out.println("👥 " + role + " " + name + " nhận thông báo:");
            System.out.println("   Task: " + task.getTaskName());
            System.out.println("   Trạng thái mới: " + task.getStatus());
            System.out.println("   Người thực hiện: " + task.getAssignee());
            
            // Phản ứng theo role
            switch (task.getStatus().toLowerCase()) {
                case "completed":
                    System.out.println("   ✅ Phản hồi: Tuyệt vời! Task đã hoàn thành.");
                    break;
                case "in progress":
                    System.out.println("   Phản hồi: Đang theo dõi tiến độ...");
                    break;
                case "blocked":
                    System.out.println("   Phản hồi: Cần hỗ trợ để giải quyết vấn đề!");
                    break;
                default:
                    System.out.println("   Phản hồi: Đã ghi nhận thay đổi.");
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
}