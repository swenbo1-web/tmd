package src;

import java.io.File;
import java.io.Serializable;
/**
 * Student类，实现Serializable接口
 */
public class Player implements Serializable{
	private File imagePic;
	private String number,name,skin,major,grade,birthday;
    public void setNumber(String number){
       this.number=number;
    }
    public String getNumber(){
       return number;
    }
    public void setName(String name){
       this.name=name;
    }
    public String getName(){
       return name;
    }
    public void setGrade(String grade){
       this.grade=grade;
    }
    public String getGrade(){
       return grade;
    }
    public void setskin(String sex){
       this.skin=sex;
    }
    public String getskin(){
       return skin;
    }
    public void setImagePic(File image){
        imagePic=image;
    }
    public File getImagePic(){
        return imagePic;
    }
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
