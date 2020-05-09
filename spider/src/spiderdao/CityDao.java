package spiderdao;
import spiderentity.City;
import util.DBUtil;

public class CityDao {
    public boolean insertCity(City city) {
        String sql = "insert into city values(?,?,?,?,?,?,?)";
        Object[] params = {city.getProvinceName(),city.getCityName(),city.getCurrentConfirmedCount(),city.getConfirmedCount(),city.getSuspectedCount(),city.getCuredCount(),city.getDeadCount()};
        return DBUtil.executeUpdate(sql, params);
    }

    public boolean clearAllCities()
    {
        String sql="delete from city";
        Object[] params={};
        return DBUtil.executeUpdate(sql, params);
    }

}
