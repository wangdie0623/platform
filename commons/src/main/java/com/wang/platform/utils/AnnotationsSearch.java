package com.wang.platform.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

//注解查询工具类
public class AnnotationsSearch {

    private static List<String> getPackageClassNames(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
        List<String> fileNames = getClassNames(filePath);
        return fileNames;
    }

    /**
     * 获取路径下所有
     *
     * @param filePath
     * @return
     */
    private static List<String> getClassNames(String filePath) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNames(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }

    /**
     * 获取指定package下，指定注解class集合
     *
     * @param packageName 指定包
     * @param annotation  指定注解
     * @return
     */
    public static List<Class> searchAnnotationClass(String packageName, Class annotation) {
        List<String> classNames = getPackageClassNames(packageName);
        List<Class> result = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                Annotation an = clazz.getAnnotation(annotation);
                if (an != null) {
                    result.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> list = getPackageClassNames("com.wang.platform");
        System.out.println(list.size());
    }
}
