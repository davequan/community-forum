package com.coral.community.entity;
/*
encapsulate paging
 */

public class Page {
    //current page
    private int current =1 ;
    //upperbound page
    private int limit = 10;
    // total messages(for calculate total page)
    private int rows;
    //look for path(used for separate pages)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current>=1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <=100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // get the start line for the current page
    public int getOffset(){
        //current * limit-limit
        return (current-1)*current;
    }
    // get the total pages
    public int getTotal(){
        //rows /limit+1
        if(rows%limit==0) {
            return (rows /limit);
        }else {
            return (rows/limit)+1;
        }
    }
    //get starting page
    public int getFrom(){
        int from=current-2;
        return from <1 ? 1:from;
    }

    //get ending page
    public int getTo(){
        int to = current+2;
        int total = getTotal();
        return to > total?total:to;
    }
}
