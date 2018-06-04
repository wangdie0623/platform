package com.wang.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @Author lujun
 * @Date 2017/9/20 17:02
 */
public class JsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsUtil.class);

    /**
     * @Author lujun
     * @Date 2017/9/20
     * @Description 执行Javascript函数，
     * @params path: js文件路径 ; methodName: 要执行的js函数的方法名；... params: js函数参数数组，安顺序传递。
     */
    public static String executeJavaScriptFromFile(String path, String methodName, Object... params) {
        String result = "";
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            FileReader reader = new FileReader(new File(path));
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                result = (String) invocable.invokeFunction(methodName, params);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ScriptException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
    /**
    * @Author lujun
    * @Date 2017/9/28
    * @Description 执行Javascript函数，
    * @params path: js,字符串 ; methodName: 要执行的js函数的方法名；... params: js函数参数数组，安顺序传递。
    */
    public static String executeJavaScriptFromStr(String jsStr,String methodName,Object... params){
        String result = "";
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            engine.eval(jsStr);
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                result = (String) invocable.invokeFunction(methodName, params);
            }
        }  catch (ScriptException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}
