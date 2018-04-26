package com.jd.b2b.shardingjdbc.web.util;

import com.google.common.io.Resources;
import org.apache.commons.lang.StringUtils;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author songwanke
 * @date 2018/4/10
 */
public class ReadIniconfigUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ReadIniconfigUtil.class);

    /**
     * 读取 ini 配置文件 获取环境名称
     *
     * @param fileName
     */
    public static List<String> initPlatform(String fileName) {
        List<String> platformList = new ArrayList<>();

        URL url = Resources.getResource(fileName);

        Config cfg = new Config();
        // 设置Section不允许出现重复
        cfg.setMultiSection(false);
        Ini ini = new Ini();
        ini.setConfig(cfg);
        try {
            // 加载配置文件
            ini.load(url);

            Set<Map.Entry<String, Profile.Section>> set = ini.entrySet();
            LOG.debug("***************读取各环境名称开始***************");
            for (Map.Entry<String, Profile.Section> entry : set) {
                String sectionName = entry.getKey();
                platformList.add(sectionName);
            }
            LOG.info("platformList:{}",platformList);
            LOG.info("***************读取各环境名称结束***************");
            return platformList;
        } catch (Exception e) {
            LOG.error("读取各环境名称错误：", e);
        }
        return platformList;
    }

    /**
     * 读取 ini 配置文件 platformId
     *
     * @param fileName
     * [pre1]
     * platformId = 20
     * [pre1]
     * platformId = -2
     */
    public static List<Long> initPlatformId(String fileName,String sectionName) {
        List<Long> platformIdList = new ArrayList<>();

        URL url = Resources.getResource(fileName);

        Config cfg = new Config();
        // 设置Section允许出现重复
        cfg.setMultiSection(true);
        Ini ini = new Ini();
        ini.setConfig(cfg);
        try {
            // 加载配置文件
            ini.load(url);
            LOG.debug("***************读取各环境下platformId开始***************");
            for (Profile.Section session : ini.getAll(sectionName)) {
                platformIdList.add(Long.valueOf(session.get("platformId")));
            }
            LOG.info("platformIdList:{}",platformIdList);
            LOG.info("***************读取各环境platformId结束***************");
            return platformIdList;
        } catch (Exception e) {
            LOG.error("读取各环境名称错误：", e);
        }
        return platformIdList;
    }

    /**
     * 读取 ini 配置文件 platformId
     *
     * @param fileName
     */
    public static List<Long> initPlatformIdV2(String fileName,String sectionName) {
        List<Long> platformIdList = new ArrayList<>();

        Map<String, List<String>> stringListMap = readIniFile(fileName);
        try {
            // 加载配置文件
            LOG.debug("***************读取各环境下platformId开始***************");
            List<String> strings = stringListMap.get(sectionName);
            for(String aa:strings){
                platformIdList.add(Long.valueOf(aa));
            }
            LOG.info("platformIdList:{}",platformIdList);
            LOG.info("***************读取各环境platformId结束***************");
            return platformIdList;
        } catch (Exception e) {
            LOG.error("读取各环境名称错误：", e);
        }
        return platformIdList;
    }
    /**
     * 读取 INI 文件，存放到Map中
     *
     * @return Map<sectionName, object> object是一个Map（存放name=value对）或List（存放只有value的properties）
     */
    public static Map<String, List<String>> readIniFile(String fileName) {
        String filePath = getFilePath(fileName);
        Map<String, List<String>> listResult = new HashMap<>();
        Map<String, List<String>> result = new HashMap<>();

        if (StringUtils.isEmpty(filePath)) {
            return result;
        }
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String str = null;
            //处理缺省的section
            String currentSection = null;
            List<String> currentProperties = new ArrayList<>();
            boolean lineContinued = false;
            String tempStr = null;

            //一次读入一行（非空），直到读入null为文件结束
            //先全部放到listResult<String, List>中
            while ((str = reader.readLine()) != null) {
                //去掉尾部的注释、去掉首尾空格
                str = removeIniComments(str).trim();

                if (StringUtils.isEmpty(str)) {
                    continue;
                }

                if (lineContinued) {
                    str = tempStr + str;
                }

                //处理行连接符'\'
                if (str.endsWith("\\")) {
                    lineContinued = true;
                    tempStr = str.substring(0, str.length() - 1);
                    continue;
                } else {
                    lineContinued = false;
                }

                //是否一个新section开始了
                if (str.startsWith("[") && str.endsWith("]")) {
                    String newSection = str.substring(1, str.length() - 1).trim();

                    //如果新section不是现在的section，则把当前section存进listResult中
                    if (!newSection.equals(currentSection)) {
                        listResult.put(currentSection, currentProperties);
                        currentSection = newSection;

                        //新section是否重复的section
                        //如果是，则使用原来的list来存放properties
                        //如果不是，则new一个List来存放properties
                        currentProperties = listResult.get(currentSection);
                        if (currentProperties == null) {
                            currentProperties = new ArrayList<>();
                        }
                    }
                } else {
                    currentProperties.add(str);
                }
            }

            //把最后一个section存进listResult中
            listResult.put(currentSection, currentProperties);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {

                }
            }
        }

        //整理拆开name=value对，并存放到MAP中：
        //从listResult<String, List>中，看各个list中的元素是否包含等号“=”，如果包含，则拆开并放到Map中
        //整理后，把结果放进result<String, Object>中
        for (String key : listResult.keySet()) {
            List<String> tempList = listResult.get(key);

            //空section不放到结果里面
            if (tempList == null || tempList.size() == 0) {
                continue;
            }
            if (tempList.get(0).contains("=")) { //name=value对，存放在MAP里面
                Map<String, String> properties = new HashMap<>();
                List<String> values = new ArrayList<>();
                for (String s : tempList) {
                    int delimiterPos = s.indexOf("=");
                    //处理等号前后的空格
//                    properties.put(s.substring(0, delimiterPos).trim(), s.substring(delimiterPos + 1, s.length()).trim());
                    values.add(s.substring(delimiterPos + 1, s.length()).trim());
                }
                result.put(key, values);
            } else {
                result.put(key, listResult.get(key));
            }
        }
        return result;
    }

    private static String getFilePath(String fileName) {
        URL url = Resources.getResource(fileName);
        if (url != null) {
            return url.getPath();
        }
        return null;
    }

    /**
     * 去除ini文件中的注释，以";"或"#"开头
     *
     * @param source
     * @return
     */
    private static String removeIniComments(String source) {
        String result = source;
        if (result.contains(";")) {
            result = result.substring(0, result.indexOf(";"));
        }
        if (result.contains("#")) {
            result = result.substring(0, result.indexOf("#"));
        }
        return result.trim();
    }
}
