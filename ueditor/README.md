#### Spring  Boot 集成 百度富文本编辑（Ueditor），使用Jquery Validate 验证框架验证

- 知识点
  - Spring Boot集成Ueditor
  - 图片上传到本地磁盘在线显示（添加静态资源映射到本地磁盘:`WebMvcConfig.addResourceHandlers`）
  - 修改Ueditor源码兼容Jquery Validate验证框架

- 集成要点

  - 修改`ueditor.config.js`文件window.UEDITOR_CONFIG.serverUrl为`/ueditor/ueditorConfig`并注释下面的配置项,此路径对应后台加载配置项路径，详见`PublicMsg`类。
  - 修改`ueditor.js`文件UE.Editor.prototype.getActionUrl为上传图片返回值为`/ueditor/imgUpload`，此路径对应后台加载配置项路径。
  - `PublicMsg`中`UeditorMsg`枚举类，返回成功结果必须为`SUCCESS`。
  - 修改`UeditorController`中`imgUpload`方法，此方法为实际接收前台上传文件方法，上传到本地或OSS对应修改即可。

- 兼容Jquery Validate验证框架

  - 原因：Jquery Validate验证框架默认不验证hidden属性的标签，而富文本编辑器会将textarea隐藏，此处冲突了。

  - 两种修改方式

    - 修改验证框架验证hidden，`jquery.validate.js`文件252行`ignore`属性值为[],即可；但这样修改会影响验证框架的全局属性，会有一些小问题，不建议。

    - 修改ueditor方式源码，让框架可验证ueditor，步骤如下：

      - 修改`ueditor.all.min.js`文件93行（或搜索`ueditor.all.min.js`）,将`display:block;`改为`display:block;opacity:0`。

      - 修改`jquery.validate.js`文件804行代码为(提示方式可自定义为其他方式)：

        ```javascript
        // 富文本编辑器错误 暂定弹框提示
        if (place.get(0).id.indexOf("ueditor_textarea_") != -1 && place.html() != ""){
          alert("JQuery.validate:富文本编辑器内容必填！");
        }else{
          place.insertAfter( element );
        }
        ```

        


