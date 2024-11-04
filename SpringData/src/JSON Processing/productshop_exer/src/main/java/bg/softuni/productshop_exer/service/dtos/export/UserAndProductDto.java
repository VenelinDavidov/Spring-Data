package bg.softuni.productshop_exer.service.dtos.export;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UserAndProductDto implements Serializable {


    @Expose
    private int usersCount;

    @Expose
    private List<UserSoldDto> users;



    //getter and setter
    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserSoldDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldDto> users) {
        this.users = users;
    }
}
