package reflection;

public class UserBean {

    private String userName;

    private int userId;

    public UserBean(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Invoke
    public static void staticMethod(String devName){
        System.out.println("Hi " + devName + " ,I'm a static method");
    }

    @Invoke
    public void publicMethod(){
        System.out.println("I'm a public method " + userName + " " + userId);
    }

    @Invoke
    private void privateMethod() {
        System.out.println("I'm a private method " + userName + " " + userId);
    }
}
