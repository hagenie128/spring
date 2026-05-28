package singleton;

public class CounterBean {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
    
    public void onDestroy() {
    	System.out.println("CounterBean 소멸");
    }
}
