// backend/server.test.js
const request = require("supertest");
const app = require("./server");

describe("CMS API - Layer Tests", () => {
  // ── GET /api/posts ──────────────────────────────────────
  test("GET /api/posts trả về mảng post", async () => {
    const res = await request(app).get("/api/posts");
    expect(res.statusCode).toBe(200);
    expect(Array.isArray(res.body)).toBe(true);
    expect(res.body.length).toBeGreaterThan(0);
  });

  // ── POST /api/posts ─────────────────────────────────────
  test("POST /api/posts tạo post mới thành công", async () => {
    const newPost = {
      title: "Test Post",
      content: "Nội dung test **markdown**",
    };
    const res = await request(app).post("/api/posts").send(newPost);
    expect(res.statusCode).toBe(201);
    expect(res.body.title).toBe("Test Post");
    // Kiểm tra hook SEO đã tạo slug tự động
    expect(res.body.slug).toBe("test-post");
  });

  // ── GET /api/posts/:id ──────────────────────────────────
  test("GET /api/posts/1 trả về đủ dữ liệu kèm markdown và SEO", async () => {
    const res = await request(app).get("/api/posts/1");
    expect(res.statusCode).toBe(200);
    expect(res.body).toHaveProperty("html");       // markdown plugin
    expect(res.body).toHaveProperty("seo");        // SEO plugin
    expect(res.body.seo.title).toContain("My CMS");
  });

  // ── GET /api/posts/:id not found ────────────────────────
  test("GET /api/posts/9999 trả về 404", async () => {
    const res = await request(app).get("/api/posts/9999");
    expect(res.statusCode).toBe(404);
  });

  // ── Plugin list ─────────────────────────────────────────
  test("GET /api/plugins liệt kê đúng plugin đang chạy", async () => {
    const res = await request(app).get("/api/plugins");
    expect(res.statusCode).toBe(200);
    expect(res.body.plugins).toContain("seo");
    expect(res.body.plugins).toContain("markdown");
  });
});
