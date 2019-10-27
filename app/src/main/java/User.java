public abstract class User {

    private String email;
    private String username;
    private String password;
    private String gender;
    private String picture;

    //getter functions
    public String getEmail(){ return email; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public String getGender(){ return gender; }
    public String getPicture(){ return picture; }

    //setter/change functions
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String newPassword){ this.password = password; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPicture(String picture) { this.picture = picture; }
}
