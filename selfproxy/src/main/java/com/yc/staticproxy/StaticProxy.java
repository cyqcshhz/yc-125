package com.yc.staticproxy;

public class StaticProxy implements OrderBiz{
    private OrderBiz orderBiz;

    public StaticProxy(OrderBiz orderBiz){
        this.orderBiz=orderBiz;
    }
    @Override
    public void addOrder(int pid, int num) {
        showHello();
        this.orderBiz.addOrder(pid,num);
        showBbye();

    }

    private void showBbye() {
        System.out.println("hello");
    }

    private void showHello() {
        System.out.println("bye");
    }
}
