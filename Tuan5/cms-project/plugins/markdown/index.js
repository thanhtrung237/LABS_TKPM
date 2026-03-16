// plugins/markdown/index.js
// Plugin Markdown: convert nội dung markdown → HTML đơn giản

module.exports = {
  name: "markdown",
  version: "1.0.0",

  execute(context) {
    const { content = "" } = context;

    const html = content
      .replace(/^### (.+)$/gm, "<h3>$1</h3>")
      .replace(/^## (.+)$/gm, "<h2>$1</h2>")
      .replace(/^# (.+)$/gm, "<h1>$1</h1>")
      .replace(/\*\*(.+?)\*\*/g, "<strong>$1</strong>")
      .replace(/\*(.+?)\*/g, "<em>$1</em>")
      .replace(/`(.+?)`/g, "<code>$1</code>")
      .replace(/\n\n/g, "</p><p>")
      .replace(/^(?!<[h|p])(.+)$/gm, "<p>$1</p>");

    return { ...context, html };
  },

  // Hook: chạy trước khi trả về post
  hooks: {
    "post:afterLoad": (post) => {
      if (post.content) {
        post.preview = post.content.substring(0, 200) + "...";
      }
      return post;
    },
  },
};
