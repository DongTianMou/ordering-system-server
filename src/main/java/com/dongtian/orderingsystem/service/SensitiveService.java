package com.dongtian.orderingsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SensitiveService implements InitializingBean {
    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "***";
    /*内部类*/
    private class TrieNode {
        //true 敏感词的终结 ；
        private boolean end = false;
        // key为字符，value是对应的节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // 向指定位置添加节点
        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }
        // 获取节点
        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }
        //  是否为敏感词结尾
        boolean isKeywordEnd() {
            return end;
        }
        //  设置为敏感词结尾
        void setKeywordEnd(boolean end) {
            this.end = end;
        }
    }
        /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();
    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String text) {
        // 如果内容为空，直接返回原内容。
        if (StringUtils.isBlank(text)) {
            return text;
        }
        // 敏感词的替换词
        String replacement = DEFAULT_REPLACEMENT;
        // StringBuilder容器存储过滤后的结果
        StringBuilder result = new StringBuilder();
        // 记录指针，初始值指向根节点
        TrieNode tempNode = rootNode;
        int begin = 0; // 回滚指针
        int position = 0; // 指示当前位置指针
        // 如果当前位置为到达文本末尾
        while (position < text.length()) {
            // 记录当前位置字符值
            char c = text.charAt(position);
            // 如果该字符是符号
            if (isSymbol(c)) {
                // 记录指针在根节点，说明第一个字符是符号
                if (tempNode == rootNode) {
                    // 把该符号添加到结果集中
                    result.append(c);
                    // 回滚指针向前移动一位
                    ++begin;
                }
                // 如果该符号不是第一个字符，当前位置向前移动一位
                ++position;
                //  跳出本次循环
                continue;
            }
            // 如果该字符不是符号，记录节点指针向前缀树查询是否存在以该字符为首的敏感词
            tempNode = tempNode.getSubNode(c);
            // 记录节点指针为空，表示查不到以该字符为首的敏感词
            if (tempNode == null) {
                // 以begin位置开始的字符串不存在敏感词
                result.append(text.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {//该字符是敏感词结束标志
                // 发现敏感词， 从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else { //存在以该字符为首的敏感词
                // 当前位置向前移动一位
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        // 循环每个字节
        for (int i = 0; i < lineTxt.length(); ++i) {
            Character c = lineTxt.charAt(i);
            // 过滤空格
            if (isSymbol(c)) {
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);

            if (node == null) { // 没初始化
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineTxt.length() - 1) {
                // 关键词结束， 设置结束标志
                tempNode.setKeywordEnd(true);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            log.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    public static void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("好色");
        System.out.print(s.filter("真色,色情ss"));
    }
}

