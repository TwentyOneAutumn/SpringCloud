package com.demo.Core.Utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.Core.DoMain.MapEntry;
import com.demo.Core.Interface.ClassMapping;
import com.demo.Core.Interface.MethodMapping;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Class工具类
 */
public class ClassUtils {

    /**
     * 扫描类
     * @param packageName 包名
     * @return 包下所有类Class对象集合
     * @throws Exception 异常
     */
    public static List<Class<?>> scanClasses(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("jar")) {
                classes.addAll(scanJarInJar(packageName, resource));
            } else {
                classes.addAll(scanClassesInDirectory(packageName, new java.io.File(resource.getFile())));
            }
        }
        return classes;
    }

    /**
     * 扫描子包下所有类
     * @param packageName 包名
     * @param directory 文件夹对象
     * @return 包下所有类Class对象集合
     * @throws ClassNotFoundException 类不存在异常
     */
    public static List<Class<?>> scanClassesInDirectory(String packageName, java.io.File directory) throws ClassNotFoundException {
        if (!directory.exists()) {
            return Collections.emptyList();
        }
        List<Class<?>> classes = new ArrayList<>();
        java.io.File[] files = directory.listFiles();
        if(ArrayUtil.isNotEmpty(files)){
            for (java.io.File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(scanClassesInDirectory(packageName + "." + file.getName(), file));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

    /**
     * 扫描Jar包下所有类
     * @param packageName 包名
     * @param jarUrl jar包路径
     * @return jar包下所有类Class对象集合
     * @throws Exception 异常
     */
    public static List<Class<?>> scanJarInJar(String packageName, URL jarUrl) throws Exception {
        String[] jarInfo = jarUrl.getPath().split("!");
        java.util.jar.JarFile jar = new java.util.jar.JarFile(jarInfo[0].substring(jarInfo[0].indexOf('/')));
        List<Class<?>> classes = new ArrayList<>();

        Enumeration<java.util.jar.JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            java.util.jar.JarEntry entry = entries.nextElement();
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                if (className.startsWith(packageName)) {
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

    /**
     * 获取目标映射
     * @param serviceName 服务名映射
     * @param url 方法映射
     * @return Map Key:目标类Class对象  Value:目标方法对象
     */
    public static MapEntry<Class<?>, Method> toMethod(String serviceName, String url) throws Exception {
        // 扫描并获取包下所有类
        List<Class<?>> classList = scanClasses("packageName");
        AtomicReference<MapEntry<Class<?>, Method>> atomicReference = new AtomicReference<>();
        classList.forEach(clazz -> {
            // 获取服务名映射注解
            ClassMapping classMapping = clazz.getAnnotation(ClassMapping.class);
            // 判空 匹配服务映射
            if(BeanUtil.isNotEmpty(classMapping) && classMapping.value().equals(serviceName)){
                // 读取类下所有方法对象
                Method[] methods = clazz.getMethods();
                Arrays.stream(methods).forEach(method -> {
                    // 获取方法路径映射
                    MethodMapping methodMapping = method.getAnnotation(MethodMapping.class);
                    if(BeanUtil.isNotEmpty(methodMapping) && methodMapping.value().equals(url)){
                        atomicReference.set(new MapEntry<>(clazz, method));
                    }
                });
            }
        });
        MapEntry<Class<?>, Method> entry = atomicReference.get();
        // 判空
        if(BeanUtil.isNotEmpty(entry) && BeanUtil.isNotEmpty(entry.getKey()) && BeanUtil.isNotEmpty(entry.getValue())){
            return entry;
        }else {
            throw new RuntimeException("读取Feign映射异常");
        }
    }

    /**
     * 执行目标方法并获取返回值
     * @param request 请求对象
     * @param entry 目标映射
     * @return 方法的返回值
     */
    public static Object invokeMethod(HttpServletRequest request, MapEntry<Class<?>, Method> entry){
        // 获取目标类Class对象
        Class<?> clazz = entry.getKey();
        // 获取目标方法对象
        Method method = entry.getValue();
        // 方法参数List
        List<Object> argList = new ArrayList<>();
        // 获取返回值类型
        Class<?> returnTypeClass = method.getReturnType();

        // 声明返回值对象
        Object returnData = null;
        // 判断请求方式
        switch (request.getMethod()){
            case "GET" : {
                argList.addAll(getParameter(request,method));
                break;
            }
            case "POST" :
            case "PUT" :
            case "DELETE" : {
                // 获取参数数量
                int parameterCount = method.getParameterCount();
                // 判断请求参数数量
                if(parameterCount == 1){
                    // 获取参数对象
                    Parameter parameter = StreamUtils.findAny(Arrays.stream(method.getParameters()));
                    // 将请求体参数转换为对象
                    Object parameterBean = JSONUtil.toBean(getRequestBody(request), parameter.getType());
                    // 将转换后的参数存入参数List
                    argList.add(parameterBean);

                }else if(parameterCount != 0){
                    throw new RuntimeException("请求参数异常");
                }
                break;
            }
        }
        try {
            // 通过反射调用方法并获取返回值
            Object invoke = method.invoke(clazz.newInstance(), argList.toArray());
            // 将返回值类型转换
            returnData = BeanUtil.copyProperties(invoke, returnTypeClass);
        } catch (Exception e) {
            throw new RuntimeException("调用缓存方法异常");
        }
        return returnData;
    }

    /**
     * 获取请求body
     * @param request 请求对象
     * @return body
     */
    public static String getRequestBody(HttpServletRequest request){
        // 获取请求Body
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("读取RequestBody异常");
        }
        return sb.toString();
    }

    /**
     * 获取GET请求参数
     * @param request 请求对象
     * @param method 方法对象
     * @return 参数List
     */
    public static List<Object> getParameter(HttpServletRequest request, Method method){
        List<Object> argList = new ArrayList<>();
        // 判断参数列表是否为空
        if(method.getParameterCount() == 0){
            return argList;
        }
        // 获取参数的类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 判断参数数量与类型数量是否一致
        if(parameterTypes.length != request.getParameterMap().size()){
            // 处理方法参数
            argList.addAll(handlerParameter(parameterTypes,request));
        }else {
            // 获取参数列表
            Enumeration<String> enu = request.getParameterNames();
            Map<String, Class<?>> parameterMap = new HashMap<>();
            int i = 0;
            while (enu.hasMoreElements()){
                parameterMap.put(request.getParameter(enu.nextElement()),parameterTypes[i++]);
            }
            // 处理方法参数
            argList.addAll(handlerParameterMapping(parameterMap));
        }
        return argList;
    }

    /**
     * 分割处理参数
     * @param str 参数
     * @return 分割处理后的参数
     */
    public static String[] handlerParameter(String str){
        return str.replace("[", "")
                .replace("]", "")
                .trim().split(",");
    }

    /**
     * 处理GET请求DTO封装问题
     * @param parameterTypes 参数类型
     * @param request 请求对象
     * @return 参数List
     */
    public static List<Object> handlerParameter(Class<?>[] parameterTypes,HttpServletRequest request){
        if(parameterTypes.length == 1) {
            Class<?> parameterType = parameterTypes[0];
            if (parameterType != String.class &&
                    parameterType != Date.class &&
                    parameterType != LocalDate.class &&
                    parameterType != int.class &&
                    parameterType != Integer.class &&
                    parameterType != long.class &&
                    parameterType != Long.class &&
                    parameterType != double.class &&
                    parameterType != Double.class &&
                    !parameterType.isArray() &&
                    !parameterType.isAssignableFrom(List.class) &&
                    !parameterType.isAssignableFrom(Set.class) &&
                    !parameterType.isAssignableFrom(Map.class)
            ) {
                // 获取当前类中字段
                Field[] fields = parameterType.getFields();
                // 设置权限
                for (Field field : fields) {
                    field.setAccessible(true);
                }
                // 获取参数列表
                Enumeration<String> enu = request.getParameterNames();
                Map<String,MapEntry<String,Class<?>>> parameterMap = new HashMap<>();
                while (enu.hasMoreElements()){
                    String parameterName =  enu.nextElement();
                    // 循环类字段数组
                    for (Field field : fields) {
                        // 判断当前参数是否跟字段值匹配
                        if(field.getName().equals(parameterName)){
                            // 如果匹配则获取当前字段类型
                            Class<?> type = field.getType();
                            // 添加映射到Map
                            parameterMap.put(parameterName, new MapEntry<>(request.getParameter(parameterName), type));
                        }
                    }
                }
                return handlerParameterMapping(parameterMap,parameterType);
            }else {
                throw new RuntimeException("参数异常");
            }
        }else {
            throw new RuntimeException("参数异常");
        }
    }

    /**
     * 处理映射并转换类型
     * @param parameterMap 映射
     * @return 参数List
     */
    public static List<Object> handlerParameterMapping(Map<String, Class<?>> parameterMap){
        List<Object> argList = new ArrayList<>();
        // 循环参数列表并转换类型
        parameterMap.forEach((key,value) -> {
            try {
                // 判空
                if(StrUtil.isEmpty(key) || value == String.class){
                    argList.add(key);
                }
                // 判断是否为时间类型
                else if(value == Date.class){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = format.parse(key);
                    argList.add(date);
                }
                else if(value == LocalDate.class){
                    LocalDate localDate = LocalDate.parse(key);
                    argList.add(localDate);
                }
                // 是否为int类型
                else if(value == int.class || value == Integer.class){
                    Integer integer = Integer.valueOf(key);
                    argList.add(integer);
                }
                // 是否为long类型
                else if(value == long.class || value == Long.class){
                    Long aLong = Long.valueOf(key);
                    argList.add(aLong);
                }
                // 是否为double类型
                else if(value == double.class || value == Double.class){
                    Double aDouble = Double.valueOf(key);
                    argList.add(aDouble);
                }
                // 是否为数组类型
                else if(value.isArray()){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Object[] parameterArr = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).toArray();
                    argList.add(parameterArr);
                }
                // 是否为List集合类型
                else if (value.isAssignableFrom(List.class)){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    List<?> parameterList = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).collect(Collectors.toList());
                    argList.add(parameterList);
                }
                // 是否为Set集合类型
                else if (value.isAssignableFrom(Set.class)){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Set<?> parameterSet = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).collect(Collectors.toSet());
                    argList.add(parameterSet);
                }
                // 是否为Map集合类型
                else if (value.isAssignableFrom(Map.class)){
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Map<String, Object> map = Arrays.stream(parameterStrArr).map(parameter -> {
                        String[] split = parameter.split(":");
                        return new MapEntry<String, Object>(split[0], split[1]);
                    }).collect(Collectors.toMap(MapEntry::getKey, MapEntry::getValue));
                    argList.add(map);
                }else {
                    throw new RuntimeException("参数类型异常");
                }
            } catch (Exception e) {
                throw new RuntimeException("参数类型转换异常");
            }
        });
        return argList;
    }

    /**
     * 处理映射并转换类型
     * @param parameterMap 映射
     * @return 参数List
     */
    public static List<Object> handlerParameterMapping(Map<String,MapEntry<String,Class<?>>> parameterMap,Class<?> parameterType){
        // 创建JSON对象
        JSONObject json = new JSONObject();
        // 循环参数列表并转换类型
        parameterMap.forEach((name,entry) -> {
            try {
                String key = entry.getKey();
                Class<?> value = entry.getValue();
                // 判空
                if(StrUtil.isEmpty(key) || value == String.class){
                    json.append(name,value);
                }
                // 判断是否为时间类型
                else if(value == Date.class){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = format.parse(key);
                    json.append(name,date);
                }
                else if(value == LocalDate.class){
                    LocalDate localDate = LocalDate.parse(key);
                    json.append(name,localDate);
                }
                // 是否为int类型
                else if(value == int.class || value == Integer.class){
                    Integer integer = Integer.valueOf(key);
                    json.append(name,integer);
                }
                // 是否为long类型
                else if(value == long.class || value == Long.class){
                    Long aLong = Long.valueOf(key);
                    json.append(name,aLong);
                }
                // 是否为double类型
                else if(value == double.class || value == Double.class){
                    Double aDouble = Double.valueOf(key);
                    json.append(name,aDouble);
                }
                // 是否为数组类型
                else if(value.isArray()){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Object[] parameterArr = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).toArray();
                    json.append(name,parameterArr);
                }
                // 是否为List集合类型
                else if (value.isAssignableFrom(List.class)){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    List<?> parameterList = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).collect(Collectors.toList());
                    json.append(name,parameterList);
                }
                // 是否为Set集合类型
                else if (value.isAssignableFrom(Set.class)){
                    // 获取数组元素类型
                    Class<?> componentType = value.getComponentType();
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Set<?> parameterSet = Arrays.stream(parameterStrArr).map(parameter -> BeanUtil.toBean(parameter, componentType)).collect(Collectors.toSet());
                    json.append(name,parameterSet);
                }
                // 是否为Map集合类型
                else if (value.isAssignableFrom(Map.class)){
                    // 获取数组
                    String[] parameterStrArr = handlerParameter(key);
                    // 泛型转换
                    Map<String, Object> map = Arrays.stream(parameterStrArr).map(parameter -> {
                        String[] split = parameter.split(":");
                        return new MapEntry<String, Object>(split[0], split[1]);
                    }).collect(Collectors.toMap(MapEntry::getKey, MapEntry::getValue));
                    json.append(name,map);
                }else {
                    throw new RuntimeException("参数类型异常");
                }
            } catch (Exception e) {
                throw new RuntimeException("参数类型转换异常");
            }
        });
        // 将JSON对象转换为参数类型
        return ListUtil.list(false,json.toBean(parameterType));
    }
}
