package user_package;
import java.util.HashMap;
import java.util.Map;

public class accountManager {
    private Map<String, String> mp;

    public accountManager() {
        mp = new HashMap<String, String>();
        mp.put("Patrick", "1234");
        mp.put("Molly", "FloPup");
    }

    public boolean accountExists(String str){   return mp.containsKey(str); }

    public boolean passwordCheck(String usr, String pwd){
        if(!accountExists(usr)) return false;
        return mp.get(usr).equals(pwd);
    }

    public void createAccount(String usr, String pwd){
        mp.putIfAbsent(usr, pwd);
    }
}
