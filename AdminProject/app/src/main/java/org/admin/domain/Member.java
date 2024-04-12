package org.admin.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Member {
    private int memberNo;
    private int nationNo;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Date birthDay;
    private String tel;
    private int gradeNo;
    private String gradeName;
    private char state;
    private String stateStr;
    private String address;
    private String sex;
    private Date joinDate;
    private Date lastLoginDate;
    private Date exitDate;
    private int warningCount;
    private String photo;
    private int rentalCount;
    private List<Rental> rentals;
}
