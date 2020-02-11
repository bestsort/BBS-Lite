module.exports = {
  plugins: {
    // 更多方式可参考:
    // https://v1.vuepress.vuejs.org/zh/plugin/using-a-plugin.html
    "vuepress-plugin-auto-sidebar": {
    } // 可参考下方的 “可选项”
  },
  displayAllHeaders: true,
  base: '/BBS-Lite/',
  themeConfig: {
    nav: [
      { text: 'Home', link: '/' },
      { text: '博客', link: 'https://bestsort.cn' },
      { text: 'GitHub', link: 'https://github.com/bestsort/BBS-Lite' },
    ]
  }
}

