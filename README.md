# Java课程项目

本项目为Java课程第四小组的课程期末项目。

项目要求使用Java语言依据git原理实现部分git命令的版本控制工具



## Git实现原理

### Object类型

- #### Blob：单个文件

  - key：根据文件内容生成的哈希值

  - value：文件内容

- #### Tree：文件夹

  - key：根据Tree存储的子结构的value生成的哈希值
  - value：Tree的子结构类型（Tree或Blob）；Tree的子结构对象的哈希值；文件名或文件夹名

- #### Commit：提交

  - key：根据Commit的value生成的哈希值
  - value：Commit的根目录Tree对象的哈希值；上次Commit对应的哈希值



## 功能简介

### 基础功能

- Key-Value存储结构

- Git基本数据结构：Blob, Tree, Commit

### 分支管理

- 切换分支

- 分支回滚

- 分支合并

### 文件比对

- Diff

### 命令交互

- 控制台输入命令实现交互



## 项目实现

### Key-Value存储结构

- 对象内容作为value，内容对应的哈希值作为key

- Key和Value作为三种Object类型的属性被封装在对应的类中

  - Key和Value均在对应的类中提供对外访问接口
  - 给定Key值可返回对应的Value值

  

### 将文件转化为Blob

- 给定文件所在路径，可读取并将文件内容存为字节数组作为Value值，并据此生成对应的Key值

- 提供对应的Key-value访问接口

  

### 将文件夹转化为Tree

- 给定文件夹目录，先生成一棵对应的空树，后续以向其中添加Blob或Tree对象的方式实现转化

- 通过深度优先遍历将其子文件转化为Blob保存，遇到子文件夹则递归调用该方法

- 提供对应的Key-value访问接口

  

### Commit功能

- 给定工作区文件夹对应的Tree对象
  - 如果该Tree对象是首次创建Commit，则以“类型” + “Key值”的形式作为内容，将其存入生成的Commit对象，并据内容生成该Commit对象的Key值，实现Key-Value存储
  - 如果该Tree对象不是首次生成Commit，则除按上述生成内容外，还需提供上次Commit对象的Key值，以“parent” + “Key值”的方式写入本次的内容
  - 利用rootsha的属性来记录所对应根的Key值，方便与新的Commit进行对比，如果相等，则不运行生成文件的函数。

- 逻辑上形成一个链式结构，方便后续的分支切换与回滚操作实现

- 提供对应的Key-value访问接口

  

### 分支管理-回滚

- 首先通过传入的commit的key值找到commit的value存入split字符串数组中，其中每三个构成一组，分别是type，key，name

- 接着通过传入rootkey和文件路径获取文件(文件夹)内容放入字符串数组，进入循环。如果文件类型为tree类型，则调用recoverFileFolder方法并递归调用recover方法，直至遍历文件类型为blob类型，通过key找到value并写入新文件实现回滚。循环直至整个字符串数组遍历一遍后结束

- recoverFile方法通过blob的key获取blob内容，并写入文件，实现回滚

- recoverFileFolder方法可以创建文件夹，在recover方法里使用



### 分支管理-切换分支

- 首先通过head记录的内容获取当前分支的信息，再利用branchChange方法通过分支名创建head分支来实现分支切换

- 可通过获取当前head来得到当前commit所对应的key值
  

### 分支管理-合并

- 首先利用定义的deleteFile方法删除暂存区目录下的文件

- 分别对master当前commit以及当前分支的当前commit进行回滚，在暂存区中生成新的合并文件夹，接着再对master分支进行一次commit操作从而实现合并

  

### 内容比对

- 通过求两个文件内容的LCS（最长公共子序列），来实现尽量减少加号与减号的出现
- 首先将两次传入的文件内容分别读入两个字符串数组，标记为old和new
- 接着求两个字符串数组的LCS，并分别将其在两个数组中的对应位置记录并返回
- 在最终的diff方法中，将两个数组中属于LCS的内容做特定标记
  - 剩余的内容如果属于old数组，表明其属于删除内容，则将本行内容带 “ - ” 输出
  - 剩余的内容如果属于new数组，表明其属于新增内容，则将本行内容带 “ + ” 输出
  - 如果碰到标记内容，则只输出一次
  - 新旧数组进行交替输出



### 命令交互

- git init 创建工作区与暂存区目录

- git add 根据目标路径创建工作区文件对象，并将其复制到暂存区

- git log 输出当前分支的commit历史

- git diff 比较工作区与暂存区文件，并输出改动情况

- reset 分支回滚

- commit 进行commit操作

- checkout 切换分支

- branch 创建分支

- merge 分支合并



## 项目不足

- 功能实现占用资源较高，流程仍有待优化

- 模块间耦合度略高，代码重复部分有待改善

- 出错反馈设计略显不足
