// backend/server.js
// Layer BE: Express API cho CMS

const express = require("express");
const cors = require("cors");
const pluginManager = require("../core/pluginManager");
const seoPlugin = require("../plugins/seo");
const markdownPlugin = require("../plugins/markdown");

// ── Đăng ký plugins vào core ──────────────────────────────
pluginManager.register("seo", seoPlugin);
pluginManager.register("markdown", markdownPlugin);

// ── App setup ─────────────────────────────────────────────
const app = express();
app.use(cors());
app.use(express.json());

// Dữ liệu tạm (thay bằng DB thật khi cần)
let posts = [
  {
    id: 1,
    title: "Bài viết đầu tiên",
    slug: "bai-viet-dau-tien",
    content: "# Hello CMS\n\nĐây là bài viết **đầu tiên** của chúng ta.",
    createdAt: new Date().toISOString(),
  },
];

// ── Routes ────────────────────────────────────────────────

// GET tất cả posts
app.get("/api/posts", (req, res) => {
  const result = posts.map((post) =>
    pluginManager.applyHook("post:afterLoad", { ...post })
  );
  res.json(result);
});

// GET post theo id, kèm render markdown + SEO
app.get("/api/posts/:id", (req, res) => {
  const post = posts.find((p) => p.id === Number(req.params.id));
  if (!post) return res.status(404).json({ error: "Không tìm thấy post" });

  let result = pluginManager.applyHook("post:afterLoad", { ...post });
  result = pluginManager.run("markdown", result); // render markdown
  result = pluginManager.run("seo", result);       // tạo SEO meta

  res.json(result);
});

// POST tạo post mới
app.post("/api/posts", (req, res) => {
  let post = {
    id: Date.now(),
    createdAt: new Date().toISOString(),
    ...req.body,
  };

  // Chạy hook trước khi lưu (ví dụ: tự tạo slug)
  post = pluginManager.applyHook("post:beforeSave", post);

  posts.push(post);
  res.status(201).json(post);
});

// DELETE post
app.delete("/api/posts/:id", (req, res) => {
  const idx = posts.findIndex((p) => p.id === Number(req.params.id));
  if (idx === -1) return res.status(404).json({ error: "Không tìm thấy post" });
  posts.splice(idx, 1);
  res.json({ success: true });
});

// GET danh sách plugins đang chạy
app.get("/api/plugins", (req, res) => {
  res.json({ plugins: pluginManager.listPlugins() });
});

// ── Start ─────────────────────────────────────────────────
if (require.main === module) {
  app.listen(3001, () => {
    console.log("🚀 BE đang chạy tại http://localhost:3001");
    console.log("📦 Plugins:", pluginManager.listPlugins());
  });
}

module.exports = app;
