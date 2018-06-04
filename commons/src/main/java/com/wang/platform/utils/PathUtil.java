package com.wang.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by liyayun on 2016/11/30.
 */
public class PathUtil {

    public static String getUserDir() {
        String property = System.getProperty("user.dir");
        return property;
    }

    public static String getClassPath() {
        String property = PathUtil.class.getResource("/").getPath();
        return property;
    }

    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);

    /**
     * 获取配置文件路径
     *
     * @param path
     * @return
     * @see class path为jar包的情况下,路径指向与jar包同级目录
     * @see jetty jar包独立部署方式,需要在maven中添加resources配置<targetPath>${project.build.directory }</targetPath>
     * @see tomcat发布,需要将配置文件放在/WEB-INF下面
     */
    @SuppressWarnings("JavadocReference")
    public static Path get(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        String java_class_path = get_class_path();
        if (java_class_path.endsWith(".jar")) {
            int lastIndexOf = java_class_path.lastIndexOf("/");
            if (lastIndexOf == -1) {
                java_class_path = "";
            } else {
                java_class_path = java_class_path.substring(0, lastIndexOf);
            }
        }
        if (!java_class_path.isEmpty() && !java_class_path.endsWith("/")) {
            java_class_path = java_class_path.concat("/");
        }
        java_class_path = java_class_path.concat(path);
        logger.info("final path ---> :".concat(java_class_path));
        return Paths.get(java_class_path);
    }

    /**
     * 返回infogen jar 包的class path (jar包地址或末尾不包含/的class文件夹地址)
     *
     * @return
     */
    public static String get_infogen_class_path() {
        String location = PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        location = location.replace("file:", "");
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            location = location.substring(1);
        }
        if (location.contains(".jar!")) {
            location = location.substring(0, location.indexOf(".jar!")).concat(".jar");
        }
        if (location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        return location;
    }

    /**
     * 当前启动项目的class path (jar包地址或末尾不包含/的class文件夹地址)
     *
     * @return
     */
    public static String get_class_path() {
        String java_class_path = System.getProperty("java.class.path");
        logger.debug("java_class_path -> :".concat(java_class_path));
        logger.debug(System.getProperty("os.name"));
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            int indexof_classes = java_class_path.indexOf("\\classes");
            if (indexof_classes != -1) {
                // 直接代码启动
                java_class_path = java_class_path.substring(0, indexof_classes).concat("\\classes");
                int indexof_separator = java_class_path.lastIndexOf(";");
                if (indexof_separator != -1) {
                    java_class_path = java_class_path.substring(indexof_separator + 1);
                }
                logger.debug("windows code start --> :".concat(java_class_path));
            } else {
                String webroot = PathUtil.class.getResource("").getFile();
                webroot = webroot.replace("file:/", "");
                webroot = webroot.substring(1);
                int indexof_web_inf = webroot.indexOf("/WEB-INF/");
                if (indexof_web_inf != -1) {
                    // WEB容器启动
                    java_class_path = webroot.substring(0, indexof_web_inf).concat("/WEB-INF/classes");
                    logger.debug("windows server start --> :".concat(java_class_path));
                } else {
                    int comma = java_class_path.indexOf(";");
                    if (comma > 0) {
                        java_class_path = java_class_path.substring(0, comma);
                    }
                    // JAR包启动
                    logger.debug("windows jar start --> :".concat(java_class_path));
                }
            }
        } else {// LINUX
            int indexof_classes = java_class_path.indexOf("/classes");
            if (indexof_classes != -1) {
                // 直接代码启动
                java_class_path = java_class_path.substring(0, indexof_classes).concat("/classes");
                int indexof_separator = java_class_path.lastIndexOf(":");
                if (indexof_separator != -1) {
                    java_class_path = java_class_path.substring(indexof_separator + 1);
                }
                logger.debug("linux code start --> :".concat(java_class_path));
            } else {
                String webroot = PathUtil.class.getResource("").getFile();
                webroot = webroot.replace("file:", "");
                int indexof_web_inf = webroot.indexOf("/WEB-INF/");
                if (indexof_web_inf != -1) {
                    // WEB容器启动
                    java_class_path = webroot.substring(0, indexof_web_inf).concat("/WEB-INF/classes");
                    logger.debug("linux server start --> :".concat(java_class_path));
                } else {
                    int comma = java_class_path.indexOf(":");
                    if (comma > 0) {
                        java_class_path = java_class_path.substring(0, comma);
                    }
                    // JAR包启动
                    logger.debug("linux jar start --> :".concat(java_class_path));
                }
            }
        }
        return java_class_path;
    }

    /**
     * 当前启动项目的根目录
     *
     * @return
     */
    public static String get_running_path() {
        String java_class_path = System.getProperty("java.class.path");
        logger.debug("java_class_path -> :".concat(java_class_path));
        logger.debug(System.getProperty("os.name"));
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            int indexof_classes = java_class_path.indexOf("\\classes");
            if (indexof_classes != -1) {
                // 直接代码启动
                java_class_path = java_class_path.substring(0, indexof_classes).concat("\\classes");
                int indexof_separator = java_class_path.lastIndexOf(";");
                if (indexof_separator != -1) {
                    java_class_path = java_class_path.substring(indexof_separator + 1);
                }
                logger.debug("windows code start --> :".concat(java_class_path));
            } else {
                String webroot = PathUtil.class.getResource("").getFile();
                webroot = webroot.replace("file:/", "");
                webroot = webroot.substring(1);
                int indexof_web_inf = webroot.indexOf("/WEB-INF/");
                if (indexof_web_inf != -1) {
                    // WEB容器启动
                    java_class_path = webroot.substring(0, indexof_web_inf + 1);
                    logger.debug("windows server start --> :".concat(java_class_path));
                } else {
                    int comma = java_class_path.indexOf(";");
                    if (comma > 0) {
                        java_class_path = java_class_path.substring(0, comma);
                    }
                    // JAR包启动
                    logger.debug("windows jar start --> :".concat(java_class_path));
                }
            }
        } else {// LINUX
            int indexof_classes = java_class_path.indexOf("/classes");
            if (indexof_classes != -1) {
                // 直接代码启动
                java_class_path = java_class_path.substring(0, indexof_classes).concat("/classes");
                int indexof_separator = java_class_path.lastIndexOf(":");
                if (indexof_separator != -1) {
                    java_class_path = java_class_path.substring(indexof_separator + 1);
                }
                logger.debug("linux code start --> :".concat(java_class_path));
            } else {
                String webroot = PathUtil.class.getResource("").getFile();
                webroot = webroot.replace("file:", "");
                int indexof_web_inf = webroot.indexOf("/WEB-INF/");
                if (indexof_web_inf != -1) {
                    // WEB容器启动
                    java_class_path = webroot.substring(0, indexof_web_inf + 1);
                    logger.debug("linux server start --> :".concat(java_class_path));
                } else {
                    int comma = java_class_path.indexOf(":");
                    if (comma > 0) {
                        java_class_path = java_class_path.substring(0, comma);
                    }
                    // JAR包启动
                    logger.debug("linux jar start --> :".concat(java_class_path));
                }
            }
        }
        return java_class_path;
    }
}
