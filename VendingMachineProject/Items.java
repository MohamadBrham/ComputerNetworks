public class Items {
    
    int id;
    int count;
    String name ; 
    Items(){
    }
    Items(  int id ,String name , int count  ){
        this.name = name;
        this.id = id;
        this.count = count;
    }
    
    void setCount(int count){
         this.count = count;
    }
    void setName(String name){
         this.name = name;
    }
    void setId(int id){
         this.id = id;
    }
    int getId(){
        return id;
    }
    int getCount(){
        return count;
    }
    String getName(){
        return name;
    }
    
    
    
    
    
    
    
}
