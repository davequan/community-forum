package com.coral.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
     /*
     * 1.define trie tree
     * 2.initialize with banned word
     * 3.define method to filter the word
     * */
    private  static  final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    private static final String  REPLACEMENT="***";
    //
    private  TrieNode rootNode = new TrieNode();


    @PostConstruct
    public void init(){
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
           String keyword;
           while((keyword = reader.readLine()) != null){
               // add to trie tree
               this.addKeyword(keyword);
           }
        }catch (IOException e){
            logger.error("loading txt fail"+e.getMessage());
        }
    }
    // add a banned word to the trie tree
    private void addKeyword(String keyword){
        TrieNode temNode = rootNode;
        for(int i =0 ;i < keyword.length(); i++){
            char c = keyword.charAt(i);
            TrieNode subNode = temNode.getSubNode(c);
            if(subNode == null){
                // initialize subNode
                subNode = new TrieNode();
                temNode.addSubNode(c,subNode);
            }
            // go to the next level
            temNode = subNode;
            if(i == keyword.length()-1){
                temNode.setWordEnd(true);
            }
        }
    }

    /**
     *
     * @param text text need to filter
     * @return text after filter
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        // pointer1 at prefix tree
        TrieNode temNode = rootNode;
        // pointer2 at beginning of word
        int begin =0;
        //pointer3
        int position =0;
        StringBuilder sb = new StringBuilder();
        while (position < text.length()){
            char c = text.charAt(position);
            // jump through the symbol
            if(isSymbol(c)){
                //if pointer is at rootnode,save this symbol to result,pointer 2 move
                if(temNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                //
                position++;
                continue;
            }
            temNode = temNode.getSubNode(c);
            if(temNode == null){
                // string start with begin is not banned word
                 sb.append(text.charAt(begin));
                // next position
                 position = ++begin;
                 // point to new node
                 temNode = rootNode;
            }else if(temNode.isWordEnd()){
                //found banned word
                sb.append(REPLACEMENT);
                begin=++position;
                //point to new node
                temNode = rootNode;
            }else{
                position++;
            }
        }
        sb.append(text.substring(begin));
        return  sb.toString();
    }
    //check whether its symbol
    private  boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c);
    }
















    // define trie tree
    private class TrieNode{
         private boolean isWordEnd = false;
        //children node : key is next char , value is next node
         private Map<Character,TrieNode> subNodes = new HashMap<>();

         public boolean isWordEnd() {
             return isWordEnd;
         }

         public void setWordEnd(boolean wordEnd) {
             isWordEnd = wordEnd;
         }
         // add children node
         public void addSubNode(Character c ,TrieNode node){
             subNodes.put(c,node);
         }
         // get children node
         public TrieNode getSubNode(Character c){
             return subNodes.get(c);
         }
     }




}
