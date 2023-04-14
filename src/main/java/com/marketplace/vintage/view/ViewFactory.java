package com.marketplace.vintage.view;

public class ViewFactory {

    public View createView(ViewType viewType) {
        return new BaseView() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 2);
                    System.out.println("exiting");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
