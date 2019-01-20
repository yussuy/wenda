package me.yushuo.wenda.service;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    private static String DEFAULT_REPLACEMENT = "<敏感词>";
    private TrieNode rootNode = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                lineText = lineText.trim();
                addWord(lineText);
            }
        }catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    private class TrieNode {

        private boolean end = false;

        private Map<Character, TrieNode> subTrieNode = new HashMap<Character, TrieNode>();

        public void addSubNode(Character c, TrieNode node) {
            subTrieNode.put(c, node);
        }

        public TrieNode getSubNode(Character c) {
            return subTrieNode.get(c);
        }

        public void setIsEndWord(boolean end) {
            this.end = end;
        }

        public boolean getIsEndWord() {
            return end;
        }

    }


    private void addWord(String lineText) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineText.length(); ++i) {
            Character c = lineText.charAt(i);

            if (isSymbol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
//            if (i == lineText.length() - 1) {
//                tempNode.setIsEndWord(true);
//            }
        }
        tempNode.setIsEndWord(true);
    }

    public String filter(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        String replace = DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position < text.length()) {
            Character c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                result.append(c);
                begin++;
                position = begin;
                tempNode = rootNode;
            } else if (tempNode.getIsEndWord()) {
                result.append(replace);
                tempNode = rootNode;
                position++;
                begin = position;
            } else {
                position++;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        s.addWord("发票");
        s.addWord("供暖");
        s.addWord("物业");
        String text = "我有一张物业和供暖发票需要的找我";

        System.out.println(s.filter(text));

    }
}
