import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class thriftShop {
    static public void main(String[] args){

        Connection conn= connectionUtil.getConnection();

            List<clothes> clothesArray = new ArrayList<>();
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("Select * From clothes");
                while(rs.next()){
                    clothes cloth = new clothes(rs.getString("clothing_type"), rs.getString("color"),rs.getString("clothing_style"),rs.getString("clothing_size"),rs.getString("gender"),rs.getInt("price"));
                    clothesArray.add(cloth);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            for (int i=0; i<clothesArray.size(); i++ ){
               System.out.println( clothesArray.get(i).toString());

        }

    }

}
