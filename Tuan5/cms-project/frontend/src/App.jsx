// frontend/src/App.jsx
import { useState, useEffect } from "react";

const API = import.meta.env.VITE_API_URL || "";

export default function App() {
  const [posts, setPosts] = useState([]);
  const [selected, setSelected] = useState(null);
  const [form, setForm] = useState({ title: "", content: "" });
  const [loading, setLoading] = useState(false);
  const [plugins, setPlugins] = useState([]);

  // Load danh sách posts
  useEffect(() => {
    fetch(`${API}/api/posts`)
      .then((r) => r.json())
      .then(setPosts);

    fetch(`${API}/api/plugins`)
      .then((r) => r.json())
      .then((d) => setPlugins(d.plugins));
  }, []);

  // Xem chi tiết post (có markdown + SEO)
  const viewPost = async (id) => {
    setLoading(true);
    const res = await fetch(`${API}/api/posts/${id}`);
    const data = await res.json();
    setSelected(data);
    setLoading(false);
  };

  // Tạo post mới
  const createPost = async (e) => {
    e.preventDefault();
    const res = await fetch(`${API}/api/posts`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });
    const newPost = await res.json();
    setPosts((prev) => [...prev, newPost]);
    setForm({ title: "", content: "" });
  };

  // Xoá post
  const deletePost = async (id) => {
    await fetch(`${API}/api/posts/${id}`, { method: "DELETE" });
    setPosts((prev) => prev.filter((p) => p.id !== id));
    if (selected?.id === id) setSelected(null);
  };

  return (
    <div style={{ fontFamily: "sans-serif", maxWidth: 900, margin: "0 auto", padding: 24 }}>
      <h1>🗂️ CMS Dashboard</h1>

      {/* Plugin status */}
      <div style={{ background: "#f0f9ff", borderRadius: 8, padding: "8px 16px", marginBottom: 24, fontSize: 13 }}>
        <strong>Plugins đang chạy:</strong>{" "}
        {plugins.map((p) => (
          <span key={p} style={{ background: "#0ea5e9", color: "#fff", borderRadius: 4, padding: "2px 8px", marginLeft: 6 }}>
            {p}
          </span>
        ))}
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 24 }}>
        {/* Danh sách posts */}
        <div>
          <h2>📄 Danh sách bài viết</h2>
          {posts.map((post) => (
            <div key={post.id} style={{ border: "1px solid #e2e8f0", borderRadius: 8, padding: 12, marginBottom: 8 }}>
              <strong>{post.title}</strong>
              {post.preview && <p style={{ fontSize: 12, color: "#64748b", margin: "4px 0" }}>{post.preview}</p>}
              <div style={{ marginTop: 8, display: "flex", gap: 8 }}>
                <button onClick={() => viewPost(post.id)} style={{ fontSize: 12, padding: "4px 10px", cursor: "pointer" }}>
                  Xem chi tiết
                </button>
                <button onClick={() => deletePost(post.id)} style={{ fontSize: 12, padding: "4px 10px", cursor: "pointer", color: "#ef4444" }}>
                  Xoá
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Form tạo post */}
        <div>
          <h2>✏️ Tạo bài viết mới</h2>
          <form onSubmit={createPost} style={{ display: "flex", flexDirection: "column", gap: 12 }}>
            <input
              placeholder="Tiêu đề"
              value={form.title}
              onChange={(e) => setForm({ ...form, title: e.target.value })}
              required
              style={{ padding: 8, borderRadius: 6, border: "1px solid #cbd5e1" }}
            />
            <textarea
              placeholder="Nội dung (hỗ trợ Markdown: # H1, **bold**, *italic*)"
              value={form.content}
              onChange={(e) => setForm({ ...form, content: e.target.value })}
              rows={6}
              required
              style={{ padding: 8, borderRadius: 6, border: "1px solid #cbd5e1", resize: "vertical" }}
            />
            <button type="submit" style={{ padding: "8px 16px", background: "#3b82f6", color: "#fff", border: "none", borderRadius: 6, cursor: "pointer" }}>
              Tạo bài viết
            </button>
          </form>
        </div>
      </div>

      {/* Chi tiết post */}
      {selected && (
        <div style={{ marginTop: 32, border: "1px solid #e2e8f0", borderRadius: 8, padding: 24 }}>
          <h2>🔍 Chi tiết: {selected.title}</h2>
          <div style={{ background: "#f8fafc", padding: 12, borderRadius: 6, marginBottom: 16 }}>
            <strong>SEO (từ plugin):</strong>
            <div style={{ fontSize: 12, marginTop: 4 }}>
              <div>Title: {selected.seo?.title}</div>
              <div>Slug: {selected.slug}</div>
              <div>Description: {selected.seo?.description?.substring(0, 80)}...</div>
            </div>
          </div>
          <div>
            <strong>Nội dung đã render (Markdown → HTML):</strong>
            <div
              style={{ marginTop: 8, padding: 12, border: "1px solid #e2e8f0", borderRadius: 6 }}
              dangerouslySetInnerHTML={{ __html: selected.html }}
            />
          </div>
        </div>
      )}
    </div>
  );
}
