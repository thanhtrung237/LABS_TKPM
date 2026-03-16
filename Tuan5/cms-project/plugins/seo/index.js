// plugins/seo/index.js
// Plugin SEO: tự động tạo meta tags cho post

module.exports = {
  name: "seo",
  version: "1.0.0",

  execute(context) {
    const { title = "", content = "", slug = "" } = context;
    const description = content.substring(0, 160).replace(/\n/g, " ");

    return {
      ...context,
      seo: {
        title: `${title} | My CMS`,
        description,
        canonical: `https://mycms.com/posts/${slug}`,
        og: {
          title,
          description,
          type: "article",
        },
      },
    };
  },

  // Hook: tự động chạy khi post được tạo
  hooks: {
    "post:beforeSave": (post) => {
      if (!post.slug && post.title) {
        post.slug = post.title
          .toLowerCase()
          .replace(/\s+/g, "-")
          .replace(/[^a-z0-9-]/g, "");
      }
      return post;
    },
  },
};
