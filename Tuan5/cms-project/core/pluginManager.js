// core/pluginManager.js
// Microkernel: core nhỏ, tất cả tính năng là plugin

class PluginManager {
  constructor() {
    this.plugins = {};
    this.hooks = {};
  }

  // Đăng ký plugin vào core
  register(name, plugin) {
    if (this.plugins[name]) {
      console.warn(`Plugin "${name}" đã tồn tại, ghi đè...`);
    }
    this.plugins[name] = plugin;

    // Tự động đăng ký hooks nếu plugin có khai báo
    if (plugin.hooks) {
      for (const [hook, fn] of Object.entries(plugin.hooks)) {
        if (!this.hooks[hook]) this.hooks[hook] = [];
        this.hooks[hook].push(fn);
      }
    }

    console.log(`✅ Plugin "${name}" đã được đăng ký`);
  }

  // Gọi plugin theo tên
  run(name, context = {}) {
    if (!this.plugins[name]) {
      throw new Error(`❌ Plugin "${name}" không tồn tại`);
    }
    return this.plugins[name].execute(context);
  }

  // Gọi tất cả plugin đăng ký vào một hook
  applyHook(hookName, data) {
    if (!this.hooks[hookName]) return data;
    return this.hooks[hookName].reduce((acc, fn) => fn(acc), data);
  }

  listPlugins() {
    return Object.keys(this.plugins);
  }
}

module.exports = new PluginManager();
