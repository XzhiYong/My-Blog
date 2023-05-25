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

 Date: 25/05/2023 15:41:52
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
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (2, 'admin', '0192023a7bbd73250516f069df18b500', '系统管理员', 0, '2023-04-27 16:33:08', 31, '2023-05-25 15:19:01', 'http://qny.xiazy.xyz/bloge63002e53ae646f1beee78b0de33e39e.jpg', '2799892833@qq.com', '', '2023-01-01', '1', 1, '', '192.168.3.182', NULL, 20);

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
INSERT INTO `tb_blog` VALUES (6, 'JAVA单列模式', '', 'http://qny.xiazy.xyz/blogfe3f606ed08b4edd844fdc105a7547a2.jpg', '#单列模式\n**意图**：保证一个类仅有一个实例，并提供一个访问它的全局访问点。\n\n**主要解决**：一个全局使用的类频繁地创建与销毁。\n\n**何时使用**：当您想控制实例数目，节省系统资源的时候。\n\n**如何解决**：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。\n\n**关键代码**：构造函数是私有的。\n\n####应用实例：\n\n1、一个班级只有一个班主任。\n2、Windows 是多进程多线程的，在操作一个文件的时候，就不可避免地出现多个进程或线程同时操作一个文件的现象，所以所有文件的处理必须通过唯一的实例来进行。\n3、一些设备管理器常常设计为单例模式，比如一个电脑有两台打印机，在输出的时候就要处理不能两台打印机打印同一个文件。\n**优点：**\n\n1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。\n2、避免对资源的多重占用（比如写文件操作）。\n缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。\n\n**使用场景：**\n\n1、要求生产唯一序列号。\n2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来。\n3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等。\n注意事项：getInstance() 方法中需要使用同步锁 synchronized (Singleton.class) 防止多线程同时进入造成 instance 被多次实例化。\n实现\n我们将创建一个 SingleObject 类。SingleObject 类有它的私有构造函数和本身的一个静态实例。\n\nSingleObject 类提供了一个静态方法，供外界获取它的静态实例。SingletonPatternDemo 类使用 SingleObject 类来获取 SingleObject 对象。\n\n单例模式的 UML 图\n\n**步骤 1**\n  SingleObject.java\n创建一个 Singleton 类。\n\n  \n    public class SingleObject {\n     \n       //创建 SingleObject 的一个对象\n       private static SingleObject instance = new SingleObject();\n     \n       //让构造函数为 private，这样该类就不会被实例化\n       private SingleObject(){}\n     \n       //获取唯一可用的对象\n       public static SingleObject getInstance(){\n          return instance;\n       }\n     \n       public void showMessage(){\n          System.out.println(\"Hello World!\");\n       }\n}\n**步骤 2**\n从 singleton 类获取唯一的对象。\n\nSingletonPatternDemo.java\n```java\npublic class SingletonPatternDemo {\n   public static void main(String[] args) {\n \n      //不合法的构造函数\n      //编译时错误：构造函数 SingleObject() 是不可见的\n      //SingleObject object = new SingleObject();\n \n      //获取唯一可用的对象\n      SingleObject object = SingleObject.getInstance();\n \n      //显示消息\n      object.showMessage();\n   }\n}\n```\n**步骤 3**\n执行程序，输出结果：\n\nHello World!\n单例模式的几种实现方式\n单例模式的实现有多种方式，如下所示：\n\n####1、懒汉式，线程不安全\n是否 Lazy 初始化：是\n\n是否多线程安全：否\n\n实现难度：易\n\n描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。\n这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n  \n    public static Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n接下来介绍的几种实现方式都支持多线程，但是在性能上有所差异。\n\n####2、懒汉式，线程安全\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。\n优点：第一次调用才初始化，避免内存浪费。\n缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。\ngetInstance() 的性能对应用程序不是很关键（该方法使用不太频繁）。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance;  \n    private Singleton (){}  \n    public static synchronized Singleton getInstance() {  \n        if (instance == null) {  \n            instance = new Singleton();  \n        }  \n        return instance;  \n    }  \n}\n```\n####3、饿汉式\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种方式比较常用，但容易产生垃圾对象。\n优点：没有加锁，执行效率会提高。\n缺点：类加载时就初始化，浪费内存。\n它基于 classloader 机制避免了多线程的同步问题，不过，instance 在类装载时就实例化，虽然导致类装载的原因有很多种，在单例模式中大多数都是调用 getInstance 方法， 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 显然没有达到 lazy loading 的效果。\n\n实例\n```java\npublic class Singleton {  \n    private static Singleton instance = new Singleton();  \n    private Singleton (){}  \n    public static Singleton getInstance() {  \n    return instance;  \n    }  \n}\n```\n4、双检锁/双重校验锁（DCL，即 double-checked locking）\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：较复杂\n\n描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。\ngetInstance() 的性能对应用程序很关键。\n\n实例\n```java\npublic class Singleton {  \n    private volatile static Singleton singleton;  \n    private Singleton (){}  \n    public static Singleton getSingleton() {  \n    if (singleton == null) {  \n        synchronized (Singleton.class) {  \n            if (singleton == null) {  \n                singleton = new Singleton();  \n            }  \n        }  \n    }  \n    return singleton;  \n    }  \n}\n```\n####5、登记式/静态内部类\n是否 Lazy 初始化：是\n\n是否多线程安全：是\n\n实现难度：一般\n\n描述：这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。\n这种方式同样利用了 classloader 机制来保证初始化 instance 时只有一个线程，它跟第 3 种方式不同的是：第 3 种方式只要 Singleton 类被装载了，那么 instance 就会被实例化（没有达到 lazy loading 效果），而这种方式是 Singleton 类被装载了，instance 不一定被初始化。因为 SingletonHolder 类没有被主动使用，只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder 类，从而实例化 instance。想象一下，如果实例化 instance 很消耗资源，所以想让它延迟加载，另外一方面，又不希望在 Singleton 类加载时就实例化，因为不能确保 Singleton 类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化 instance 显然是不合适的。这个时候，这种方式相比第 3 种方式就显得很合理。\n\n实例\n```java\npublic class Singleton {  \n    private static class SingletonHolder {  \n    private static final Singleton INSTANCE = new Singleton();  \n    }  \n    private Singleton (){}  \n    public static final Singleton getInstance() {  \n        return SingletonHolder.INSTANCE;  \n    }  \n}\n```\n####6、枚举\nJDK 版本：JDK1.5 起\n\n是否 Lazy 初始化：否\n\n是否多线程安全：是\n\n实现难度：易\n\n描述：这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。\n这种方式是 Effective Java 作者 Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。\n不能通过 reflection attack 来调用私有构造方法。\n\n实例\n```java\npublic enum Singleton {  \n    INSTANCE;  \n    public void whateverMethod() {  \n    }  \n}\n```\n经验之谈：一般情况下，不建议使用第 1 种和第 2 种懒汉方式，建议使用第 3 种饿汉方式。只有在要明确实现 lazy loading 效果时，才会使用第 5 种登记方式。如果涉及到反序列化创建对象时，可以尝试使用第 6 种枚举方式。如果有其他特殊的需求，可以考虑使用第 4 种双检锁方式。', 25, '设计模式', 'JAVA,设计模式', 2, 1107, 0, 0, '2023-04-21 13:54:26', '2023-05-04 17:02:33', 2);
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
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (51, 134, 6, '2023-05-17 18:44:58', '121212', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (52, 134, 6, '2023-05-17 18:45:03', '121212', 51, 51, 0);
INSERT INTO `tb_blog_comment` VALUES (53, 134, 6, '2023-05-18 09:36:02', '12121212', 51, 52, 0);
INSERT INTO `tb_blog_comment` VALUES (54, 134, 6, '2023-05-18 09:36:18', '12121212', 51, 53, 0);
INSERT INTO `tb_blog_comment` VALUES (60, 2, 6, '2023-05-18 14:03:45', '121212', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (61, 2, 6, '2023-05-18 14:04:02', '121212', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (62, 2, 6, '2023-05-18 14:04:17', '侧首', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (65, 2, 6, '2023-05-18 14:05:21', '3333', NULL, NULL, 0);
INSERT INTO `tb_blog_comment` VALUES (70, 2, 6, '2023-05-18 15:11:56', '121212', 65, 65, 0);

-- ----------------------------
-- Table structure for tb_blog_cover
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_cover`;
CREATE TABLE `tb_blog_cover`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '随机封面',
  `url` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_msg
-- ----------------------------
INSERT INTO `tb_blog_msg` VALUES (4, 2, '121212', 2, '2023-05-17 18:03:34', 6, 1, 2, 1);
INSERT INTO `tb_blog_msg` VALUES (5, 2, '121212', 2, '2023-05-25 10:26:42', 6, 1, 1, 1);

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
INSERT INTO `tb_sign` VALUES (1661632952544616449, 2, 1, '{\"ip\":\"192.168.3.182\",\"ipAddress\":\"本地局域网\"}', '2023-05-25 15:19:02', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_user_role
-- ----------------------------
INSERT INTO `tb_sys_user_role` VALUES (15, 1, 2, '2023-04-28 16:58:29');
INSERT INTO `tb_sys_user_role` VALUES (16, 2, 2, '2023-04-28 16:58:29');

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

SET FOREIGN_KEY_CHECKS = 1;
