# copy-ai-reference

![Build](https://github.com/runtime-excpetion/copy-ai-reference/workflows/Build/badge.svg)

> 🇺🇸 Quickly copy file paths and line numbers in a format optimized for AI prompts.
>
> 🇨🇳 一键复制文件路径和行号，格式专为 AI 提示优化。

---

## 🇺🇸 English

### Why?

When working with AI coding assistants (Claude, ChatGPT, etc.), you often need to reference specific files with syntax like `@path/to/file#L10-25`. This plugin makes it a one-click operation — right-click, copy, done.

### Features

- **Project View** — Right-click any file to copy its project-relative path as `@path/to/file.kt`
- **Editor** — Right-click with a text selection to copy the path and line range as `@path/to/file.kt#L5-12`
- Single line selection copies just `@path/to/file.kt#L5`

### Usage

| Context | Action | Result |
|---------|--------|--------|
| Right-click a file in Project View | Copy relative path | `@src/main/Main.kt` |
| Select text in editor → right-click | Copy path + line numbers | `@src/main/Main.kt#L10-25` |

The copied text is ready to paste directly into your AI prompt.

### Installation

**From JetBrains Marketplace** (once published):

<kbd>Settings/Preferences</kbd> → <kbd>Plugins</kbd> → <kbd>Marketplace</kbd> → Search for "copy-ai-reference" → <kbd>Install</kbd>

**Manually from disk:**

Download the [latest release](https://github.com/runtime-excpetion/copy-ai-reference/releases/latest) and install via
<kbd>Settings/Preferences</kbd> → <kbd>Plugins</kbd> → <kbd>⚙️</kbd> → <kbd>Install plugin from disk...</kbd>

---

## 🇨🇳 中文

### 为什么需要这个插件？

在用 AI 编程助手（Claude、ChatGPT 等）时，经常需要在提示词中引用具体文件，格式如 `@path/to/file#L10-25`。这个插件让你一键搞定 —— 右键、复制、粘贴。

### 功能

- **项目视图** — 右键任意文件，复制 `@相对路径/文件名.kt` 格式的引用
- **编辑器** — 选中代码后右键，复制 `@相对路径/文件名.kt#L5-12` 格式的引用（含行号）
- 单行选中则仅输出 `@相对路径/文件名.kt#L5`

### 使用方式

| 操作 | 说明 | 结果 |
|------|------|------|
| 在项目视图中右键文件 | 复制相对路径 | `@src/main/Main.kt` |
| 在编辑器中选中文本后右键 | 复制路径 + 行号 | `@src/main/Main.kt#L10-25` |

复制后的内容可直接粘贴到 AI 提示词中使用。

### 安装

**从 JetBrains Marketplace 安装**（发布后）：

<kbd>设置</kbd> → <kbd>插件</kbd> → <kbd>Marketplace</kbd> → 搜索 "copy-ai-reference" → <kbd>安装</kbd>

**手动安装：**

下载[最新发布包](https://github.com/runtime-excpetion/copy-ai-reference/releases/latest)，通过
<kbd>设置</kbd> → <kbd>插件</kbd> → <kbd>⚙️</kbd> → <kbd>从磁盘安装插件...</kbd> 安装

---

## Build from source / 从源码构建

```bash
./gradlew buildPlugin
# Output: build/distributions/copy-ai-reference-*.zip
```

## Compatibility / 兼容性

- **IDE**: IntelliJ IDEA (2025.2+)
- **Platform**: `com.intellij.modules.platform`

---

Plugin based on the [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template).
