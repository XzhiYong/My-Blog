/*
 Navicat Premium Data Transfer

 Source Server         : xiazy_baicai
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : my_blog_db

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 05/06/2023 18:48:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user`  (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `login_count` int NULL DEFAULT NULL COMMENT '登录次数',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `head_portrait` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `birthday` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  `education` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别1.男 2女',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `signature` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '签名',
  `point` int NULL DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (2, 'admin', '21232f297a57a5a743894a0e4a801fc3', '系统管理员', 0, '2023-04-27 16:33:08', 75, '2023-06-05 13:56:36', 'http://qny.xiazy.xyz/bloge63002e53ae646f1beee78b0de33e39e.jpg', '2799892833@qq.com', '', '2023-01-01', '1', 1, '121212', '192.168.3.182', NULL, 135);
INSERT INTO `tb_admin_user` VALUES (137, '17375180945', '96e79218965eb72c92a549dd5a330112', '钟宇', 0, '2023-05-26 14:47:56', NULL, NULL, 'http://qny.xiazy.xyz/blog5390482cbb974b0ebe684500f91af61f.jpg', NULL, '17375180945', NULL, NULL, NULL, NULL, '192.168.3.182', NULL, NULL);

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `blog_id` bigint NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客自定义路径url',
  `blog_cover_image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客封面图',
  `blog_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客内容',
  `blog_category_id` int NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标签',
  `blog_status` tinyint NOT NULL DEFAULT 0 COMMENT '0-草稿 1-待审核 2-已审核 3-已下架',
  `blog_views` bigint NOT NULL DEFAULT 0 COMMENT '阅读量',
  `enable_comment` tinyint NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int NULL DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`blog_id`) USING BTREE,
  INDEX `fk_category_id`(`blog_category_id` ASC) USING BTREE,
  INDEX `fk_user_id_blog`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_category_id` FOREIGN KEY (`blog_category_id`) REFERENCES `tb_blog_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id_blog` FOREIGN KEY (`user_id`) REFERENCES `tb_admin_user` (`admin_user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES (5, 'JAVA单列模式', '', 'http://192.168.3.182:28083/admin/dist/img/rand/15.jpg', '#单列模式\n**意图**：保证一个类仅有一个实例，并提供一个访问它的全局访问点。\n\n**主要解决**：一个全局使用的类频繁地创建与销毁。\n\n**何时使用**：当您想控制实例数目，节省系统资源的时候。\n\n**如何解决**：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。\n\n**关键代码**：构造函数是私有的。\n\n####应用实例：\n\n1、一个班级只有一个班主任。\n2、Windows 是多进程多线程的，在操作一个文件的时候，就不可避免地出现多个进程或线程同时操作一个文件的现象，所以所有文件的处理必须通过唯一的实例来进行。\n3、一些设备管理器常常设计为单例模式，比如一个电脑有两台打印机，在输出的时候就要处理不能两台打印机打印同一个文件。\n**优点：**\n\n1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。\n2、避免对资源的多重占用（比如写文件操作）。\n缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。\n\n**使用场景：**\n\n1、要求生产唯一序列号。\n2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来。\n3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等。\n注意事项：getInstance() 方法中需要使用同步锁 synchronized (Singleton.class) 防止多线程同时进入造成 instance 被多次实例化。\n实现\n我们将创建一个 SingleObject 类。SingleObject 类有它的私有构造函数和本身的一个静态实例。\n\nSingleObject 类提供了一个静态方法，供外界获取它的静态实例。SingletonPatternDemo 类使用 SingleObject 类来获取 SingleObject 对象。\n\n单例模式的 UML 图\n\n**步骤 1**\n  SingleObject.java\n创建一个 Singleton 类。\n\n  \n    public class SingleObject {\n     \n       //创建 SingleObject 的一个对象\n       private static SingleObject instance = new SingleObject();\n     \n       //让构造函数为 private，这样该类就不会被实例化\n       private SingleObject(){}\n     \n       //获取唯一可用的对象\n       public static SingleObject getInstance(){\n          return instance;\n       }\n     \n       public void showMessage(){\n          System.out.println(\"Hello World!\");\n       }\n}\n**步骤 2**\n从 singleton 类获取唯一的对象。\n\nSingletonPatternDemo.java\n```java\npublic class SingletonPatternDemo {\n   public static void main(String[] args) {\n \n      //不合法的构造函数\n      //编译时错误：构造函数 SingleObject() 是不可见的\n      //SingleObject object = new SingleObject();\n \n      //获取唯一可用的对象\n      SingleObject object = SingleObject.getInstance();\n \n      //显示消息\n      object.showMessage();\n   }\n}\n```\n**步骤 3**\n执行程序，输出结果：\n\nHello World!\n单例模式的几种实现方式\n单例模式的实现有多种方式，如下所示：\n\n####1、懒汉式，线程不安全\n是否 Lazy 初始化：是\n\n是否多线程安全：否\n\n实现难度：易\n\n描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。\n这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n  \n    public static Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n接下来介绍的几种实现方式都支持多线程，但是在性能上有所差异。\n\n####2、懒汉式，线程安全\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。\n优点：第一次调用才初始化，避免内存浪费。\n缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。\ngetInstance() 的性能对应用程序不是很关键（该方法使用不太频繁）。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n    public static synchronized Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n####3、饿汉式\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式比较常用，但容易产生垃圾对象。\n优点：没有加锁，执行效率会提高。\n缺点：类加载时就初始化，浪费内存。\n它基于 classloader 机制避免了多线程的同步问题，不过，instance 在类装载时就实例化，虽然导致类装载的原因有很多种，在单例模式中大多数都是调用 getInstance 方法， 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 显然没有达到 lazy loading 的效果。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance = new Singleton();  \n    private Singleton (){}  \n    public static Singleton getInstance() {  \n    return instance;  \n    }  \n}\n```\n4、双检锁/双重校验锁（DCL，即 double-checked locking）\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：较复杂\n\n描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。\ngetInstance() 的性能对应用程序很关键。\n\n实例\n```java\npublic class Singleton {  \n    private volatile static Singleton singleton;  \n    private Singleton (){}  \n    public static Singleton getSingleton() {  \n    if (singleton == null) {  \n        synchronized (Singleton.class) {  \n            if (singleton == null) {  \n                singleton = new Singleton();  \n            }  \n        }  \n    }  \n    return singleton;  \n    }  \n}\n```\n####5、登记式/静态内部类\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：一般\n\n描述：这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。\n这种方式同样利用了 classloader 机制来保证初始化 instance 时只有一个线程，它跟第 3 种方式不同的是：第 3 种方式只要 Singleton 类被装载了，那么 instance 就会被实例化（没有达到 lazy loading 效果），而这种方式是 Singleton 类被装载了，instance 不一定被初始化。因为 SingletonHolder 类没有被主动使用，只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder 类，从而实例化 instance。想象一下，如果实例化 instance 很消耗资源，所以想让它延迟加载，另外一方面，又不希望在 Singleton 类加载时就实例化，因为不能确保 Singleton 类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化 instance 显然是不合适的。这个时候，这种方式相比第 3 种方式就显得很合理。\n\n实例\n```java\npublic class Singleton {  \n    private static class SingletonHolder {  \n    private static final Singleton INSTANCE = new Singleton();  \n    }  \n    private Singleton (){}  \n    public static final Singleton getInstance() {  \n        return SingletonHolder.INSTANCE;  \n    }  \n}\n```\n####6、枚举\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。\n这种方式是 Effective Java 作者 Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。\n不能通过 reflection attack 来调用私有构造方法。\n\n实例\n```java\npublic enum Singleton {  \n    INSTANCE;  \n    public void whateverMethod() {  \n    }  \n}\n```\n经验之谈：一般情况下，不建议使用第 1 种和第 2 种懒汉方式，建议使用第 3 种饿汉方式。只有在要明确实现 lazy loading 效果时，才会使用第 5 种登记方式。如果涉及到反序列化创建对象时，可以尝试使用第 6 种枚举方式。如果有其他特殊的需求，可以考虑使用第 4 种双检锁方式。', 25, '设计模式', '设计模式', 1, 8, 0, 1, '2023-04-17 18:25:12', '2023-04-17 18:25:12', 2);
INSERT INTO `tb_blog` VALUES (6, 'JAVA单列模式', '', 'http://qny.xiazy.xyz/blogfe3f606ed08b4edd844fdc105a7547a2.jpg', '#单列模式\n**意图**：保证一个类仅有一个实例，并提供一个访问它的全局访问点。\n\n**主要解决**：一个全局使用的类频繁地创建与销毁。\n\n**何时使用**：当您想控制实例数目，节省系统资源的时候。\n\n**如何解决**：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。\n\n**关键代码**：构造函数是私有的。\n\n####应用实例：\n\n1、一个班级只有一个班主任。\n2、Windows 是多进程多线程的，在操作一个文件的时候，就不可避免地出现多个进程或线程同时操作一个文件的现象，所以所有文件的处理必须通过唯一的实例来进行。\n3、一些设备管理器常常设计为单例模式，比如一个电脑有两台打印机，在输出的时候就要处理不能两台打印机打印同一个文件。\n**优点：**\n\n1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。\n2、避免对资源的多重占用（比如写文件操作）。\n缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。\n\n**使用场景：**\n\n1、要求生产唯一序列号。\n2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来。\n3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等。\n注意事项：getInstance() 方法中需要使用同步锁 synchronized (Singleton.class) 防止多线程同时进入造成 instance 被多次实例化。\n实现\n我们将创建一个 SingleObject 类。SingleObject 类有它的私有构造函数和本身的一个静态实例。\n\nSingleObject 类提供了一个静态方法，供外界获取它的静态实例。SingletonPatternDemo 类使用 SingleObject 类来获取 SingleObject 对象。\n\n单例模式的 UML 图\n\n**步骤 1**\n  SingleObject.java\n创建一个 Singleton 类。\n\n  \n    public class SingleObject {\n     \n       //创建 SingleObject 的一个对象\n       private static SingleObject instance = new SingleObject();\n     \n       //让构造函数为 private，这样该类就不会被实例化\n       private SingleObject(){}\n     \n       //获取唯一可用的对象\n       public static SingleObject getInstance(){\n          return instance;\n       }\n     \n       public void showMessage(){\n          System.out.println(\"Hello World!\");\n       }\n}\n**步骤 2**\n从 singleton 类获取唯一的对象。\n\nSingletonPatternDemo.java\n```java\npublic class SingletonPatternDemo {\n   public static void main(String[] args) {\n \n      //不合法的构造函数\n      //编译时错误：构造函数 SingleObject() 是不可见的\n      //SingleObject object = new SingleObject();\n \n      //获取唯一可用的对象\n      SingleObject object = SingleObject.getInstance();\n \n      //显示消息\n      object.showMessage();\n   }\n}\n```\n**步骤 3**\n执行程序，输出结果：\n\nHello World!\n单例模式的几种实现方式\n单例模式的实现有多种方式，如下所示：\n\n####1、懒汉式，线程不安全\n是否 Lazy 初始化：是\n\n是否多线程安全：否\n\n实现难度：易\n\n描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。\n这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n  \n    public static Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n接下来介绍的几种实现方式都支持多线程，但是在性能上有所差异。\n\n####2、懒汉式，线程安全\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。\n优点：第一次调用才初始化，避免内存浪费。\n缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。\ngetInstance() 的性能对应用程序不是很关键（该方法使用不太频繁）。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n    public static synchronized Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n####3、饿汉式\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式比较常用，但容易产生垃圾对象。\n优点：没有加锁，执行效率会提高。\n缺点：类加载时就初始化，浪费内存。\n它基于 classloader 机制避免了多线程的同步问题，不过，instance 在类装载时就实例化，虽然导致类装载的原因有很多种，在单例模式中大多数都是调用 getInstance 方法， 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 显然没有达到 lazy loading 的效果。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance = new Singleton();  \n    private Singleton (){}  \n    public static Singleton getInstance() {  \n    return instance;  \n    }  \n}\n```\n4、双检锁/双重校验锁（DCL，即 double-checked locking）\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：较复杂\n\n描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。\ngetInstance() 的性能对应用程序很关键。\n\n实例\n```java\npublic class Singleton {  \n    private volatile static Singleton singleton;  \n    private Singleton (){}  \n    public static Singleton getSingleton() {  \n    if (singleton == null) {  \n        synchronized (Singleton.class) {  \n            if (singleton == null) {  \n                singleton = new Singleton();  \n            }  \n        }  \n    }  \n    return singleton;  \n    }  \n}\n```\n####5、登记式/静态内部类\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：一般\n\n描述：这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。\n这种方式同样利用了 classloader 机制来保证初始化 instance 时只有一个线程，它跟第 3 种方式不同的是：第 3 种方式只要 Singleton 类被装载了，那么 instance 就会被实例化（没有达到 lazy loading 效果），而这种方式是 Singleton 类被装载了，instance 不一定被初始化。因为 SingletonHolder 类没有被主动使用，只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder 类，从而实例化 instance。想象一下，如果实例化 instance 很消耗资源，所以想让它延迟加载，另外一方面，又不希望在 Singleton 类加载时就实例化，因为不能确保 Singleton 类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化 instance 显然是不合适的。这个时候，这种方式相比第 3 种方式就显得很合理。\n\n实例\n```java\npublic class Singleton {  \n    private static class SingletonHolder {  \n    private static final Singleton INSTANCE = new Singleton();  \n    }  \n    private Singleton (){}  \n    public static final Singleton getInstance() {  \n        return SingletonHolder.INSTANCE;  \n    }  \n}\n```\n####6、枚举\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。\n这种方式是 Effective Java 作者 Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。\n不能通过 reflection attack 来调用私有构造方法。\n\n实例\n```java\npublic enum Singleton {  \n    INSTANCE;  \n    public void whateverMethod() {  \n    }  \n}\n```\n经验之谈：一般情况下，不建议使用第 1 种和第 2 种懒汉方式，建议使用第 3 种饿汉方式。只有在要明确实现 lazy loading 效果时，才会使用第 5 种登记方式。如果涉及到反序列化创建对象时，可以尝试使用第 6 种枚举方式。如果有其他特殊的需求，可以考虑使用第 4 种双检锁方式。', 25, '设计模式', 'JAVA,设计模式', 2, 1183, 0, 0, '2023-04-21 13:54:26', '2023-05-04 17:02:33', 2);
INSERT INTO `tb_blog` VALUES (8, 'JAVA单列模式1', '', 'http://qny.xiazy.xyz/blog96cf7bb4316c4ee788a8038b0d3cf90d.jpg', '1', 24, '日常随笔', '1', 1, 0, 0, 1, '2023-05-10 17:28:34', '2023-05-10 17:28:34', 2);

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (20, 'About', '/admin/dist/img/category/10.png', 8, 1, '2018-11-12 00:28:49');
INSERT INTO `tb_blog_category` VALUES (22, 'SSM整合进阶篇', '/admin/dist/img/category/02.png', 19, 0, '2018-11-12 10:42:25');
INSERT INTO `tb_blog_category` VALUES (24, '日常随笔', '/admin/dist/img/category/06.png', 24, 0, '2018-11-12 10:43:21');
INSERT INTO `tb_blog_category` VALUES (25, '设计模式', '/admin/dist/img/category/06.png', 4, 0, '2023-04-17 18:16:07');
INSERT INTO `tb_blog_category` VALUES (26, 'mysql', '/admin/dist/img/category/11.png', 1, 0, '2023-04-23 17:56:20');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `blog_id` bigint NULL DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parentCommentId` int NULL DEFAULT NULL,
  `replyCommentId` int NULL DEFAULT NULL,
  `remind` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  INDEX `parentCommentId`(`parentCommentId` ASC) USING BTREE,
  INDEX `replyCommentId`(`replyCommentId` ASC) USING BTREE,
  INDEX `tb_blog_comment_ibfk_4`(`blog_id` ASC) USING BTREE,
  CONSTRAINT `tb_blog_comment_ibfk_2` FOREIGN KEY (`parentCommentId`) REFERENCES `tb_blog_comment` (`cid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `tb_blog_comment_ibfk_3` FOREIGN KEY (`replyCommentId`) REFERENCES `tb_blog_comment` (`cid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `tb_blog_comment_ibfk_4` FOREIGN KEY (`blog_id`) REFERENCES `tb_blog` (`blog_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (71, 2, 6, '2023-05-29 18:31:19', '121212<img class=\"emoji_icon\" src=\"/blog/img/emoji/2.gif\">', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (73, 2, 6, '2023-06-02 17:15:25', '1111111', 71, 71, 0);

-- ----------------------------
-- Table structure for tb_blog_cover
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_cover`;
CREATE TABLE `tb_blog_cover`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '随机封面',
  `url` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_cover
-- ----------------------------
INSERT INTO `tb_blog_cover` VALUES (2, 'http://qny.xiazy.xyz/blog89df10a9f616485cac2358afe1825c65.jpg', '2023-05-10 16:56:06');
INSERT INTO `tb_blog_cover` VALUES (3, 'http://qny.xiazy.xyz/blog18678bfef6f44a3b9896fba024e514fa.jpg', '2023-05-10 16:59:51');
INSERT INTO `tb_blog_cover` VALUES (4, 'http://qny.xiazy.xyz/blog396fa52ad0454c80934eaf7a3c0773be.jpg', '2023-05-10 17:44:14');
INSERT INTO `tb_blog_cover` VALUES (5, 'http://qny.xiazy.xyz/blog61eb52075cb3451ab6ec59b2ccb7a214.jpg', '2023-05-11 09:22:11');
INSERT INTO `tb_blog_cover` VALUES (6, 'http://qny.xiazy.xyz/bloga971d86f501d4967ae4dc4f1978d3627.jpg', '2023-05-11 09:22:20');
INSERT INTO `tb_blog_cover` VALUES (8, 'http://qny.xiazy.xyz/bloga14118f3284444a881866a3abdba9dc3.jpg', '2023-05-11 09:23:36');
INSERT INTO `tb_blog_cover` VALUES (9, 'http://qny.xiazy.xyz/blog793b082c77d7434c9b26089e4bf73391.jpg', '2023-05-11 09:23:42');
INSERT INTO `tb_blog_cover` VALUES (11, 'http://qny.xiazy.xyz/blogf9024fe64e9445b0b3e9d71136bc1522.jpg', '2023-05-11 09:24:17');
INSERT INTO `tb_blog_cover` VALUES (12, 'http://qny.xiazy.xyz/blog7ba947cb28dd4ed0b5b44dbe56db9c38.jpg', '2023-05-11 09:24:22');
INSERT INTO `tb_blog_cover` VALUES (14, 'http://qny.xiazy.xyz/bloge8a6d5c8b88f402c9f3cf1f276701b51.jpg', '2023-05-11 09:25:03');
INSERT INTO `tb_blog_cover` VALUES (16, 'http://qny.xiazy.xyz/blogfe81f80e5a59427fb85ee9836b3010f1.jpg', '2023-05-11 09:25:12');
INSERT INTO `tb_blog_cover` VALUES (17, 'http://qny.xiazy.xyz/blogc05477a3f249474cb72716bc47fe6d6c.jpg', '2023-05-11 09:27:44');
INSERT INTO `tb_blog_cover` VALUES (18, 'http://qny.xiazy.xyz/blog515fee2e2e7a46efba4b642838969706.jpg', '2023-05-11 09:27:52');
INSERT INTO `tb_blog_cover` VALUES (20, 'http://qny.xiazy.xyz/blog1416a45c48f64951a657c07aa86a11ca.jpg', '2023-05-11 09:29:14');
INSERT INTO `tb_blog_cover` VALUES (21, 'http://qny.xiazy.xyz/blog60e78dac687c40c1ac9d1b144b6b2c3e.jpg', '2023-05-11 09:30:49');
INSERT INTO `tb_blog_cover` VALUES (22, 'http://qny.xiazy.xyz/blog4f59b807e68747b5a38e7aeaf664d377.jpg', '2023-05-11 09:30:58');
INSERT INTO `tb_blog_cover` VALUES (26, 'http://qny.xiazy.xyz/blog2a8b398dbb3c40a38c9fedb7a042bda8.jpg', '2023-05-11 09:31:15');
INSERT INTO `tb_blog_cover` VALUES (27, 'http://qny.xiazy.xyz/blog62f82fc5107d46b0a9b129244575cc58.jpg', '2023-05-11 09:33:25');
INSERT INTO `tb_blog_cover` VALUES (28, 'http://qny.xiazy.xyz/blog477621610fe44b61a61bb3e23ca60c15.jpg', '2023-05-11 09:34:04');
INSERT INTO `tb_blog_cover` VALUES (29, 'http://qny.xiazy.xyz/blog8b7dbbb6dc424632981be347a422e77e.jpg', '2023-05-11 09:34:12');
INSERT INTO `tb_blog_cover` VALUES (30, 'http://qny.xiazy.xyz/blogc4c3df8be4414bee8b9042c3102e19f1.jpg', '2023-05-11 09:40:05');
INSERT INTO `tb_blog_cover` VALUES (31, 'http://qny.xiazy.xyz/blog98cc29404d8242ee9ed8289b01b3d4f8.jpg', '2023-05-11 09:40:11');
INSERT INTO `tb_blog_cover` VALUES (32, 'http://qny.xiazy.xyz/blog8020b0f804734cb489727d9a2578d86f.jpg', '2023-05-11 09:40:19');

-- ----------------------------
-- Table structure for tb_blog_fans
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_fans`;
CREATE TABLE `tb_blog_fans`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关注列表',
  `follower_user_id` int NOT NULL COMMENT '关注者',
  `user_id` int NULL DEFAULT NULL COMMENT '被关注者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_fans
-- ----------------------------

-- ----------------------------
-- Table structure for tb_blog_msg
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_msg`;
CREATE TABLE `tb_blog_msg`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '消息表',
  `c_id` int NULL DEFAULT NULL COMMENT '谁发表',
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '消息',
  `u_id` int NULL DEFAULT NULL COMMENT '用户',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `blog_id` int NULL DEFAULT NULL COMMENT '博客id',
  `state` int NULL DEFAULT 0 COMMENT '0.未读 1.已读',
  `type` int NULL DEFAULT NULL COMMENT '类型 0系统消息 1评论文章 2回复评论',
  `is_deleted` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_msg
-- ----------------------------
INSERT INTO `tb_blog_msg` VALUES (4, 2, '121212', 2, '2023-05-17 18:03:34', 6, 1, 2, 1);
INSERT INTO `tb_blog_msg` VALUES (5, 2, '121212', 2, '2023-05-25 10:26:42', 6, 1, 1, 1);
INSERT INTO `tb_blog_msg` VALUES (29, 2, '121212<img class=\"emoji_icon\" src=\"/blog/img/emoji/2.gif\">', 2, '2023-05-29 18:31:19', 6, 1, 1, 0);
INSERT INTO `tb_blog_msg` VALUES (30, 2, '121212', 2, '2023-05-29 18:31:25', 6, 1, 2, 0);
INSERT INTO `tb_blog_msg` VALUES (31, 2, '121212', 2, '2023-06-02 16:38:00', NULL, 1, 0, 0);
INSERT INTO `tb_blog_msg` VALUES (32, 2, '12121212', 2, '2023-06-02 16:49:23', NULL, 1, 0, 0);
INSERT INTO `tb_blog_msg` VALUES (33, 2, '12121212', 2, '2023-06-02 16:59:41', NULL, 1, 0, 0);
INSERT INTO `tb_blog_msg` VALUES (34, 2, '12121212', 2, '2023-06-02 16:59:47', NULL, 1, 0, 0);
INSERT INTO `tb_blog_msg` VALUES (35, 2, '1111111', 2, '2023-06-02 17:15:25', 6, 1, 2, 0);

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (57, '世界上有一个很可爱的人', 0, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (58, '现在这个人就在看这句话', 0, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (66, 'Spring', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (67, 'SpringMVC', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (68, 'MyBatis', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (69, 'easyUI', 0, '2018-11-12 10:55:14');
INSERT INTO `tb_blog_tag` VALUES (127, '目录', 0, '2019-04-24 15:41:39');
INSERT INTO `tb_blog_tag` VALUES (128, 'AdminLte3', 0, '2019-04-24 15:46:16');
INSERT INTO `tb_blog_tag` VALUES (130, 'SpringBoot', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (131, '入门教程', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (132, '实战教程', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (133, 'spring-boot企业级开发', 0, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag` VALUES (134, 'JAVA设计模式', 0, '2023-04-17 18:15:32');
INSERT INTO `tb_blog_tag` VALUES (135, '设计模式', 0, '2023-04-17 18:25:12');
INSERT INTO `tb_blog_tag` VALUES (136, 'JAVA', 0, '2023-04-21 13:54:26');
INSERT INTO `tb_blog_tag` VALUES (137, 'undefined', 1, '2023-04-24 15:50:22');
INSERT INTO `tb_blog_tag` VALUES (138, '功能测试', 1, '2023-04-24 15:56:29');
INSERT INTO `tb_blog_tag` VALUES (139, '1', 0, '2023-05-10 17:28:34');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation`  (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 285 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES (266, 1, 57, '2019-05-13 09:45:42');
INSERT INTO `tb_blog_tag_relation` VALUES (267, 1, 58, '2019-05-13 09:45:42');
INSERT INTO `tb_blog_tag_relation` VALUES (269, 2, 127, '2019-05-13 09:56:49');
INSERT INTO `tb_blog_tag_relation` VALUES (270, 4, 130, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (271, 4, 131, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (272, 4, 132, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (273, 4, 133, '2019-05-13 09:58:54');
INSERT INTO `tb_blog_tag_relation` VALUES (274, 3, 66, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (275, 3, 67, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (276, 3, 68, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (277, 3, 69, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (278, 3, 128, '2019-05-13 10:07:27');
INSERT INTO `tb_blog_tag_relation` VALUES (280, 5, 135, '2023-04-17 18:27:45');
INSERT INTO `tb_blog_tag_relation` VALUES (281, 6, 135, '2023-04-21 13:54:26');
INSERT INTO `tb_blog_tag_relation` VALUES (282, 6, 136, '2023-04-21 13:54:26');
INSERT INTO `tb_blog_tag_relation` VALUES (283, 7, 136, '2023-05-06 11:47:53');
INSERT INTO `tb_blog_tag_relation` VALUES (284, 8, 139, '2023-05-10 17:28:34');

-- ----------------------------
-- Table structure for tb_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config`  (
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的名称',
  `config_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_config
-- ----------------------------
INSERT INTO `tb_config` VALUES ('footerAbout', 'your personal blog. have fun.', '2018-11-11 20:33:23', '2023-04-17 10:31:19');
INSERT INTO `tb_config` VALUES ('footerCopyRight', '钟宇', '2018-11-11 20:33:31', '2023-04-17 10:31:19');
INSERT INTO `tb_config` VALUES ('footerICP', '豫ICP备2023006969号', '2018-11-11 20:33:27', '2023-04-17 10:31:19');
INSERT INTO `tb_config` VALUES ('footerPoweredBy', 'https://github.com/ZHENFENG13', '2018-11-11 20:33:36', '2023-04-17 10:31:19');
INSERT INTO `tb_config` VALUES ('footerPoweredByURL', 'https://github.com/ZHENFENG13', '2018-11-11 20:33:39', '2023-04-17 10:31:19');
INSERT INTO `tb_config` VALUES ('websiteDescription', 'personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建', '2018-11-11 20:33:04', '2023-04-17 10:29:10');
INSERT INTO `tb_config` VALUES ('websiteIcon', '/admin/dist/img/favicon.png', '2018-11-11 20:33:11', '2023-04-17 10:29:10');
INSERT INTO `tb_config` VALUES ('websiteLogo', '/admin/dist/img/logo2.png', '2018-11-11 20:33:08', '2023-04-17 10:29:10');
INSERT INTO `tb_config` VALUES ('websiteName', 'personal blog', '2018-11-11 20:33:01', '2023-04-17 10:29:10');
INSERT INTO `tb_config` VALUES ('yourAvatar', '/admin/dist/img/13.png', '2018-11-11 20:33:14', '2023-04-17 10:29:08');
INSERT INTO `tb_config` VALUES ('yourEmail', '2799892833@qq.com', '2018-11-11 20:33:17', '2023-04-17 10:29:08');
INSERT INTO `tb_config` VALUES ('yourName', '钟宇', '2018-11-11 20:33:20', '2023-04-17 10:29:08');

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_link`;
CREATE TABLE `tb_link`  (
  `link_id` int NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_link
-- ----------------------------
INSERT INTO `tb_link` VALUES (1, 0, 'tqr', 'rqwe', 'rqw', 0, 1, '2018-10-22 18:57:52');
INSERT INTO `tb_link` VALUES (2, 2, '十三的GitHub', 'https://github.com/ZHENFENG13', '十三分享代码的地方', 1, 0, '2018-10-22 19:41:04');
INSERT INTO `tb_link` VALUES (3, 2, '十三的博客', 'http://13blog.site', '个人独立博客13blog', 14, 0, '2018-10-22 19:53:34');
INSERT INTO `tb_link` VALUES (4, 1, 'CSDN 图文课', 'https://gitchat.csdn.net', 'IT优质内容平台', 6, 0, '2018-10-22 19:55:55');
INSERT INTO `tb_link` VALUES (5, 2, '十三的博客园', 'https://www.cnblogs.com/han-1034683568', '最开始写博客的地方', 17, 0, '2018-10-22 19:56:14');
INSERT INTO `tb_link` VALUES (6, 1, 'CSDN', 'https://www.csdn.net/', 'CSDN-专业IT技术社区官网', 4, 0, '2018-10-22 19:56:47');
INSERT INTO `tb_link` VALUES (7, 0, '梁桂钊的博客', 'http://blog.720ui.com', '后端攻城狮', 1, 0, '2018-10-22 20:01:38');
INSERT INTO `tb_link` VALUES (8, 0, '猿天地', 'http://cxytiandi.com', '一个综合性的网站,以程序猿用户为主,提供各种开发相关的内容', 12, 0, '2018-10-22 20:02:41');
INSERT INTO `tb_link` VALUES (9, 0, 'Giraffe Home', 'https://yemengying.com/', 'Giraffe Home', 0, 0, '2018-10-22 20:27:04');
INSERT INTO `tb_link` VALUES (10, 0, '纯洁的微笑', 'http://www.ityouknow.com', '分享技术，分享生活', 3, 0, '2018-10-22 20:27:16');
INSERT INTO `tb_link` VALUES (11, 0, 'afsdf', 'http://localhost:28080/admin/links', 'fad', 0, 1, '2018-10-22 20:27:26');
INSERT INTO `tb_link` VALUES (12, 1, 'afsdf', 'http://localhost', 'fad1', 0, 1, '2018-10-24 14:04:18');
INSERT INTO `tb_link` VALUES (13, 0, '郭赵晖', 'http://guozh.net/', '老郭三分地', 0, 0, '2019-04-24 15:30:19');
INSERT INTO `tb_link` VALUES (14, 0, 'dalaoyang', 'https://www.dalaoyang.cn/', 'dalaoyang', 0, 0, '2019-04-24 15:31:50');
INSERT INTO `tb_link` VALUES (15, 0, 'mushblog', 'https://www.sansani.cn', '穆世明博客', 0, 0, '2019-04-24 15:32:19');
INSERT INTO `tb_link` VALUES (16, 1, '实验楼', 'https://www.shiyanlou.com/', '一家专注于IT技术的在线实训平台', 17, 0, '2019-04-24 16:03:48');
INSERT INTO `tb_link` VALUES (17, 2, '《SSM 搭建精美实用的管理系统》', 'https://gitbook.cn/gitchat/column/5b4dae389bcda53d07056bc9', 'Spring+SpringMVC+MyBatis实战课程', 18, 0, '2019-04-24 16:06:52');
INSERT INTO `tb_link` VALUES (18, 2, '《Spring Boot 入门及前后端分离项目实践》', 'https://www.shiyanlou.com/courses/1244', 'SpringBoot实战课程', 19, 0, '2019-04-24 16:07:27');
INSERT INTO `tb_link` VALUES (19, 2, '《玩转Spring Boot 系列》', 'https://www.shiyanlou.com/courses/1274', 'SpringBoot实战课程', 20, 0, '2019-04-24 16:10:30');

-- ----------------------------
-- Table structure for tb_sign
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign`;
CREATE TABLE `tb_sign`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '签到的用户ID',
  `days` int NOT NULL COMMENT '连续签到的天数',
  `info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他信息（JSON格式）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sign
-- ----------------------------
INSERT INTO `tb_sign` VALUES (1661632952544616449, 2, 5, '{\"ip\":\"192.168.3.182\",\"ipAddress\":\"本地局域网\"}', '2023-05-25 15:19:02', '2023-05-31 10:44:08');

-- ----------------------------
-- Table structure for tb_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign_log`;
CREATE TABLE `tb_sign_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '签到的用户ID',
  `sign_id` bigint NULL DEFAULT NULL COMMENT '日志对应的签到记录',
  `point` int NULL DEFAULT NULL COMMENT '签到所得的积分',
  `ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签到时的IP',
  `ip_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP所属的地区',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sign_log
-- ----------------------------
INSERT INTO `tb_sign_log` VALUES (1661632952544616450, 2, 1661632952544616449, 5, '192.168.3.182', '本地局域网', '2023-05-25 15:19:02', NULL);
INSERT INTO `tb_sign_log` VALUES (1661906622231961602, 2, 1661632952544616449, 5, '192.168.3.182', '本地局域网', '2023-05-26 09:26:30', NULL);
INSERT INTO `tb_sign_log` VALUES (1662991648788865026, 2, 1661632952544616449, 5, '192.168.3.182', '本地局域网', '2023-05-29 09:18:01', NULL);
INSERT INTO `tb_sign_log` VALUES (1663351650892419074, 2, 1661632952544616449, 5, '192.168.3.182', '本地局域网', '2023-05-30 09:08:32', NULL);
INSERT INTO `tb_sign_log` VALUES (1663747023993569282, 2, 1661632952544616449, 50, '192.168.3.182', '本地局域网', '2023-05-31 11:19:36', NULL);
INSERT INTO `tb_sign_log` VALUES (1664128682979000321, 2, 1661632952544616449, 50, '127.0.0.1', '本地局域网，无法获取位置', '2023-06-01 12:36:11', NULL);
INSERT INTO `tb_sign_log` VALUES (1664562988356485121, 2, 1661632952544616449, 50, '192.168.3.182', '本地局域网', '2023-06-02 17:21:57', NULL);

-- ----------------------------
-- Table structure for tb_sms_msg
-- ----------------------------
DROP TABLE IF EXISTS `tb_sms_msg`;
CREATE TABLE `tb_sms_msg`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `result` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果',
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '消息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sms_msg
-- ----------------------------
INSERT INTO `tb_sms_msg` VALUES (5, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:104363764516835299334591062\",\"PhoneNumber\":\"+8617710810624\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"0fcf7adb-39f1-4c9d-9388-ae656e9dc1b4\"}', '2023-05-08 15:12:19', '17710810624');
INSERT INTO `tb_sms_msg` VALUES (6, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:71625343316835301995991062\",\"PhoneNumber\":\"+8617710810624\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"77961e68-faf7-4878-aaab-c86e2891d083\"}', '2023-05-08 15:16:40', '17710810624');
INSERT INTO `tb_sms_msg` VALUES (7, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:297003605116835305892371062\",\"PhoneNumber\":\"+8617710810624\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"74be9c04-019b-426c-b1f5-d96d64d9cdc7\"}', '2023-05-08 15:23:09', '17710810624');
INSERT INTO `tb_sms_msg` VALUES (8, 'on', '{\"SendStatusSet\":[{\"SerialNo\":\"\",\"PhoneNumber\":\"+8617710810624\",\"Fee\":0,\"SessionContext\":\"\",\"Code\":\"LimitExceeded.PhoneNumberOneHourLimit\",\"Message\":\"the number of sms messages sent from a single mobile number within 1 hour exceeds the upper limit\",\"IsoCode\":\"CN\"}],\"RequestId\":\"cf9fdb30-cd0b-4041-b006-a976677f86a6\"}', '2023-05-08 15:24:40', '17710810624');
INSERT INTO `tb_sms_msg` VALUES (9, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:404533855916835308032433659\",\"PhoneNumber\":\"+8617788236596\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"4dc079b6-557d-4441-8c61-22359168a192\"}', '2023-05-08 15:26:43', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (10, 'ok', NULL, '2023-05-08 15:30:20', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (11, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:44766976016835350112083659\",\"PhoneNumber\":\"+8617788236596\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"7e797adf-fddd-4878-9706-18197364630b\"}', '2023-05-08 16:36:51', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (12, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:3414219616835400712883659\",\"PhoneNumber\":\"+8617788236596\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"14831fc8-56e8-41ed-9205-67e12fda6a8b\"}', '2023-05-08 18:01:12', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (13, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:210689988216835965817088094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"b892cf0a-3b2d-46c2-8d28-b64d6c55b6e0\"}', '2023-05-09 09:43:03', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (14, 'ok', NULL, '2023-05-09 09:46:13', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (15, 'ok', NULL, '2023-05-09 09:50:26', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (16, 'ok', NULL, '2023-05-09 09:54:56', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (17, 'ok', NULL, '2023-05-09 09:57:16', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (18, 'ok', NULL, '2023-05-09 10:01:50', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (19, 'ok', NULL, '2023-05-09 10:04:02', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (20, 'ok', NULL, '2023-05-09 10:05:38', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (21, 'ok', NULL, '2023-05-09 10:09:02', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (22, 'ok', NULL, '2023-05-09 10:15:17', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (23, 'ok', NULL, '2023-05-09 10:30:20', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (24, 'ok', NULL, '2023-05-09 10:31:36', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (25, 'ok', NULL, '2023-05-09 10:33:38', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (26, 'ok', NULL, '2023-05-09 10:36:02', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (27, 'ok', NULL, '2023-05-09 10:46:12', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (28, 'ok', NULL, '2023-05-09 10:54:39', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (29, 'ok', NULL, '2023-05-09 10:55:58', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (30, 'ok', NULL, '2023-05-09 11:00:35', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (31, 'ok', NULL, '2023-05-09 11:09:06', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (32, 'ok', NULL, '2023-05-09 11:11:26', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (33, 'ok', NULL, '2023-05-09 11:13:52', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (34, 'ok', NULL, '2023-05-09 11:16:07', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (35, 'ok', NULL, '2023-05-09 11:23:14', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (36, 'ok', NULL, '2023-05-09 11:24:59', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (37, 'ok', NULL, '2023-05-09 11:40:16', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (38, 'ok', NULL, '2023-05-09 11:42:18', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (39, 'ok', NULL, '2023-05-09 11:44:57', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (40, 'ok', NULL, '2023-05-09 11:51:45', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (41, 'ok', NULL, '2023-05-09 12:05:00', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (42, 'ok', NULL, '2023-05-09 12:11:21', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (43, 'ok', NULL, '2023-05-09 12:14:08', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (44, 'ok', NULL, '2023-05-09 12:55:41', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (45, 'ok', NULL, '2023-05-09 13:02:26', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (46, 'ok', NULL, '2023-05-09 13:08:57', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (47, 'ok', NULL, '2023-05-09 13:10:40', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (48, 'on', 'Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1:6379', '2023-05-10 17:48:43', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (49, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:255257749416837121796578094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"1eae518b-4b0f-4a31-a200-ce04f45481c1\"}', '2023-05-10 17:49:40', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (50, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:397234718116837123740568094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"1cb3eae5-92e5-4252-bd5a-c6149e5d6ccb\"}', '2023-05-10 17:52:55', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (51, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:123736855116837127289278094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"2cbda56b-aa49-492c-887b-61ce17500f0f\"}', '2023-05-10 17:58:49', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (52, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:182690024216837128571368094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"4d5783ea-4575-431e-ae27-2064921dc97e\"}', '2023-05-10 18:00:57', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (53, 'on', 'Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1:6379', '2023-05-17 09:04:16', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (54, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:172294806916842855389468094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"4d1c1067-07f7-49b4-881b-aeb7dbb55a10\"}', '2023-05-17 09:05:40', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (55, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:41774979616843061728468094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"d703c2f3-5307-4e53-a28d-5e0b004184b3\"}', '2023-05-17 14:49:34', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (56, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:362642518416844035487443659\",\"PhoneNumber\":\"+8617788236596\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"fd287c3e-2e79-42c5-b4fd-3f993d2e4dac\"}', '2023-05-18 17:52:30', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (57, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:14548250816844037446403659\",\"PhoneNumber\":\"+8617788236596\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"54ff2345-60ec-4042-add2-5040c223ac91\"}', '2023-05-18 17:55:45', '17788236596');
INSERT INTO `tb_sms_msg` VALUES (58, 'on', 'Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1:6379', '2023-05-26 14:45:38', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (59, 'on', 'Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1:6379', '2023-05-26 14:46:38', '17375180945');
INSERT INTO `tb_sms_msg` VALUES (60, 'ok', '{\"SendStatusSet\":[{\"SerialNo\":\"3369:136962363516850836556648094\",\"PhoneNumber\":\"+8617375180945\",\"Fee\":1,\"SessionContext\":\"\",\"Code\":\"Ok\",\"Message\":\"send success\",\"IsoCode\":\"CN\"}],\"RequestId\":\"0d26ccde-3f05-4443-a228-484da13661b1\"}', '2023-05-26 14:47:37', '17375180945');

-- ----------------------------
-- Table structure for tb_sys_data_bank
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_data_bank`;
CREATE TABLE `tb_sys_data_bank`  (
  `id` int NOT NULL COMMENT '资源库',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称',
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源地址',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `classify` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级分类',
  `classify_item` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级分类',
  `crate_user_id` int NULL DEFAULT NULL COMMENT '提交人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `check_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `state` tinyint NULL DEFAULT 0 COMMENT '状态 0 待审核 1 审核通过 2 下架',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_data_bank
-- ----------------------------
INSERT INTO `tb_sys_data_bank` VALUES (-1925083135, 'Goobe', 'http://images.newstar.net.cn/img/image-20201213230950269.png', 'https://goobe.io/', '一个好用的\"程序员搜索\"', '搜索资源', '引擎类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083134, 'DogeDoge', 'http://images.newstar.net.cn/img/image-20201213230920144.png', 'https://www.dogedoge.com/', '不追踪、不误导搜索', '搜索资源', '引擎类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083133, '秘迹搜索', 'http://images.newstar.net.cn/img/image-20201213230812704.png', 'https://mijisou.com/', '不追踪你的搜索引擎', '搜索资源', '引擎类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083132, 'mengso', 'http://images.newstar.net.cn/img/image-20201215204356710.png', 'https://mengso.com/', '不追踪你的搜索引擎', '搜索资源', '引擎类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083131, '小马盘搜索', 'http://images.newstar.net.cn/img/image-20201213230701783.png', 'https://www.xiaomapan.com/', '简洁好用的百度网盘搜索', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083130, '资源搜索', 'http://images.newstar.net.cn/img/image-20201213230623161.png', 'http://magnet.chongbuluo.com/', '网盘资源搜索', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083129, '白马盘', 'http://images.newstar.net.cn/img/image-20201213230542035.png', 'https://www.baimapan.com/', '更新最及时的百度网盘资源', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083128, '飞飞盘', 'http://images.newstar.net.cn/img/image-20201213230457970.png', 'https://www.feifeipan.com/', '百度网盘搜索引擎', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083127, '58盘搜索', 'http://images.newstar.net.cn/img/image-20201213230402793.png', 'https://www.58wangpan.com/', '提供网盘资源搜索、网盘资源分享', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083126, '面包树', 'http://images.newstar.net.cn/img/image-20201213230320980.png', 'https://www.yunpanjingling.com/', '资源搜索', '搜索资源', '网盘类', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083125, '谷粉学术', 'http://images.newstar.net.cn/img/image-20201213230243073.png', 'https://gfsoso.99lb.net/', '快速地利用谷歌学术搜索查找文献', '搜索资源', '学术资源', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083124, '术语在线', 'http://images.newstar.net.cn/img/image-20201213230137690.png', 'https://www.termonline.cn/index', '术语知识公共服务平台', '搜索资源', '学术资源', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083123, 'iData', 'http://images.newstar.net.cn/img/image-20201213230039210.png', 'https://www.cn-ki.net/', '学术文献有限浏览下载', '搜索资源', '学术资源', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-1925083122, '简答题', 'http://images.newstar.net.cn/img/jiandati.png', 'https://www.jiandati.com/', '安全、高效试题答案检索服务', '搜索资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:39:07', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-993947647, 'NO视频', 'http://images.newstar.net.cn/img/image-20201214085750707.png', 'https://www.novipnoad.com/', '没有广告看电影视频', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838783, '电影推荐', 'http://images.newstar.net.cn/img/image-20201214085712112.png', 'https://www.mvcat.com/', '分类搜索电影', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838782, '疯狂影视搜索', 'http://images.newstar.net.cn/img/image-20201215194753192.png', 'http://ifkdy.com/', '根据影视关键字搜索', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838781, '牛牛TV', 'http://images.newstar.net.cn/img/image-20201214085419139.png', 'http://www.ziliao6.com/tv/', '根据影视名称搜索', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838780, '在线看剧', 'http://images.newstar.net.cn/img/image-20201214085357538.png', 'http://dy.27234.cn/', '搜索影视', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838779, '视屏鱼', 'http://images.newstar.net.cn/img/image-20201214085307102.png', 'http://www.shipinyu.com/', '视屏下载', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838778, 'APP影院', 'http://images.newstar.net.cn/img/image-20201214085212884.png', 'https://app.movie/', '在线影视', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838777, '人人影视', 'http://images.newstar.net.cn/img/image-20201214085024320.png', 'http://www.yyetss.com/', '在线看剧', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838776, '酷绘视屏', 'http://images.newstar.net.cn/img/image-20201214084749903.png', 'http://www.kuhuiv.com/', '在线看剧', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838775, '去看TV', 'http://images.newstar.net.cn/img/image-20201214084658852.png', 'https://www.qukantv.net/', '在线看剧', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838774, '神马电影网', 'http://images.newstar.net.cn/img/image-20201214084526596.png', 'http://www.92xiaoyouxi.com/', '影视在线看', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838773, '爱爱客电影网', 'http://images.newstar.net.cn/img/image-20201214084300191.png', 'http://www.yc2050.com/', '免费影视', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838772, 'neets搜索站', 'http://images.newstar.net.cn/img/image-20201214084104201.png', 'https://neets.cc/', '影视作品', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838771, 'ZzzFun', 'http://images.newstar.net.cn/img/image-20201213233905994.png', 'http://www.zzzfun.com/', '动漫视屏网', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838770, '完美看看', 'http://images.newstar.net.cn/img/image-20210118204603046.png', 'https://www.wanmeikk.me/', '免费高清电影、电视剧', '娱乐资源', '影视剧', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838769, '歌曲大全', 'http://images.newstar.net.cn/img/image-20201213233724237.png', 'http://www.gequdaquan.net/gqss/', '音乐聚合搜索引擎', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838768, '无损生活', 'http://images.newstar.net.cn/img/image-20201213233652814.png', 'https://flac.life/', '无损音乐下载，很赞', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838767, '天天静听', 'http://images.newstar.net.cn/img/image-20201213233604114.png', 'http://47.112.23.238/', '在线下载无损音乐', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838766, 'MS-魔声', 'http://images.newstar.net.cn/img/image-20201213233539595.png', 'http://music.moresound.tk/', '解析VIP音乐免费下载', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838765, '疯狂音乐搜索', 'http://images.newstar.net.cn/img/image-20201215201016976.png', 'http://music.ifkdy.com/', '多平台音乐聚合型网站', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838764, '音乐搜索器', 'http://images.newstar.net.cn/img/image-20201213233221756.png', 'http://www.musictool.top/', '多站合一音乐搜索', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838763, 'muxiv', 'http://images.newstar.net.cn/img/image-20201213233127406.png', 'https://muxiv.com/sc/', '好像是网易的另一个版本', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838762, '一起听音乐呀', 'http://images.newstar.net.cn/img/image-20201213233031208.png', 'http://music.jsososo.com/', '在线听歌及下载', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838761, 'MyFreeMP3', 'http://images.newstar.net.cn/img/image-20201213232930273.png', 'http://tool.liumingye.cn/music/', '在线音乐、视频解析', '娱乐资源', '听音乐', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838760, '电视直播', 'http://images.newstar.net.cn/img/image-20201213233803097.png', 'http://ivi.bupt.edu.cn/', '在线看电视', '娱乐资源', '直播类', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838759, '足球巴巴', 'http://images.newstar.net.cn/img/image-20210118204236024.png', 'http://www.nba01.com/', 'NBA直播|足球直播|体育直播', '娱乐资源', '直播类', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838758, '心理FM', 'http://images.newstar.net.cn/img/image-20201213232847719.png', 'http://fm.xinli001.com/', '治愈系网络电台', '娱乐资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-926838757, '逗比拯救世界', 'http://images.newstar.net.cn/img/image-20201213232831880.png', 'http://www.dbbqb.com/', '丰富的表情包', '娱乐资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:35:12', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522302, '爱资料工具', 'http://images.newstar.net.cn/img/aiziliao.jpg', 'http://www.toolnb.com/', '提供开发上的便捷工具', '工具资源', '工具类', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522301, '精准云工具', 'http://images.newstar.net.cn/img/image-20201213222715460.png', 'https://jingzhunyun.com/', '在线工具导航站', '工具资源', '工具类', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522300, 'ToolFK', 'http://images.newstar.net.cn/img/image-20201213222609790.png', 'https://www.toolfk.com/', '在线开发工具箱', '工具资源', '工具类', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522299, '工具123', 'http://images.newstar.net.cn/img/image-20201213222420203.png', 'http://www.gjw123.com/', '在线工具大全', '工具资源', '工具类', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522298, '小心点', 'http://images.newstar.net.cn/img/image-20201213222315932.png', 'https://www.shulijp.com/index.html', '在线工具站', '工具资源', '工具类', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522297, 'BigJPG', 'http://images.newstar.net.cn/img/image-20201213222154094.png', 'https://bigjpg.com/zh', '图片无损放大', '工具资源', '图片处理', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522296, '图贴士', 'http://images.newstar.net.cn/img/image-20201213221940425.png', 'https://www.tutieshi.com/', 'GIF工具', '工具资源', '图片处理', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522295, 'IMGBOT', 'http://images.newstar.net.cn/img/image-20201213221101658.png', 'https://www.imgbot.ai/', '在线图片处理', '工具资源', '图片处理', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522294, 'docsmall', 'http://images.newstar.net.cn/img/image-20201214131219401.png', 'https://docsmall.com/', '在线文件处理', '工具资源', '图片处理', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522293, 'yaml与properties互转', 'http://images.newstar.net.cn/img/image-20201224192142951.png', 'https://www.toyaml.com/index.html', 'yaml与properties文件在线转换工具', '工具资源', '转化工具', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522292, '幕布', 'http://images.newstar.net.cn/img/image-20201213220753486.png', 'https://mubu.com/', '大纲一键生成思维导图', '工具资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522291, '谷歌镜像大全', 'http://images.newstar.net.cn/img/guge.jpg', 'https://www.uedbox.com/post/54776/', '维护率最高、可用率最好的谷歌镜像', '工具资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-423522290, '草料二维码', 'http://images.newstar.net.cn/img/image-20201213212745140.png', 'https://cli.im/', '二维码生成工具', '工具资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:40:10', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-289304575, 'B站', 'http://images.newstar.net.cn/img/image-20201214160701358.png', 'https://www.bilibili.com/', '众所周知，B站是一个学习网站', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-289304574, '慕课网', 'http://images.newstar.net.cn/img/image-20201214160721192.png', 'https://www.imooc.com/', '互联网IT技能免费学习网站', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-289304573, '学堂在线', 'http://images.newstar.net.cn/img/image-20201214160744900.png', 'https://www.xuetangx.com/', '中文大规模开发在线课程', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-289304572, '大学资源网', 'http://images.newstar.net.cn/img/image-20201214160820923.png', 'http://www.dxzy163.com/', '考研、外语、电脑等课程视频', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195710, 'MOOC中国', 'http://images.newstar.net.cn/img/image-20201214161128810.png', 'https://www.mooc.cn/', '致力于分享最好的慕课', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195709, '极客学院', 'http://images.newstar.net.cn/img/image-20201214161148780.png', 'https://www.jikexueyuan.com/', '国内领先的IT在线咨询及教育平台', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195708, '网易公开课', 'http://images.newstar.net.cn/img/image-20201214161215628.png', 'https://open.163.com/', '一个公开的免费课程平台', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195707, '译学馆', 'http://images.newstar.net.cn/img/image-20201214161315931.png', 'https://www.yxgapp.com/', '一个专注于译制知识视频的平台', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195706, 'doyoudo', 'http://images.newstar.net.cn/img/image-20201214161355991.png', 'https://www.doyoudo.com/', '设计领域在线学习平台', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195705, '我要自学网', 'http://images.newstar.net.cn/img/image-20201214161440036.png', 'https://www.51zxw.net/', '提高全民的电脑水平', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195704, 'TED', 'http://images.newstar.net.cn/img/1607961043(1).jpg', 'https://www.ted.com/', '最优质的演讲', '学习资源', '视屏类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-222195703, '菜鸟教程', 'http://images.newstar.net.cn/img/image-20201214161505432.png', 'https://www.runoob.com/', '提供了基础编程技术教程', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281151, '易百教程', 'http://images.newstar.net.cn/img/image-20201214155702735.png', 'https://www.yiibai.com/', 'IT技术入门学习实例教程网站', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281150, '码农教程', 'http://images.newstar.net.cn/img/image-20201214155610415.png', 'http://www.manongjc.com/', 'IT免费学习平台', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281149, '简单教程', 'http://images.newstar.net.cn/img/image-20201214155526681.png', 'https://www.twle.cn/', '简洁明了的IT教程', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281148, '知乎', 'http://images.newstar.net.cn/img/image-20201214155331548.png', 'https://www.zhihu.com/', '国内最大的知识问答平台', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281147, '简书', 'http://images.newstar.net.cn/img/image-20201214155253763.png', 'https://www.jianshu.com/', '一个优质的创作社区', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281146, 'CSDN', 'http://images.newstar.net.cn/img/image-20201214155219293.png', 'https://www.csdn.net/', '国内最大的IT技术交流平台', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281145, '掘金', 'http://images.newstar.net.cn/img/image-20201214155132395.png', 'https://juejin.cn/', '一个帮助开发者成长的社区', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281144, '博客园', 'http://images.newstar.net.cn/img/image-20201214155037052.png', 'https://www.cnblogs.com/', '简洁大气的阅读体验', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281143, '开源中国', 'http://images.newstar.net.cn/img/image-20201214154909714.png', 'https://www.oschina.net/', '国内最大的开源技术社区', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281142, '思否', 'http://images.newstar.net.cn/img/image-20201214154823027.png', 'https://segmentfault.com/', '交流和分享任何技术编程相关知识', '学习资源', '文章类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281141, '书栈网', 'http://images.newstar.net.cn/img/image-20201214154747581.png', 'https://www.bookstack.cn/', '一个开源书籍和文档分享站点', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281140, '鸠摩搜书', 'http://images.newstar.net.cn/img/image-20201214154702245.png', 'https://www.jiumodiary.com/', '文档书籍搜索引擎', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-159281139, '云海电子图书馆', 'http://images.newstar.net.cn/img/image-20201214154342600.png', 'http://www.pdfbook.cn/', '各类书籍免费下载', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172286, '码农之家', 'http://images.newstar.net.cn/img/image-20201214153632698.png', 'https://www.xz577.com/', '计算机电子书下载网站', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172285, '书格', 'http://images.newstar.net.cn/img/image-20201214153557305.png', 'https://new.shuge.org/', '在线古籍图书', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172284, '时宜搜书', 'http://images.newstar.net.cn/img/image-20201214153507534.png', 'https://www.shiyisoushu.com/', '专业电子书搜索引擎', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172283, '知识库', 'http://images.newstar.net.cn/img/image-20201214153345514.png', 'https://book.zhishikoo.com/', '书籍、电子书分享的资源网站', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172282, 'LoreFree', 'http://images.newstar.net.cn/img/image-20201214153303673.png', 'https://ebook2.lorefree.com/', '各种格式电子书免费下载', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172281, '书享家', 'http://images.newstar.net.cn/img/image-20201214153215116.png', 'http://shuxiangjia.cn/', '电子书下载导航', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172280, '电子书搜索', 'http://images.newstar.net.cn/img/image-20201214153059539.png', 'https://ebook.chongbuluo.com/', '提供各种电子书搜索网站', '学习资源', '书籍类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172279, '力扣', 'http://images.newstar.net.cn/img/image-20201214152947095.png', 'https://leetcode-cn.com/', '海量技术面试资源、算法题', '学习资源', '刷题类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172278, '牛客网', 'http://images.newstar.net.cn/img/image-20201214152718579.png', 'https://www.nowcoder.com/', '笔面试、题库、课程、招聘内推', '学习资源', '刷题类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172277, 'LintCode', 'http://images.newstar.net.cn/img/image-20201214152554992.png', 'https://www.lintcode.com/', '在线编程训练系统', '学习资源', '刷题类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172276, 'algorithm', 'http://images.newstar.net.cn/img/image-20201214152506414.png', 'https://algorithm-visualizer.org/', '动态算法演示', '学习资源', '刷题类', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (-92172275, '实验楼', 'http://images.newstar.net.cn/img/image-20201214152422403.png', 'https://www.shiyanlou.com/', '实训平台，提供编程环境', '学习资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:30:25', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423938, '码力全开', 'http://images.newstar.net.cn/img/image-20201213232817491.png', 'https://www.maliquankai.com/', '产品/设计/独立开发者的资源库', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423939, 'ICONS8', 'http://images.newstar.net.cn/img/image-20201213232802099.png', 'https://icons8.cn/', '图标、插图、照片、音乐设计工具', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423940, 'MAKA', 'http://images.newstar.net.cn/img/image-20201213232542666.png', 'https://www.maka.im/', '在线设计', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423941, 'LOGO神器', 'http://images.newstar.net.cn/img/image-20201213232428931.png', 'https://www.logosc.cn/', '在线人工智能logo设计', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423942, '钙网', 'http://images.newstar.net.cn/img/image-20201213232329113.png', 'http://www.uugai.com/', '免费的LOGO在线设计制作工具', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423943, '搞定设计', 'http://images.newstar.net.cn/img/image-20201217211440498.png', 'https://www.gaoding.com/', '商业视觉在线设计平台', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423944, '创客贴', 'http://images.newstar.net.cn/img/image-20201217211534748.png', 'https://www.chuangkit.com/', '在线设计平台', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423945, 'Fotor懒设计', 'http://images.newstar.net.cn/img/image-20201217211613795.png', 'https://www.fotor.com.cn/', '图片编辑和平面设计产品', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423946, 'Canva', 'http://images.newstar.net.cn/img/image-20201217211655047.png', 'https://www.canva.cn/', '在线平面设计平台', '设计资源', '设计类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423947, 'iconfont', 'http://images.newstar.net.cn/img/image-20201213232257546.png', 'https://www.iconfont.cn/', '丰富的矢量图标管理', '设计资源', '图标类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423948, 'easyicon', 'http://images.newstar.net.cn/img/image-20201213232233959.png', 'https://www.easyicon.net/', '图标搜索下载网站', '设计资源', '图标类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423949, '360查字体', 'http://images.newstar.net.cn/img/image-20201213232148519.png', 'https://fonts.safe.360.cn/', '查看字体能否商用', '设计资源', '字体类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423950, '字由', 'http://images.newstar.net.cn/img/image-20201213232036880.png', 'https://www.hellofont.cn/', '字体应用管理神器', '设计资源', '字体类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423951, '第一字体', 'http://images.newstar.net.cn/img/image-20201213232012134.png', 'http://www.diyiziti.com/', '书法字体在线转换生成器', '设计资源', '字体类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (352423952, '100font', 'http://images.newstar.net.cn/img/image-20201213231958882.png', 'https://www.100font.com/', '免费商用字体', '设计资源', '字体类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532801, 'Clipchamp', 'http://images.newstar.net.cn/img/image-20201213231908159.png', 'https://clipchamp.com/zh-hans/video-editor/', '专业免费在线视频编辑器', '设计资源', '音视频类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532802, '爱给', ' http://images.newstar.net.cn/img/image-20201213231757497.png', 'https://www.aigei.com/', '音效配乐、视频素材', '设计资源', '音视频类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532803, 'NEWCGER', 'http://images.newstar.net.cn/img/image-20201213231722473.png', 'https://www.newcger.com/', '视频素材免费下载', '设计资源', '音视频类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532804, 'mixkit', 'http://images.newstar.net.cn/img/image-20201213231255048.png', 'https://mixkit.co/', '免费音视频模板', '设计资源', '音视频类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532805, '稿定', 'http://images.newstar.net.cn/img/image-20201217211725189.png', 'https://ps.gaoding.com/#/', '稿定设计在线PS', '设计资源', '在线PS', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532806, 'Photopea', 'http://images.newstar.net.cn/img/image-20201217211842276.png', 'https://www.photopea.com/', '高级图片编辑器', '设计资源', '在线PS', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532807, '在线PS', 'http://images.newstar.net.cn/img/image-20201217211924558.png', 'https://www.uupoop.com/', '在线PS', '设计资源', '在线PS', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532808, 'removebg', 'http://images.newstar.net.cn/img/image-20201217212043606.png', 'https://www.remove.bg/zh', '消除图片中的背景', '设计资源', '抠图类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532809, '稿定抠图', 'http://images.newstar.net.cn/img/image-20201217211440498.png', 'https://koutu.gaoding.com/', '在线删除图片背景', '设计资源', '抠图类', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532810, 'JING.FM', 'http://images.newstar.net.cn/img/image-20201213231153129.png', 'https://www.jing.fm/', '免费库存剪贴画和剪影', '设计资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532811, '花瓣网', 'http://images.newstar.net.cn/img/image-20201217211326049.png', 'https://huaban.com/', '做生活的设计师', '设计资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532812, 'unDraw', 'http://images.newstar.net.cn/img/image-20201217211234793.png', 'https://undraw.co/illustrations', '插图下载', '设计资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (419532813, 'MAGIC', 'http://images.newstar.net.cn/img/image-20201217213242903.png', 'https://magicmockups.com/', '将产品放在逼真的环境中', '设计资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:37:38', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105602, '电脑壁纸', 'http://images.newstar.net.cn/img/image-20201214152059669.png', 'http://lcoc.top/bizhi/', '高清电脑壁纸', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105603, '彼岸图网', 'http://images.newstar.net.cn/img/image-20201214151818037.png', 'http://pic.netbian.com/', '4K超清图片', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105604, '极简壁纸', 'http://images.newstar.net.cn/img/image-20201214151745052.png', 'https://bz.zzzmh.cn/', '海量电脑桌面4K超清壁纸', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105605, '搜图神器', 'http://images.newstar.net.cn/img/image-20201214151653173.png', 'https://www.logosc.cn/so/', '免费版权图片一键搜索', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105606, 'Pexels', 'http://images.newstar.net.cn/img/image-20201214150902163.png', 'https://www.pexels.com/zh-cn/', '免费图片素材', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105607, 'StockSnap', 'http://images.newstar.net.cn/img/image-20201214150800370.png', 'https://stocksnap.io/', '免费图片素材高清资源库', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105608, 'Unsplash', 'http://images.newstar.net.cn/img/image-20201214150600302.png', 'https://unsplash.com/', '免费无版权高清图片', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105609, 'Pixabay', 'http://images.newstar.net.cn/img/image-20201215191445494.png', 'https://pixabay.com/zh/', '免费正版高清图片素材库', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105610, 'visualhunt', 'http://images.newstar.net.cn/img/image-20201214144200466.png', 'https://visualhunt.com/', '可以通过颜色来查找图片', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (734105611, 'wallhaven', 'http://images.newstar.net.cn/img/image-20210924002603371.png', 'https://wallhaven.cc/', '号称网上最好的图片网站', '图片资源', '图片类', 2, '2023-06-05 11:42:10', '2023-06-05 11:32:04', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901185, '文件转换器', 'http://images.newstar.net.cn/img/image-20201214143834268.png', 'https://convertio.co/zh/', '任意文件格式转换', '办公资源', '文件转换器', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901186, 'All To All', 'http://images.newstar.net.cn/img/image-20201214143748540.png', 'https://www.alltoall.net/', '在线格式转换', '办公资源', '文件转换器', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901187, 'Office-Converter', 'http://images.newstar.net.cn/img/image-20201214143600571.png', 'https://cn.office-converter.com/', '在线文件转换器', '办公资源', '文件转换器', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901188, 'DocTranslator', 'http://images.newstar.net.cn/img/image-20201214134038067.png', 'https://www.onlinedoctranslator.com/zh-CN/', '免费在线文件翻译器', '办公资源', '文件转换器', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901189, 'lightPDF', 'http://images.newstar.net.cn/img/image-20201214133158808.png', 'https://lightpdf.com/', '在线PDF编辑和转换', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901190, 'Smallpdf', 'http://images.newstar.net.cn/img/image-20201214133031770.png', 'https://smallpdf.com/cn', '简单好用的线上PDF工具', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901191, 'PDF派', 'http://images.newstar.net.cn/img/image-20201214132938596.png', 'https://www.pdfpai.com/', '在线PDF免费工具', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901192, '迅捷PDF转换器', 'http://images.newstar.net.cn/img/image-20201214132859707.png', 'https://app.xunjiepdf.com/', 'PDF文件转换器', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901193, 'ilovepdf', 'http://images.newstar.net.cn/img/image-20201214132812862.png', 'https://www.ilovepdf.com/zh-cn', '丰富的PDF处理工具', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901194, 'hipdf', 'http://images.newstar.net.cn/img/image-20201214132728567.png', 'https://www.hipdf.cn', '一站式在线PDF解决方案', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901195, 'PDF24 Tools', 'http://images.newstar.net.cn/img/image-20201214132634402.png', 'https://tools.pdf24.org/zh/', '免费且易于使用的在线PDF工具', '办公资源', 'PDF编辑', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901196, '优品PPT', 'http://images.newstar.net.cn/img/image-20201214132527742.png', 'http://www.ypppt.com/', '免费PPT模板下载', '办公资源', 'PPT模板', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901197, 'PPT超级市场', 'http://images.newstar.net.cn/img/image-20201214132346986.png', 'http://ppt.sotary.com/web/wxapp/index.html', '免费、优质、高效、安全PPT下载', '办公资源', 'PPT模板', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901198, '第一PPT', 'http://images.newstar.net.cn/img/image-20201214132305062.png', 'http://www.1ppt.com/', 'PPT免费模板下载', '办公资源', 'PPT模板', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901199, 'PPT宝藏', 'http://images.newstar.net.cn/img/image-20201214132225542.png', 'http://www.pptbz.com/', '高质量的PPT模板下载', '办公资源', 'PPT模板', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901200, 'Processon', 'http://images.newstar.net.cn/img/image-20201214132143784.png', 'https://www.processon.com/', '免费在线作图，实时协作', '办公资源', '在线作图', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901201, '流程图', 'http://images.newstar.net.cn/img/image-20201214132106075.png', 'https://www.liuchengtu.com/lct/', '免费在线创建流程图', '办公资源', '在线作图', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901202, 'Untitled Diagram', 'http://images.newstar.net.cn/img/image-20201214131933284.png', 'https://app.diagrams.net/', '在线制作流程图', '办公资源', '在线作图', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901203, '文叔叔传文件', 'http://images.newstar.net.cn/img/image-20201214131822963.png', 'https://www.wenshushu.cn/', '大文件传输，不限速', '办公资源', '文件传输', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901204, '拷贝兔', 'http://images.newstar.net.cn/img/image-20201214131515776.png', 'https://cp.anyknew.com/', '快速分享的实用工具', '办公资源', '文件传输', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901205, 'AirPortal', 'http://images.newstar.net.cn/img/image-20201214131420640.png', 'https://airportal.cn/', '文件在线接收', '办公资源', '文件传输', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901206, '福昕在线编辑器', 'http://images.newstar.net.cn/img/image-20201214131347480.png', 'https://edit.foxitcloud.cn/', 'PDF在线编辑', '办公资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901207, '网易见外工作台', 'http://images.newstar.net.cn/img/image-20201214131014043.png', 'https://jianwai.youdao.com/', '视频文档处理，可添加字幕', '办公资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901208, 'EasyScreenOCR', 'http://images.newstar.net.cn/img/image-20201214130935817.png', 'https://online.easyscreenocr.com/zh', '免费在线将图像转换为纯文本', '办公资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901209, '夺目', 'http://images.newstar.net.cn/img/image-20201214130922099.png', 'https://duomu.tv/', '企业视频在线制作工具', '办公资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);
INSERT INTO `tb_sys_data_bank` VALUES (1031901210, 'Apowersoft', 'http://images.newstar.net.cn/img/image-20201214130645063.png', 'https://www.apowersoft.cn/free-online-screen-recorder', '免费在线录屏', '办公资源', '其他', 2, '2023-06-05 11:42:10', '2023-06-05 11:33:02', 1);

-- ----------------------------
-- Table structure for tb_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_permission`;
CREATE TABLE `tb_sys_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `p_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sys_permission
-- ----------------------------
INSERT INTO `tb_sys_permission` VALUES (1, 0, 'Dashboard');
INSERT INTO `tb_sys_permission` VALUES (2, 0, '管理模块');
INSERT INTO `tb_sys_permission` VALUES (3, 0, '系统管理');
INSERT INTO `tb_sys_permission` VALUES (4, 1, 'Dashboard');
INSERT INTO `tb_sys_permission` VALUES (5, 1, '发布博客');
INSERT INTO `tb_sys_permission` VALUES (6, 2, '博客管理');
INSERT INTO `tb_sys_permission` VALUES (7, 2, '评论管理');
INSERT INTO `tb_sys_permission` VALUES (8, 2, '分类管理');
INSERT INTO `tb_sys_permission` VALUES (9, 2, '标签管理');
INSERT INTO `tb_sys_permission` VALUES (10, 2, '友情链接');
INSERT INTO `tb_sys_permission` VALUES (11, 3, '角色管理');
INSERT INTO `tb_sys_permission` VALUES (12, 3, '系统配置');
INSERT INTO `tb_sys_permission` VALUES (13, 3, '个人信息');
INSERT INTO `tb_sys_permission` VALUES (14, 3, '安全退出');
INSERT INTO `tb_sys_permission` VALUES (15, 3, '用户管理');
INSERT INTO `tb_sys_permission` VALUES (16, 6, '博客审核');
INSERT INTO `tb_sys_permission` VALUES (17, 12, '站点信息');
INSERT INTO `tb_sys_permission` VALUES (18, 12, '底部设置');
INSERT INTO `tb_sys_permission` VALUES (19, 3, '博客封面');

-- ----------------------------
-- Table structure for tb_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_resource`;
CREATE TABLE `tb_sys_resource`  (
  `id` int NOT NULL COMMENT '个性资源表',
  `type` int NULL DEFAULT NULL COMMENT '类型 1.点击 2.滑动 3.背景',
  `point` int NULL DEFAULT NULL COMMENT '换购所需积分·',
  `file_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '资源id',
  `img` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预览图',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_resource
-- ----------------------------
INSERT INTO `tb_sys_resource` VALUES (1, 1, 1000, '20230525001', '\r\nhttp://qny.xiazy.xyz/blog/47ddfbf7a4274da189ce3e5435574b96_tplv-k3u1fbpfcp-zoom-in-crop-mark_1512_0_0_0.webp', '2023-05-25 17:09:03', NULL, '鼠标点击爱心');
INSERT INTO `tb_sys_resource` VALUES (2, 1, 1000, '20230525002', 'http://qny.xiazy.xyz/blog/413c7d2801e34e3c87e9da0b6d05c263_tplv-k3u1fbpfcp-zoom-in-crop-mark_1512_0_0_0.webp', '2023-05-25 17:10:52', NULL, '鼠标点击烟花');
INSERT INTO `tb_sys_resource` VALUES (3, 3, 1000, '20230525004', '\r\nhttp://qny.xiazy.xyz/blog/0b260f12c097412dbb817a9f093c24ff_tplv-k3u1fbpfcp-zoom-in-crop-mark_1512_0_0_0.webp', '2023-05-26 09:44:06', NULL, '花瓣飘落背景');
INSERT INTO `tb_sys_resource` VALUES (4, 1, 1000, '20230525005', 'http://qny.xiazy.xyz/blog/315e0f7de74f41d4a0a9e4b375e0c31d_tplv-k3u1fbpfcp-zoom-in-crop-mark_1512_0_0_0.webp', '2023-05-26 09:45:15', NULL, '鼠标点击爱国');
INSERT INTO `tb_sys_resource` VALUES (5, 2, 1000, '20230525006', 'http://qny.xiazy.xyz/blog/b651bf939ccd420392bfd3a60ac6ff27_tplv-k3u1fbpfcp-zoom-in-crop-mark_1512_0_0_0.webp', '2023-05-26 09:46:13', NULL, '鼠标尾随蜘蛛网');
INSERT INTO `tb_sys_resource` VALUES (6, 4, 100, 'https://unpkg.com/live2d-widget-model-hijiki@1.0.5/assets/hijiki.model.json', 'http://qny.xiazy.xyz/blog/cat.png', '2023-05-31 14:01:49', NULL, '黑猫');
INSERT INTO `tb_sys_resource` VALUES (7, 4, 150, 'https://cdn.jsdelivr.net/npm/live2d-widget-model-wanko@1.0.5/assets/wanko.model.json', 'http://qny.xiazy.xyz/blog/dog.png', '2023-05-31 14:01:49', NULL, '茶杯犬');
INSERT INTO `tb_sys_resource` VALUES (8, 4, 150, 'https://unpkg.com/live2d-widget-model-miku@1.0.5/assets/miku.model.json', 'http://qny.xiazy.xyz/blog/%E5%88%9D%E9%9F%B3%E6%9C%AA%E6%9D%A5.png', '2023-06-01 16:35:33', NULL, '初音未来');
INSERT INTO `tb_sys_resource` VALUES (9, 4, 150, 'https://unpkg.com/live2d-widget-model-haruto@1.0.5/assets/haruto.model.json', 'http://qny.xiazy.xyz/blog/%E5%B0%8F%E8%90%8C%E7%94%B7.png', '2023-06-01 16:36:56', NULL, '小萌男');
INSERT INTO `tb_sys_resource` VALUES (10, 4, 150, 'https://unpkg.com/live2d-widget-model-koharu@1.0.5/assets/koharu.model.json', 'http://qny.xiazy.xyz/blog/%E5%B0%8F%E8%90%8C%E5%A6%B9.png', '2023-06-01 16:37:42', NULL, '小萌妹');
INSERT INTO `tb_sys_resource` VALUES (11, 4, 150, 'https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json', 'http://qny.xiazy.xyz/blog/%E5%A4%A7%E8%90%8C%E5%A6%B9.png', '2023-06-01 16:40:46', NULL, '大萌妹');

-- ----------------------------
-- Table structure for tb_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role`;
CREATE TABLE `tb_sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_role
-- ----------------------------
INSERT INTO `tb_sys_role` VALUES (1, '系统管理员', '1', '2023-04-24 09:35:43', NULL, NULL, NULL);
INSERT INTO `tb_sys_role` VALUES (2, '基础角色', NULL, '2023-04-26 18:07:07', 'admin', '2023-04-26 18:07:07', NULL);
INSERT INTO `tb_sys_role` VALUES (11, '博客封面管理员', NULL, '2023-05-10 17:46:59', 'admin', '2023-05-10 17:46:59', NULL);

-- ----------------------------
-- Table structure for tb_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_permission`;
CREATE TABLE `tb_sys_role_permission`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` int NULL DEFAULT NULL,
  `permission_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_id`(`role_id` ASC) USING BTREE,
  INDEX `fk_permission_id`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `fk_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `tb_sys_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `tb_sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sys_role_permission
-- ----------------------------
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272901541889', 2, 1);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272901541890', 2, 5);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272901541891', 2, 2);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272930902018', 2, 6);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272930902019', 2, 3);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272930902020', 2, 13);
INSERT INTO `tb_sys_role_permission` VALUES ('1654736272930902021', 2, 14);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870691569666', 1, 1);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870691569667', 1, 4);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870725124097', 1, 5);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318402', 1, 2);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318403', 1, 6);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318404', 1, 16);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318405', 1, 7);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318406', 1, 8);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870729318407', 1, 9);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038658', 1, 10);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038659', 1, 3);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038660', 1, 11);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038661', 1, 12);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038662', 1, 17);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038663', 1, 18);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038664', 1, 13);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870788038665', 1, 14);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870855147522', 1, 15);
INSERT INTO `tb_sys_role_permission` VALUES ('1656231870855147523', 1, 19);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459323723777', 11, 1);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459323723778', 11, 4);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459323723779', 11, 5);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083905', 11, 2);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083906', 11, 6);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083907', 11, 16);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083908', 11, 3);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083909', 11, 13);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459353083910', 11, 14);
INSERT INTO `tb_sys_role_permission` VALUES ('1656234459420192770', 11, 19);

-- ----------------------------
-- Table structure for tb_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_role`;
CREATE TABLE `tb_sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色和用户关联表',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rloe_id`(`role_id` ASC) USING BTREE,
  INDEX `fk_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_rloe_id` FOREIGN KEY (`role_id`) REFERENCES `tb_sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_admin_user` (`admin_user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_user_role
-- ----------------------------
INSERT INTO `tb_sys_user_role` VALUES (15, 1, 2, '2023-04-28 16:58:29');
INSERT INTO `tb_sys_user_role` VALUES (16, 2, 2, '2023-04-28 16:58:29');
INSERT INTO `tb_sys_user_role` VALUES (157, 2, 137, '2023-05-26 14:47:57');

-- ----------------------------
-- Table structure for tb_test
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `test_info` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测试内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES (1, 'SpringBoot-MyBatis测试');

-- ----------------------------
-- Table structure for tb_user_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_comment`;
CREATE TABLE `tb_user_comment`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `createTime` datetime NOT NULL,
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parentCommentId` int NULL DEFAULT NULL,
  `replyCommentId` int NULL DEFAULT NULL,
  `remind` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  INDEX `parentCommentId`(`parentCommentId` ASC) USING BTREE,
  INDEX `replyCommentId`(`replyCommentId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_comment
-- ----------------------------
INSERT INTO `tb_user_comment` VALUES (76, 2, '2023-06-02 16:59:41', '12121212', NULL, NULL, 0);
INSERT INTO `tb_user_comment` VALUES (77, 2, '2023-06-02 16:59:47', '12121212', 76, 76, 0);

-- ----------------------------
-- Table structure for tb_user_music
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_music`;
CREATE TABLE `tb_user_music`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '音乐表',
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '播放路径',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_music
-- ----------------------------
INSERT INTO `tb_user_music` VALUES (64, '怎么了', '伦桑', 'http://music.163.com/song/media/outer/url?id=2041198788.mp3', 'http://p2.music.126.net/p0U0kKd6bl6qiFmlzZhBJg==/109951168571530432.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (65, '可能', '程响', 'http://music.163.com/song/media/outer/url?id=1950343972.mp3', 'http://p2.music.126.net/Lk26KqRDPVbbhB4rQRGnPw==/109951167570919875.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (66, '窗', '虎二', 'http://music.163.com/song/media/outer/url?id=1847250546.mp3', 'http://p2.music.126.net/CJAjJcG4N0_tSww1CX9k_w==/109951166004493776.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (67, '多年后再见你', '乔洋', 'http://music.163.com/song/media/outer/url?id=1804057877.mp3', 'http://p2.music.126.net/qdMUBbDqLRc0dXKUKyDX_w==/109951165538245848.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (68, '等风雪', '姜姜', 'http://music.163.com/song/media/outer/url?id=1887936394.mp3', 'http://p2.music.126.net/ebtpYmm-2vGjtw3tl-VdGw==/109951166531797660.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (69, '三国恋', '王巨星', 'http://music.163.com/song/media/outer/url?id=1947948874.mp3', 'http://p2.music.126.net/fw5f7Gifm_vCirn4Hdo2jQ==/109951167435417002.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (70, '惊鸿醉', '王麻花', 'http://music.163.com/song/media/outer/url?id=2005519969.mp3', 'http://p1.music.126.net/r4CD1S2m1EUp6WtZLhNyFg==/109951168132925405.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (71, '怎随天下', '王若熙', 'http://music.163.com/song/media/outer/url?id=1818094034.mp3', 'http://p1.music.126.net/o_LPKwNJu_oK_P0Ruw2WNg==/109951165699067266.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (72, '红色高跟鞋', '王小帅', 'http://music.163.com/song/media/outer/url?id=1449401449.mp3', 'http://p1.music.126.net/YxaTi-1u3mKZnqMvNArApQ==/109951165000993257.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (73, '武为', '要不要买菜', 'http://music.163.com/song/media/outer/url?id=1834142931.mp3', 'http://p1.music.126.net/TYf4Z7-Fn0nVV5sxFatV6Q==/109951165852969953.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (74, '师妹', '鸾音社', 'http://music.163.com/song/media/outer/url?id=1426021340.mp3', 'http://p1.music.126.net/Noh09vnQaRZ8qASBYaeVgA==/109951164741494109.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (75, '虚无', 'Zic子晨', 'http://music.163.com/song/media/outer/url?id=1985709585.mp3', 'http://p1.music.126.net/bSnqB67UiLEOE6tchIo1oA==/109951167920720461.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (76, '武当', '等什么君(邓寓君)', 'http://music.163.com/song/media/outer/url?id=1474653078.mp3', 'http://p1.music.126.net/3gqKmqwUY4-nh6onNq5l3w==/109951165277189701.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (77, '恨离别', '柯柯柯啊', 'http://music.163.com/song/media/outer/url?id=1983451461.mp3', 'http://p1.music.126.net/QnCKLi3RbWFEZ5_uoVHLRA==/109951168152576537.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (78, '日不落', '藤柒吖', 'http://music.163.com/song/media/outer/url?id=1478626435.mp3', 'http://p1.music.126.net/uqkzFodkVRPt9C4_7CDEoA==/109951165311403110.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (79, '请你吃个冰激凌', '花粥', 'http://music.163.com/song/media/outer/url?id=1357999885.mp3', 'http://p1.music.126.net/H6dt7IgvXNWhRM_w7XbcqQ==/109951163990575387.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (80, '若把你', '蛋总', 'http://music.163.com/song/media/outer/url?id=1840700016.mp3', 'http://p1.music.126.net/PyTjq22uOegIOGxoJ_T_iQ==/109951165928680369.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (81, '我的果汁分你一半', '李金源', 'http://music.163.com/song/media/outer/url?id=1422212205.mp3', 'http://p1.music.126.net/k0C0pZG4Soq86gXfXy38QQ==/109951164742942259.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (82, '记·念', '不是花火呀', 'http://music.163.com/song/media/outer/url?id=1949119429.mp3', 'http://p1.music.126.net/czSu2Irehg4h_H0urEGEPQ==/109951167466878408.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (83, '彩色翅膀·2022', 'Sasablue', 'http://music.163.com/song/media/outer/url?id=1992034569.mp3', 'http://p1.music.126.net/lfVb0tSVBpm3OugmgKqEuA==/109951168024932764.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (84, '巡光', '就是南方凯（项学凯）', 'http://music.163.com/song/media/outer/url?id=1985027951.mp3', 'http://p1.music.126.net/WQOZeZE97qX4AJ04Wj068A==/109951167914385713.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (85, '爱人错过', '初月', 'http://music.163.com/song/media/outer/url?id=1815759242.mp3', 'http://p1.music.126.net/L-6FYIDjMmrrOI-a_hT8CQ==/109951165554944740.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (86, '好男人都死哪儿去了', '要不要买菜', 'http://music.163.com/song/media/outer/url?id=1464465276.mp3', 'http://p1.music.126.net/yE9c--nVNJ03x_rbPRpmcw==/109951165156913555.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (87, '大田後生仔', '王玉萌', 'http://music.163.com/song/media/outer/url?id=1396602869.mp3', 'http://p1.music.126.net/hITz5arBFLYC4_3D1gu2qw==/109951164423988553.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (88, '爱立刻有', 'Bell玲惠', 'http://music.163.com/song/media/outer/url?id=1965671755.mp3', 'http://p1.music.126.net/NLIMaAbS4VAuyKK699_87A==/109951167705664282.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (89, '鼓楼', '赵雷', 'http://music.163.com/song/media/outer/url?id=447926067.mp3', 'http://p1.music.126.net/BJgUd9aD9gpougZFASRTTw==/18548761162235571.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (90, '安和桥', '宋冬野', 'http://music.163.com/song/media/outer/url?id=27646205.mp3', 'http://p1.music.126.net/GcRunGm02vZBicYmIN6GXw==/109951163200249252.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (91, '从前有个妹妹说要跟我走', '贾盛强', 'http://music.163.com/song/media/outer/url?id=1332994607.mp3', 'http://p2.music.126.net/eeR_3M6TDkOy1hojfbIOhw==/109951163722203665.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (92, '机器铃 砍菜刀', '张卫', 'http://music.163.com/song/media/outer/url?id=473817665.mp3', 'http://p2.music.126.net/uIOrdW8DnS-TM2UinIBwXQ==/18590542604337710.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (93, '广东雨神 - 水山少年梦', '张大帅', 'http://music.163.com/song/media/outer/url?id=1865229309.mp3', 'http://p2.music.126.net/icBDaTKdj6tG4Tj99u4PTQ==/109951166230873592.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (94, '对酒', '浅影阿', 'http://music.163.com/song/media/outer/url?id=1977881457.mp3', 'http://p2.music.126.net/WaWBoR10466boazfxIkqoA==/109951167839139107.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (95, '麻雀', '李荣浩', 'http://music.163.com/song/media/outer/url?id=1407551413.mp3', 'http://p2.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (96, '百年孤寂', '苏玮', 'http://music.163.com/song/media/outer/url?id=1886371886.mp3', 'http://p2.music.126.net/vGsVzlfFMHl4iGy8hfsCyQ==/109951166512617210.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (97, '老伙计', 'cococola', 'http://music.163.com/song/media/outer/url?id=1824756293.mp3', 'http://p2.music.126.net/FMZE9Z40J8JYJe6vhjBaTg==/109951165777432400.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (98, '棉湖', '暗杠', 'http://music.163.com/song/media/outer/url?id=1888631655.mp3', 'http://p2.music.126.net/Dai7XYXh0qsA-Eot91FujQ==/109951166540853684.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (99, '碎银几两（Live）', '轩东', 'http://music.163.com/song/media/outer/url?id=1891208941.mp3', 'http://p2.music.126.net/XqQuQK1h6GXsC3Jnm0FTUQ==/109951167078524217.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (100, '胆怯', '一一', 'http://music.163.com/song/media/outer/url?id=1879112133.mp3', 'http://p2.music.126.net/bD3j-Kd8qRxfTfOXQApj6Q==/109951166426934645.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (101, '修得孤独淡寂寞', '乔洋', 'http://music.163.com/song/media/outer/url?id=1811901998.mp3', 'http://p2.music.126.net/ftqoIJKE7lR0GcuW5MGcuw==/109951165628025463.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (102, '偏爱和例外', '烟(许佳豪)', 'http://music.163.com/song/media/outer/url?id=1882041535.mp3', 'http://p2.music.126.net/kHcSK4xnum1d6SBdc1lw4Q==/109951167329387028.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (103, '别怕我伤心', '悦开心i', 'http://music.163.com/song/media/outer/url?id=1852086408.mp3', 'http://p2.music.126.net/orOwEsESQDZa6JsecPI1Yw==/109951166075100391.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (104, '莫等', '要不要买菜', 'http://music.163.com/song/media/outer/url?id=1902224134.mp3', 'http://p2.music.126.net/4bQzBfQK-CnPbluD-ELj-w==/109951166716819576.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (105, '星月落', '浮生梦tuzi', 'http://music.163.com/song/media/outer/url?id=1980103417.mp3', 'http://p2.music.126.net/5YtgBn3M08yLHQC-TUECOg==/109951167861097482.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (106, '是否', '程响', 'http://music.163.com/song/media/outer/url?id=1979274315.mp3', 'http://p2.music.126.net/bOJxkp8ujE62zRNZLWesqw==/109951167959345243.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (107, '浪里行', '封茗囧菌', 'http://music.163.com/song/media/outer/url?id=1812240297.mp3', 'http://p2.music.126.net/8r6w2zeYI07dQZBskQ6s2g==/109951165631206395.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (108, '折扇谣', '泽典', 'http://music.163.com/song/media/outer/url?id=1490631904.mp3', 'http://p2.music.126.net/YXojU5537QMGtAukoWhX1g==/109951165420771493.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (109, '子时', '音阙诗听', 'http://music.163.com/song/media/outer/url?id=1873289325.mp3', 'http://p2.music.126.net/y6fGmLO0TNf9H3mV_vHsIA==/109951166327922384.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (110, '海风与晚星', '张紫宁', 'http://music.163.com/song/media/outer/url?id=1922348893.mp3', 'http://p2.music.126.net/7kWrnA4BPoiY-WTLf9hLBw==/109951167164498265.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (111, '桃花赋', '鸾音社', 'http://music.163.com/song/media/outer/url?id=1431570733.mp3', 'http://p2.music.126.net/CHKb5FzJEg_9xGDmgxxSsA==/109951164811215100.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (112, '筝语', '卓舒晨', 'http://music.163.com/song/media/outer/url?id=1348950975.mp3', 'http://p2.music.126.net/ytitAZoF-aGSfCeKuxb7Hg==/109951163893047198.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (113, '孤身', '徐秉龙', 'http://music.163.com/song/media/outer/url?id=1365393542.mp3', 'http://p2.music.126.net/yVmtE5RFcJ1fhv-ivuyuRw==/109951164075300143.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (114, '上元诗酒游', 'Pig小优', 'http://music.163.com/song/media/outer/url?id=1410415908.mp3', 'http://p2.music.126.net/rf6PAbT7_Mq4QOPmLT0GVg==/109951166598762842.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (115, '西楼别序', '尹昔眠', 'http://music.163.com/song/media/outer/url?id=1811662859.mp3', 'http://p2.music.126.net/Yf2hFsTV3RRETpiKtCgbbA==/109951165762720351.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (116, '归去来兮', '花粥', 'http://music.163.com/song/media/outer/url?id=1357999894.mp3', 'http://p2.music.126.net/H6dt7IgvXNWhRM_w7XbcqQ==/109951163990575387.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (117, '迷人的危险', '尚士达', 'http://music.163.com/song/media/outer/url?id=1383876635.mp3', 'http://p2.music.126.net/aryxbULAHjqP5MPgUdg9gA==/109951164292787462.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (118, '这一生关于你的风景', '隔壁老樊', 'http://music.163.com/song/media/outer/url?id=1383927243.mp3', 'http://p2.music.126.net/72pkxsrTN_zUscdzMk5mMA==/109951164289743850.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (119, '失眠飞行', '接个吻，开一枪', 'http://music.163.com/song/media/outer/url?id=1365898499.mp3', 'http://p2.music.126.net/Bq6Io8lpY1l2HsQ28QKFlw==/109951164083996255.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (120, '像鱼', '王贰浪', 'http://music.163.com/song/media/outer/url?id=1331819951.mp3', 'http://p2.music.126.net/ejEPGN6ulPSgCBXGq7dgqw==/109951163720047382.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (121, '雅俗共赏', '许嵩', 'http://music.163.com/song/media/outer/url?id=411214279.mp3', 'http://p1.music.126.net/Wcs2dbukFx3TUWkRuxVCpw==/3431575794705764.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (122, '多想在平庸的生活拥抱你', '隔壁老樊', 'http://music.163.com/song/media/outer/url?id=1346104327.mp3', 'http://p1.music.126.net/gNbAlXamNjhR2j3aOukNhg==/109951164232796511.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (123, '记念', 'RAiNBOW计划', 'http://music.163.com/song/media/outer/url?id=35625821.mp3', 'http://p1.music.126.net/W_srVOtG_DKS1-txPLqNQQ==/3273246117001205.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (124, '山楂树之恋', '程佳佳', 'http://music.163.com/song/media/outer/url?id=1381755293.mp3', 'http://p1.music.126.net/G00sAe86sPi5gFQyuJHU7A==/109951164260611202.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (125, '梦里梦外都是你', '李雨泽lyz', 'http://music.163.com/song/media/outer/url?id=542897860.mp3', 'http://p1.music.126.net/dedM4Txz8qkZ2adfSqZxNQ==/109951163171476715.jpg', 2, NULL);
INSERT INTO `tb_user_music` VALUES (126, '四块五', '隔壁老樊', 'http://music.163.com/song/media/outer/url?id=1365221826.mp3', 'http://p1.music.126.net/gNbAlXamNjhR2j3aOukNhg==/109951164232796511.jpg', 2, NULL);

-- ----------------------------
-- Table structure for tb_user_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_resource`;
CREATE TABLE `tb_user_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户资源绑定表',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `resource_id` int NULL DEFAULT NULL COMMENT '资源id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id_a`(`user_id` ASC) USING BTREE,
  INDEX `fk_resource_id_a`(`resource_id` ASC) USING BTREE,
  CONSTRAINT `fk_resource_id_a` FOREIGN KEY (`resource_id`) REFERENCES `tb_sys_resource` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id_a` FOREIGN KEY (`user_id`) REFERENCES `tb_admin_user` (`admin_user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_resource
-- ----------------------------
INSERT INTO `tb_user_resource` VALUES (1, 2, 1, '2023-05-26 10:51:46', 1);
INSERT INTO `tb_user_resource` VALUES (2, 2, 3, '2023-05-26 11:49:31', 1);
INSERT INTO `tb_user_resource` VALUES (3, 2, 5, '2023-05-26 11:50:18', 1);
INSERT INTO `tb_user_resource` VALUES (4, 2, 2, '2023-05-30 10:23:03', 0);
INSERT INTO `tb_user_resource` VALUES (5, 2, 6, '2023-05-31 14:20:40', 0);
INSERT INTO `tb_user_resource` VALUES (6, 2, 7, '2023-06-01 14:10:50', 0);
INSERT INTO `tb_user_resource` VALUES (7, 2, 10, '2023-06-01 17:46:38', 1);

-- ----------------------------
-- Table structure for tb_user_resource_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_resource_log`;
CREATE TABLE `tb_user_resource_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '资源兑换历史',
  `resource_id` int NULL DEFAULT NULL COMMENT '资源id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `point` int NULL DEFAULT NULL COMMENT '扣减积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_resource_log
-- ----------------------------
INSERT INTO `tb_user_resource_log` VALUES (1, 2, 2, '2023-05-30 10:23:03', 1000);
INSERT INTO `tb_user_resource_log` VALUES (2, 6, 2, '2023-05-31 14:20:40', 100);
INSERT INTO `tb_user_resource_log` VALUES (3, 7, 2, '2023-06-01 14:10:50', 150);
INSERT INTO `tb_user_resource_log` VALUES (4, 10, 2, '2023-06-01 17:46:38', 150);

-- ----------------------------
-- Table structure for tb_user_social
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_social`;
CREATE TABLE `tb_user_social`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `social_uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交用户id',
  `social_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交平台名称',
  `social_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交平台类型',
  `access_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户此次登录的Access Token',
  `refresh_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '针对QQ，在授权自动续期步骤中，获取新的Access_Token时需要提供的参数',
  `expires_in` bigint NULL DEFAULT NULL COMMENT 'Access Token过期时间',
  `status` int NULL DEFAULT NULL COMMENT '绑定状态',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_with_social_name`(`user_id` ASC, `social_name` ASC) USING BTREE COMMENT '用户id相同情况下，社交平台不能一致'
) ENGINE = InnoDB AUTO_INCREMENT = 1664121421602783234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户与第三方账户绑定表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_social
-- ----------------------------
INSERT INTO `tb_user_social` VALUES (1664121421602783233, 2, '7746503599', '微博', 'weibo_id', '2.0089XP9IJQUHJCa7e5cf40a70tX3oZ', NULL, 157679999, NULL, '2023-06-01 12:07:19', '2023-06-01 12:23:08');

SET FOREIGN_KEY_CHECKS = 1;
